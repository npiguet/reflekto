package reflekto;

import java.util.List;

public interface Method extends Member, GenericDeclaration {

	public Method withErasure();
	public List<Type> getDeclaredParameterTypes();
	public List<Type> getActualParameterTypes();
	public List<TypeVariable> getDeclaredTypeParameters();
	public List<Type> getActualTypeParameters();
	public Type getDeclaredReturnType();
	public Type getActualReturnType();
	public Type getInferredReturnType(List<Type> argumentTypes);
	public AccessModifier getAccessModifier();
	public Method assignTypeVariables(List<TypeVariable> vars, List<Type> values);
	public boolean isAccessibleFrom(ClassType clazz);
	public boolean isPublic();
	public boolean isPackageProtected();
	public boolean isProtected();
	public boolean isPrivate();
	public boolean isAbstract();
	public boolean isFinal();
	public boolean isSubsignature(Method that);
	public boolean overrides(Method that);

}
