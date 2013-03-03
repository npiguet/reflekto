package reflekto.impl;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import reflekto.AbstractTypeName;
import reflekto.ClassType;
import reflekto.Constructor;
import reflekto.Field;
import reflekto.FinalReference;
import reflekto.FullReflector;
import reflekto.LazyInit;
import reflekto.Method;
import reflekto.Methods;
import reflekto.ReadOnlyReference;
import reflekto.Type;
import reflekto.TypeName;
import reflekto.TypeVariable;

public class DefaultClassType extends AbstractType implements ClassType {

	private final ReadOnlyReference<List<Type>> actualTypeParameters;
	private final ReadOnlyReference<List<TypeVariable>> declaredTypeParameters;
	private final ReadOnlyReference<Methods> methods = new LazyInit<Methods>() {
		@Override
		protected Methods init() {
			return initMethods();
		}
	};
	private final ReadOnlyReference<List<Field>> fields = new LazyInit<List<Field>>() {
		@Override
		protected List<Field> init() {
			return initFields();
		}
	};
	private final ReadOnlyReference<List<Constructor>> constructors = new LazyInit<List<Constructor>>() {
		@Override
		protected List<Constructor> init() {
			return initConstructors();
		}
	};
	private final ReadOnlyReference<ClassType> superClass = new LazyInit<ClassType>() {
		@Override
		protected ClassType init() {
			return initSuperClass();
		}
	};
	private final ReadOnlyReference<List<ClassType>> interfaces = new LazyInit<List<ClassType>>() {
		@Override
		protected List<ClassType> init() {
			return initInterfaces();
		}
	};
	private final ReadOnlyReference<ClassType> enclosingClass = new LazyInit<ClassType>() {
		@Override
		protected ClassType init() {
			return initEnclosingClass();
		}
	};
	private final boolean isErasure;

	@SuppressWarnings("unchecked")
	public DefaultClassType(Class<?> clazz, FullReflector creator) {
		super(clazz, creator);
		declaredTypeParameters = new LazyInit<List<TypeVariable>>() {
			@Override
			protected List<TypeVariable> init() {
				return initTypeParameters();
			}
		};
		// Yes, I know this is ugly, but since all of the involved objects are effectively final,
		// it is effectively safe, and makes my life easier than carrying around two levels of wildcard types.
		this.actualTypeParameters = (ReadOnlyReference<List<Type>>) (Object) this.declaredTypeParameters;
		this.isErasure = false;
	}

	/**
	 * Builds a copy of the original DefaultClassType, replacing the list of type parameters, methods, constructors and
	 * fields iif the specified one is not null.
	 */
	// FIXME
	// FIXME
	// FIXME
	// FIXME
	// FIXME: Shouldn't be public
	public DefaultClassType(DefaultClassType original, List<Type> actualTypeParameters, boolean isErasure) {
		super(original.clazz, original.reflector);
		this.declaredTypeParameters = original.declaredTypeParameters;
		List<Type> unmodifiable = Collections.unmodifiableList(new ArrayList<Type>(actualTypeParameters));
		this.actualTypeParameters = new FinalReference<List<Type>>(unmodifiable);
		this.isErasure = isErasure;
	}

	private List<TypeVariable> initTypeParameters() {
		java.lang.reflect.TypeVariable<? extends Class<?>>[] langParams = clazz.getTypeParameters();
		List<TypeVariable> params = new ArrayList<TypeVariable>(langParams.length);
		for (java.lang.reflect.TypeVariable<? extends Class<?>> var : langParams) {
			params.add(reflector.reflect(var));
		}
		return Collections.unmodifiableList(params);
	}

	private List<Constructor> initConstructors() {
		// TODO: replace the type variables by their values if necessary
		java.lang.reflect.Constructor<?>[] langConstructors = clazz.getDeclaredConstructors();
		List<Constructor> cons = new ArrayList<Constructor>(langConstructors.length);
		for (java.lang.reflect.Constructor<?> c : langConstructors) {
			cons.add(reflector.reflect(c, this));
		}
		return Collections.unmodifiableList(cons);
	}

	private List<Field> initFields() {
		// TODO: replace the type variables by their values if necessary
		java.lang.reflect.Field[] langFields = clazz.getDeclaredFields();
		List<Field> flds = new ArrayList<Field>(langFields.length);
		for (java.lang.reflect.Field f : langFields) {
			flds.add(reflector.reflect(f, this));
		}
		return Collections.unmodifiableList(flds);
	}

