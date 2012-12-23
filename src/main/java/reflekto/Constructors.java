package reflekto;


public interface Constructors extends MemberCollection<Constructor, Constructors> {

	public Constructor getMostSpecific(ClassType... parameterTypes);
	public Constructor getExact(ClassType... parameterTypes);

	public static interface Filter extends MemberCollection.Filter<Constructor>{}
}
