package reflekto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Methods extends MemberCollection<Method, Methods> {

	public Methods(Collection<? extends Method> declared, Collection<? extends Methods> allInherited) {
		super(true, declared, allInherited);
	}

	private Methods(Collection<? extends Method> methods) {
		super(true, methods, Collections.<Methods> emptyList());
	}

	@Override
	protected boolean canInheritedMemberBeAdded(Method method) {
		return this.getOverriding(method) == null && !method.isPrivate();
	}

	@Override
	protected Methods buildSubset(Collection<? extends Method> subset) {
		return new Methods(subset);
	}

	public Method getMostSpecific(ClassType... parameterTypes) {
		// TODO Auto-generated method stub
		return null;
	}

	public Method getExact(ClassType... parameterTypes) {
		// TODO Auto-generated method stub
		return null;
	}

	public Method getOverriding(Method overridden) {
		List<Method> sameName = withNameAsList(overridden.getName());
		if (sameName.isEmpty()) {
			return null;
		}
		for (Method m : sameName) {
			if (m.overrides(overridden)) {
				return m;
			}
		}
		return null;
	}

	public Method getOverridden(Method overriding) {
		List<Method> sameName = withNameAsList(overriding.getName());
		if (sameName.isEmpty()) {
			return null;
		}
		for (Method m : sameName) {
			if (overriding.overrides(m)) {
				return m;
			}
		}
		return null;
	}

	public static interface Filter extends MemberCollection.Filter<Method> {
	}
}