	private Methods initMethods() {
		List<Methods> superTypesMethods = new ArrayList<Methods>();
		if (this.getSuperClass() != null) {
			superTypesMethods.add(this.getSuperClass().methods());
		}
		for (ClassType iface : this.getInterfaces()) {
			superTypesMethods.add(iface.methods());
		}

		List<Method> thisMethods = initThisClassMethods();
		return new Methods(thisMethods, superTypesMethods);
	}

	private List<Method> initThisClassMethods() {
		java.lang.reflect.Method[] jMethods = clazz.getDeclaredMethods();
		List<Method> methods = new ArrayList<Method>();
		for (java.lang.reflect.Method jMethod : jMethods) {
			methods.add(reflector.reflect(jMethod, this));
		}
		return methods;
	}

	private ClassType initSuperClass() {
		ClassType genericSuperClass = (ClassType) reflector.reflect(clazz.getGenericSuperclass());
		if (genericSuperClass == null) {
			return null;
		}
		return genericSuperClass.assignVariables(getDeclaredTypeParameters(), getActualTypeParameters());
	}

	private List<ClassType> initInterfaces() {
		java.lang.reflect.Type[] ifaces = clazz.getGenericInterfaces();
		List<ClassType> types = new ArrayList<ClassType>(ifaces.length);
		for (java.lang.reflect.Type iface : ifaces) {
			ClassType genericIface = (ClassType) reflector.reflect(iface);
			types.add(genericIface.assignVariables(getDeclaredTypeParameters(), getActualTypeParameters()));
		}
		return Collections.unmodifiableList(types);
	}

	private ClassType initEnclosingClass() {
		return (ClassType) reflector.reflect(clazz.getEnclosingClass());
	}

	public List<TypeVariable> getTypeParameters() {
		return declaredTypeParameters.get();
	}

	public ClassType assignVariables(List<TypeVariable> variables, List<Type> values) {
		List<Type> actual = new ArrayList<Type>(getDeclaredTypeParameters().size());
		for (Type originalTypeArg : this.getActualTypeParameters()) {
			actual.add(originalTypeArg.assignVariables(variables, values));
		}

		return reflector.reflectGenericInvocation(this, actual, false);
	}

	public ClassType getGenericInvocation(List<Type> values) {
		return reflector.reflectGenericInvocation(this, values, false);
	}

	public ClassType getGenericInvocation(ClassType genericDeclaringClass) {
		// FIXME: attempt at creating a proper instance of a non-static inner class that lives inside a
		// generic invocation of its outer class
		return null;
	}

	public ClassType withErasure() {
		if (isErasure()) {
			return this;
		}
		// TODO: handle inner classes whose outer types are parameterized

		// calculate the erased types of the type parameters of this class
		List<Type> erasedTypeParameters = new ArrayList<Type>(this.getDeclaredTypeParameters().size());
		for (Type t : this.getDeclaredTypeParameters()) {
			erasedTypeParameters.add(t.withErasure());
		}

		return reflector.reflectGenericInvocation(this, erasedTypeParameters, true);
	}

	public boolean isErasure() {
		return this.isErasure;
	}

	public boolean isParameterizable() {
		return true;
	}

	public boolean isGenericInvocation() {
		// the cast to object is ugly, but it is due to another ugly cast when
		// initializing actualTypeParameters. It still works as expected.
		return (Object) this.declaredTypeParameters != this.actualTypeParameters;
	}

	public String declarationString() {
		// declaration
		StringBuilder s = new StringBuilder();
		if (this.isPublic()) {
			s.append("public ");
		}
		if (this.isAbstract()) {
			s.append("abstract ");
		}
		if (this.isFinal()) {
			s.append("final ");
		}
		if (this.isInterface()) {
			s.append("interface ");
		} else if (this.isEnum()) {
			s.append("enum ");
		} else {
			s.append("class ");
		}
		s.append(this.getGenericName().full());

		// extends
		ClassType superClazz = superClass.get();
		if (superClazz != null && !superClazz.getName().equals("java.lang.Object") && !this.isEnum()) {
			s.append(" extends ");
			s.append(superClazz.getName());
		}

		// implements
		List<ClassType> ifaces = interfaces.get();
		if (ifaces.size() > 0) {
			s.append(" implements ");
			for (int i = 0; i < ifaces.size(); i++) {
				if (i > 0) {
					s.append(", ");
				}
				s.append(ifaces.get(i).getName());
			}
		}

		return s.toString();
	}

