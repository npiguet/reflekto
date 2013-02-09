package reflekto;

import java.util.List;

import reflekto.impl.FullReflector;

public class Constructor extends CallableMember {

	private final java.lang.reflect.Constructor<?> constructor;

	public Constructor(java.lang.reflect.Constructor<?> constructor, ClassType declaringClass, FullReflector reflector) {
		super(declaringClass, reflector);
		this.constructor = constructor;
	}

	@Override
	protected int getJavaModifiers() {
		return constructor.getModifiers();
	}

	public Constructor withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}

	public Constructor assignVariables(List<TypeVariable> variable, List<Type> value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isAccessibleFromInternal(ClassType caller) {
		// TODO Auto-generated method stub
		return false;
	}
}
