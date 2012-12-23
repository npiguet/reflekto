package reflekto;



public interface Methods extends MemberCollection<Method, Methods> {

	public Method getMostSpecific(ClassType... parameterTypes);
	public Method getExact(ClassType... parameterTypes);
	public Method getOverriding(Method overriden);
	public Method getOverridden(Method overriding);

	public static interface Filter extends MemberCollection.Filter<Method>{}
}
