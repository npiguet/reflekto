package reflekto;

import java.util.List;


public class Field extends Member {

	private final java.lang.reflect.Field field;
	private Type type;

	public Field(java.lang.reflect.Field field, ClassType declaringClass, FullReflector reflector) {
		super(declaringClass, reflector);
		this.field = field;
	}

	@Override
	protected int getJavaModifiers() {
		return field.getModifiers();
	}

	@Override
	public String getName() {
		return field.getName();
	}

	public boolean isErasure() {
		return type.isErasure();
	}

	public Field assignVariables(List<TypeVariable> variable, List<Type> value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Field withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isAccessibleFromInternal(ClassType caller) {
		// TODO Auto-generated method stub
		return false;
	}
}
