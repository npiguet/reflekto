package org.nextflection.impl;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.Constructor;
import org.nextflection.Field;
import org.nextflection.Method;
import org.nextflection.Type;
import org.nextflection.TypeName;
import org.nextflection.TypeVariable;

public class DefaultClassType extends AbstractType implements ClassType {

	private final ReadOnlyReference<List<TypeVariable>> typeParameters;
	private final ReadOnlyReference<List<Method>> methods;
	private final ReadOnlyReference<List<Field>> fields;
	private final ReadOnlyReference<List<Constructor>> constructors;

	public DefaultClassType(Class<?> clazz, FullReflector creator) {
		super(clazz, creator);
		this.typeParameters = new LazyInit<List<TypeVariable>>(){
			@Override
			protected List<TypeVariable> init() {
				return initTypeParameters();
			}
		};
		this.methods = new LazyInit<List<Method>>(){
			@Override
			protected List<Method> init() {
				return initMethods();
			}
		};
		this.fields = new LazyInit<List<Field>>(){
			@Override
			protected List<Field> init() {
				return initFields();
			}
		};
		this.constructors = new LazyInit<List<Constructor>>(){
			@Override
			protected List<Constructor> init() {
				return initConstructors();
			}
		};
	}

	/**
	 * Builds a copy of the original DefaultClassType, replacing the list of type parameters, methods, constructors and fields iif the specified
	 * one is not null.
	 */
	protected DefaultClassType(DefaultClassType original, List<TypeVariable> newTypeParameters, List<Method> newMethods, List<Field> newFields,
			List<Constructor> newConstructors) {
		super(original.clazz, original.reflector);
		//		if (newTypeParameters != null) {
		//			this.typeParameters = Collections.unmodifiableList(newTypeParameters);
		//		} else {
		//			this.typeParameters = original.typeParameters;
		//		}
		//		if (newMethods != null) {
		//			this.methods = Collections.unmodifiableList(newMethods);
		//		} else {
		//			this.methods = original.methods;
		//		}
		//		if (newFields != null) {
		//			this.fields = Collections.unmodifiableList(newFields);
		//		} else {
		//			this.fields = original.fields;
		//		}
		//		if (newConstructors != null) {
		//			this.constructors = Collections.unmodifiableList(newConstructors);
		//		} else {
		//			this.constructors = original.constructors;
		//		}
		this.typeParameters = null;
		this.methods = null;
		this.fields = null;
		this.constructors = null;
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
		java.lang.reflect.Constructor<?>[] langConstructors = clazz.getDeclaredConstructors();
		List<Constructor> cons = new ArrayList<Constructor>(langConstructors.length);
		for (java.lang.reflect.Constructor<?> c : langConstructors) {
			cons.add(reflector.reflect(c, this));
		}
		return Collections.unmodifiableList(cons);
	}

	private List<Field> initFields() {
		java.lang.reflect.Field[] langFields = clazz.getDeclaredFields();
		List<Field> flds = new ArrayList<Field>(langFields.length);
		for (java.lang.reflect.Field f : langFields) {
			flds.add(reflector.reflect(f, this));
		}
		return Collections.unmodifiableList(flds);
	}

	private List<Method> initMethods() {
		java.lang.reflect.Method[] langMethods = clazz.getDeclaredMethods();
		List<Method> meths = new ArrayList<Method>(langMethods.length);
		for (java.lang.reflect.Method m : langMethods) {
			meths.add(reflector.reflect(m, this));
		}
		return Collections.unmodifiableList(meths);
	}

	public List<TypeVariable> getTypeParameters() {
		return typeParameters.get();
	}

	public ClassType withTypeArguments(List<TypeVariable> variables, List<Type> values){
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType withTypeArguments(List<Type> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType withErasure() {
		//		List<Field> newFields = new ArrayList<Field>(fields.size());
		//		List<Constructor> newConstructors = new ArrayList<Constructor>(fields.size());
		//		List<Method> newMethods = new ArrayList<Method>(methods.size());
		//		List<TypeVariable> noTypeVariables = Collections.emptyList();
		//
		//		for (Field f : fields) {
		//			newFields.add(f.withErasure());
		//		}
		//		for (Constructor c : constructors) {
		//			newConstructors.add(c.withErasure());
		//		}
		//		for (Method m : methods) {
		//			newMethods.add(m.withErasure());
		//		}
		//
		//		return reflector.buildCopy(this, noTypeVariables, newFields, newConstructors, newMethods);
		return null;
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isParameterizable() {
		return true;
	}

	@Override
	public String toString() {
		return clazz.toString();
	}

	public String declarationString() {
		// declaration
		StringBuilder s = new StringBuilder();
		if(this.isPublic()){
			s.append("public ");
		}
		if(this.isAbstract()){
			s.append("abstract ");
		}
		if(this.isFinal()){
			s.append("final ");
		}
		if(this.isInterface()){
			s.append("interface ");
		}else {
			s.append("class ");
		}
		s.append(this.getGenericName().full());

		// extends
		// TODO: use methods from the ClassType interface instead of Class
		Class<?> superClass = clazz.getSuperclass();
		if(superClass != null && superClass != Object.class){
			s.append(" extends ");
			s.append(superClass.getName());
		}

		// implements
		// TODO: use methods from the ClassType interface instead of Class
		Class<?>[] ifaces = clazz.getInterfaces();
		if(ifaces.length > 0){
			s.append(" implements ");
			for(int i = 0; i < ifaces.length; i ++){
				if(i > 0){
					s.append(", ");
				}
				s.append(ifaces[i].getName());
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

	public TypeName getGenericName() {
		return new AbstractTypeName(){
			public String full() {
				return buildName(clazz.getName(), TypeName.Kind.FULL);
			}

			public String simple() {
				return buildName(clazz.getSimpleName(), TypeName.Kind.SIMPLE);
			}

			public String canonical() {
				return buildName(clazz.getCanonicalName(), TypeName.Kind.CANONICAL);
			}

			public String buildName(String className, TypeName.Kind kind){
				if(typeParameters.get().isEmpty()){
					return className;
				}
				StringBuilder s = new StringBuilder();
				s.append(className);
				s.append('<');
				for(int i = 0; i < typeParameters.get().size(); i ++){
					if(i > 0){
						s.append(", ");
					}
					TypeVariable var = typeParameters.get().get(i);
					s.append(var.getGenericName().get(kind));
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
		result = prime * clazz.hashCode();
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
		return this.clazz.equals(other.clazz);
	}
}
