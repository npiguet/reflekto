package reflekto;

import java.util.List;

public class PrimitiveType extends AbstractType implements Type {

	public PrimitiveType(Class<?> clazz, FullReflector creator) {
		super(clazz, creator);
	}

	public Type withErasure() {
		return this;
	}

	public boolean isErasure() {
		return true;
	}

	public Type assignVariables(List<TypeVariable> variable, List<Type> value) {
		return this;
	}

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeName getRawName() {
		return new AbstractTypeName() {
			public String full() {
				return clazz.getName();
			}

			public String simple() {
				return clazz.getName();
			}

			public String canonical() {
				return clazz.getName();
			}
		};
	}

	public TypeName getGenericName() {
		return getRawName();
	}

	@Override
	public int hashCode() {
		final int prime = 29;
		int result = 1;
		result = prime * result + clazz.hashCode();
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
		PrimitiveType other = (PrimitiveType) obj;
		return this.clazz.equals(other.clazz);
	}

	public boolean isSuperTypeOf(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAssignableFrom(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSameType(Type that) {
		return this.equals(that);
	}
}
