package reflekto.impl;

import java.lang.reflect.Modifier;

import reflekto.AccessFilter;
import reflekto.AccessModifier;
import reflekto.ClassType;
import reflekto.Member;

public abstract class AbstractMember extends AbstractElement implements Member {

	protected final ClassType declaringClass;

	public AbstractMember(ClassType declaringClass, FullReflector reflector) {
		super(reflector);
		this.declaringClass = declaringClass;
	}

	protected abstract int getJavaModifiers();
	protected abstract boolean isAccessibleFromInternal(ClassType caller);

	public ClassType getDeclaringClass() {
		return declaringClass;
	}

	public boolean isPublic() {
		return getAccessModifier().equals(AccessModifier.PUBLIC);
	}

	public boolean isPackageProtected() {
		return getAccessModifier().equals(AccessModifier.PACKAGE);
	}

	public boolean isProtected() {
		return getAccessModifier().equals(AccessModifier.PROTECTED);
	}

	public boolean isPrivate() {
		return getAccessModifier().equals(AccessModifier.PRIVATE);
	}
	public boolean isAbstract() {
		return Modifier.isAbstract(getJavaModifiers());
	}

	public boolean isFinal() {
		return Modifier.isFinal(getJavaModifiers());
	}

	public boolean isStatic(){
		return Modifier.isStatic(getJavaModifiers());
	}

	public AccessModifier getAccessModifier() {
		return AccessModifier.fromModifier(getJavaModifiers());
	}

	public boolean isAccessibleFrom(ClassType caller) {
		// caller is the same class as declaringClass
		// PRIVATE is visible
		if(this.declaringClass.getDeclaredClass().equals(caller.getDeclaredClass())){
			return AccessFilter.PRIVATE.andMoreLenient().accepts(getAccessModifier());
		}

		// caller is an inner class (at any depth) of declaringClass, or vice-versa
		// PRIVATE is visible
		if(declaringClass.isInnerClassOf(caller) || caller.isInnerClassOf(declaringClass)){
			return AccessFilter.PRIVATE.andMoreLenient().accepts(getAccessModifier());
		}

		// caller is in the same package as declaringClass
		// PACKAGE is visible
		if(this.declaringClass.getPackage().equals(caller.getPackage())){
			return AccessFilter.PACKAGE.andMoreLenient().accepts(getAccessModifier());
		}

		// caller is a subType of declaringClass
		// PROTECTED is visible
		if(declaringClass.isSuperClassOf(caller)){
			return AccessFilter.PROTECTED.andMoreLenient().accepts(getAccessModifier());
		}

		if(isAccessibleFromInternal(caller)){
			return true;
		}
		// caller is a superType of declaringClass
		// AND and this method overrides a method in caller
		// PROTECTED is visible
		//		if(caller.isSuperClassOf(declaringClass) && //
		//				caller.methods().getOverridden(this) != null) {
		//			return AccessFilter.PROTECTED.andMoreLenient().accepts(getAccessModifier());
		//		}

		// caller has no relation to declaringClass
		// PUBLIC is visible
		return AccessFilter.PUBLIC.accepts(getAccessModifier());
	}
}
