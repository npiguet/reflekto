package reflekto;


public interface Member extends Parameterizable {

	public boolean isAccessibleFrom(ClassType clazz);
	public boolean isPublic();
	public boolean isPackageProtected();
	public boolean isProtected();
	public boolean isPrivate();
	public boolean isAbstract();
	public boolean isFinal();
	public AccessModifier getAccessModifier();
	public ClassType getDeclaringClass();
	public Member withErasure();
	public String declarationString();
	public String getName();
}
