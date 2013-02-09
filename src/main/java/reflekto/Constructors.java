package reflekto;

import java.util.Collection;
import java.util.Collections;

public class Constructors extends MemberCollection<Constructor, Constructors> {

	public Constructors(Collection<? extends Constructor> declared) {
		super(false, declared, Collections.<Constructors> emptyList());
	}

	@Override
	protected boolean canInheritedMemberBeAdded(Constructor member) {
		return false;
	}

	@Override
	protected Constructors buildSubset(Collection<? extends Constructor> subset) {
		return new Constructors(subset);
	}

	public Constructor getMostSpecific(ClassType... parameterTypes) {
		// TODO Auto-generated method stub
		return null;
	}

	public Constructor getExact(ClassType... parameterTypes) {
		// TODO Auto-generated method stub
		return null;
	}

	public static interface Filter extends MemberCollection.Filter<Constructor> {
	}
}