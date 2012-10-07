package org.nextflection;

public enum AccessModifier {
	PRIVATE, PACKAGE, PROTECTED, PUBLIC;

	public static AccessModifier fromModifier(int jModifier){
		if(java.lang.reflect.Modifier.isPrivate(jModifier)){
			return PRIVATE;
		}
		if(java.lang.reflect.Modifier.isProtected(jModifier)){
			return PROTECTED;
		}
		if(java.lang.reflect.Modifier.isPublic(jModifier)){
			return PUBLIC;
		}
		return PACKAGE;
	};
}
