package reflekto;

import java.util.List;

public class ArrayType extends AbstractElement implements ObjectType {

	private final java.lang.reflect.Type jElementType;
	private final ReadOnlyReference<Type> elementType;

	public ArrayType(final java.lang.reflect.Type jElementType, FullReflector creator) {
		super(creator);
		this.jElementType = jElementType;
		this.elementType = new LazyInit<Type>() {
			@Override
			protected Type init() {
				return reflector.reflect(jElementType);
			}
		};
	}

	/**
	 * Builds an erasure version of the specified original array type.
	 */
	public ArrayType(final ArrayType original) {
		super(original.reflector);
		this.jElementType = original.jElementType;
		this.elementType = new LazyInit<Type>() {
			@Override
			protected Type init() {
				return original.getElementType().withErasure();
			}
		};
	}

	public ArrayType(ArrayType original, Type elementType) {
		super(original.reflector);
		this.jElementType = original.jElementType;
		this.elementType = new FinalReference<Type>(elementType);
	}

	public ArrayType withErasure() {
		if (this.isErasure()) {
			return this;
		}
		return reflector.reflectErasure(this);
	}

	public boolean isErasure() {
		return getElementType().isErasure();
	}

	@Override
	public String toString() {
		return getElementType().toString() + "[]";
	}

	public String declarationString() {
		return toString();
	}

	public TypeName getRawName() {
		return getGenericName();
	}

	public TypeName getGenericName() {
		return new AbstractTypeName() {
			public String full() {
				return buildName(TypeName.Kind.FULL);
			}

			public String simple() {
				return buildName(TypeName.Kind.SIMPLE);
			}

			public String canonical() {
				return buildName(TypeName.Kind.CANONICAL);
			}

			private String buildName(TypeName.Kind kind) {
				Type t = ArrayType.this.getElementType();
				String elementTypeName;
				if (t instanceof TypeVariable) {
					elementTypeName = t.getGenericName().simple();
				} else {
					elementTypeName = t.getGenericName().get(kind);
				}
				return elementTypeName + "[]";
			}
		};
	}

	public ArrayType assignVariables(List<TypeVariable> variables, List<Type> values) {
		Type assignedElementType = getElementType().assignVariables(variables, values);
		if (getElementType().equals(assignedElementType)) {
			return this;
		}
		return reflector.reflectGenericInvocation(this, assignedElementType);
	}

	public boolean isSuperTypeOf(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAssignableFrom(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSameType(Type other) {
		if (this == other) {
			return true;
		}
		if (other == null || !(other instanceof ArrayType)) {
			return false;
		}
		ArrayType that = (ArrayType) other;
		return that.getElementType().isSameType(this.getElementType());
	}

	public boolean isPrimitive() {
		return false;
	}

	public String getName() {
		return toString();
	}

	public Type getElementType() {
		return elementType.get();
	}
}
