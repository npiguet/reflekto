package reflekto.impl;

import reflekto.AbstractElement;
import reflekto.AbstractTypeName;
import reflekto.FullReflector;
import reflekto.Type;
import reflekto.TypeName;

public abstract class AbstractType extends AbstractElement implements Type {

	protected final Class<?> clazz;

	public AbstractType(Class<?> clazz, FullReflector creator) {
		super(creator);
		this.clazz = clazz;
	}

	public String getName(){
		return getGenericName().full();
	}

	@Override
	public String toString() {
		return getName();
	}

	public TypeName getRawName() {
		return new AbstractTypeName(){

			public String full() {
				return clazz.getName();
			}

			public String simple() {
				return clazz.getSimpleName();
			}

			public String canonical() {
				return clazz.getCanonicalName();
			}
		};
	}

	public boolean isPrimitive() {
		return clazz.isPrimitive();
	}
}
