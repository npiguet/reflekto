package org.nextflection;


public interface Methods extends Iterable<Method> {

	public Methods withAccess(Accesses accesses);
	public Methods withName(String name);
	public Methods withAbstract(boolean isAbstract);
	public Methods withFinal(boolean isFinal);
	public Methods withDeclaredInSuperTypes(boolean includeDeclaredInSuperTypes);

	public int size();
	public Method get(int index);
	public Method getMostSpecific(ClassType... parameterTypes);
	public Method getExact(ClassType... parameterTypes);
}
