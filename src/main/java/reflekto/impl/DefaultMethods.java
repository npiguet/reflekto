package reflekto.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import reflekto.ClassType;
import reflekto.Method;
import reflekto.Methods;

public class DefaultMethods extends AbstractMemberCollection<Method, Methods> implements Methods {

	public DefaultMethods(Collection<? extends Method> declared, Collection<? extends Methods> allInherited){
		super(true, declared, allInherited);
	}

	private DefaultMethods(Collection<? extends Method> methods){
		super(true, methods, Collections.<Methods>emptyList());
	}

	@Override
	protected boolean canInheritedMemberBeAdded(Method method){
		return this.getOverriding(method) == null && !method.isPrivate();
	}

	@Override
	protected Methods buildSubset(Collection<? extends Method> subset){
		return new DefaultMethods(subset);
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
		if(sameName.isEmpty()){
			return null;
		}
		for(Method m : sameName){
			if(m.overrides(overridden)){
				return m;
			}
		}
		return null;
	}

	public Method getOverridden(Method overriding) {
		List<Method> sameName = withNameAsList(overriding.getName());
		if(sameName.isEmpty()){
			return null;
		}
		for(Method m : sameName){
			if(overriding.overrides(m)){
				return m;
			}
		}
		return null;
	}
}
