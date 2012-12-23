package reflekto.impl;

import java.util.List;

import reflekto.AccessModifier;
import reflekto.ClassType;
import reflekto.Constructor;
import reflekto.Type;
import reflekto.TypeVariable;

public class DefaultConstructor extends AbstractMember implements Constructor {

	private final java.lang.reflect.Constructor<?> constructor;

	public DefaultConstructor(java.lang.reflect.Constructor<?> constructor, ClassType declaringClass, FullReflector reflector) {
		super(declaringClass, reflector);
		this.constructor = constructor;
	}

	public Constructor withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}

	public Constructor assignVariables(List<TypeVariable> variable, List<Type> value){
		// TODO Auto-generated method stub
		return null;
	}

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isAccessibleFrom(ClassType clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPublic() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPackageProtected() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isProtected() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPrivate() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAbstract() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isFinal() {
		// TODO Auto-generated method stub
		return false;
	}

	public AccessModifier getAccessModifier() {
		// TODO Auto-generated method stub
		return null;
	}
}
