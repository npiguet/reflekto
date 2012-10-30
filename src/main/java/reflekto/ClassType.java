package reflekto;

import java.util.List;

public interface ClassType extends ObjectType, GenericDeclaration {

	public boolean isPublic();
	public boolean isAbstract();
	public boolean isFinal();
	public boolean isInterface();
	public Methods methods();
	public boolean isGenericInvocation();
	public boolean isInnerClass();
	public boolean isInnerClassOf(ClassType enclosing);
	public ClassType getEnclosingClass();
	public ClassType getDeclaredClass();
	public ClassType getSuperClass();
	public boolean isSuperClassOf(ClassType that);
	public List<ClassType> getInterfaces();
	public List<Type> getActualTypeParameters();
	public ClassType getGenericInvocation(List<Type> values);
	public ClassType assignVariables(List<TypeVariable> variables, List<Type> values);
	// FIXME: attempt at creating a proper instance of a non-static inner class that lives inside a
	//        generic invocation of its outer class
	public ClassType getGenericInvocation(ClassType genericDeclaringClass);
	// TODO: use a proper Package object
	public String getPackage();
}
