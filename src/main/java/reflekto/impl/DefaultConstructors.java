package reflekto.impl;

import java.util.Collection;
import java.util.Collections;

import reflekto.ClassType;
import reflekto.Constructor;
import reflekto.Constructors;

public class DefaultConstructors extends AbstractMemberCollection<Constructor, Constructors> implements Constructors {

	public DefaultConstructors(Collection<? extends Constructor> declared) {
		super(false, declared, Collections.<Constructors>emptyList());
	}

	@Override
	protected boolean canInheritedMemberBeAdded(Constructor member) {
		return false;
	}

	@Override
	protected Constructors buildSubset(Collection<? extends Constructor> subset) {
		return new DefaultConstructors(subset);
	}


	public Constructor getMostSpecific(ClassType... parameterTypes) {
		// TODO Auto-generated method stub
		return null;
	}

	public Constructor getExact(ClassType... parameterTypes) {
		// TODO Auto-generated method stub
		return null;
	}
}