	public boolean isPublic() {
		return Modifier.isPublic(clazz.getModifiers());
	}

	public boolean isAbstract() {
		return Modifier.isAbstract(clazz.getModifiers());
	}

	public boolean isFinal() {
		return Modifier.isFinal(clazz.getModifiers());
	}

	public boolean isInterface() {
		return clazz.isInterface();
	}

	public boolean isEnum() {
		return clazz.isEnum();
	}

	public TypeName getGenericName() {
		return new AbstractTypeName() {
			public String full() {
				return buildName(clazz.getName(), TypeName.Kind.FULL);
			}

			public String simple() {
				return buildName(clazz.getSimpleName(), TypeName.Kind.SIMPLE);
			}

			public String canonical() {
				return buildName(clazz.getCanonicalName(), TypeName.Kind.CANONICAL);
			}

			public String buildName(String className, TypeName.Kind kind) {
				if (actualTypeParameters.get().isEmpty() || DefaultClassType.this.isErasure()) {
					return className;
				}
				StringBuilder s = new StringBuilder();
				s.append(className);
				s.append('<');
				for (int i = 0; i < actualTypeParameters.get().size(); i++) {
					if (i > 0) {
						s.append(", ");
					}
					Type var = actualTypeParameters.get().get(i);
					if (isGenericInvocation() && var instanceof TypeVariable) {
						s.append(var.getGenericName().simple());
					} else {
						s.append(var.getGenericName().get(kind));
					}
				}
				s.append('>');
				return s.toString();
			}
		};
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + clazz.hashCode();
		result = prime * result + getActualTypeParameters().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		DefaultClassType other = (DefaultClassType) obj;
		return this.clazz.equals(other.clazz) && //
				this.getActualTypeParameters().equals(other.getActualTypeParameters());
	}

	public boolean isSameType(Type other) {
		if (this == other) {
			return true;
		}
		if (other == null || !(other instanceof ClassType)) {
			return false;
		}
		ClassType that = (ClassType) other;
		if (!this.getRawName().full().equals(that.getRawName().full())) {
			return false;
		}

		List<Type> thisParams = this.getActualTypeParameters();
		List<Type> thatParams = that.getActualTypeParameters();
		for (int i = 0; i < thisParams.size(); i++) {
			if (!thisParams.get(i).isSameType(thatParams.get(i))) {
				return false;
			}
		}
		return true;
	}

	public Methods methods() {
		return this.methods.get();
	}

	public ClassType getSuperClass() {
		return this.superClass.get();
	}

	public List<ClassType> getInterfaces() {
		return this.interfaces.get();
	}

	public List<TypeVariable> getDeclaredTypeParameters() {
		return declaredTypeParameters.get();
	}

	public List<Type> getActualTypeParameters() {
		return actualTypeParameters.get();
	}

	public boolean isInnerClass() {
		return clazz.getEnclosingClass() != null;
	}

	public ClassType getEnclosingClass() {
		return enclosingClass.get();
	}

	public ClassType getDeclaredClass() {
		return (ClassType) reflector.reflect(clazz);
	}

	public String getPackage() {
		return clazz.getPackage().getName();
	}

	public boolean isInnerClassOf(ClassType enclosing) {
		if (!this.isInnerClass()) {
			return false;
		}

		ClassType thisEnclosing = getEnclosingClass().getDeclaredClass();
		ClassType thatEnclosing = enclosing.getDeclaredClass();
		while (thisEnclosing != null) {
			if (thisEnclosing.equals(thatEnclosing)) {
				return true;
			}
			thisEnclosing = thisEnclosing.getEnclosingClass().getDeclaredClass();
		}
		return false;
	}

	public boolean isSuperTypeOf(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAssignableFrom(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSuperClassOf(ClassType that) {
		// base cases
		if (that.getDeclaredClass().equals(this.getDeclaredClass())) {
			return true;
		}
		if (that.getSuperClass() == null) {
			return false;
		}

		// recursion
		if (this.isSuperClassOf(that.getSuperClass())) {
			return true;
		}
		for (ClassType thatIface : that.getInterfaces()) {
			if (this.isSuperClassOf(thatIface)) {
				return true;
			}
		}
		return false;
	}
}
