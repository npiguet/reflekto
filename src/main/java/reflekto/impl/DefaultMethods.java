package reflekto.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import reflekto.AccessFilter;
import reflekto.ClassType;
import reflekto.Method;
import reflekto.Methods;

public class DefaultMethods implements Methods {

	private final Map<String, List<Method>> methodsByName = new HashMap<String, List<Method>>();
	private final List<Method> methods = new ArrayList<Method>();

	public DefaultMethods(Collection<? extends Method> methods, Collection<? extends Methods> superTypeMethods){
		for(Method m : methods){
			putMethod(m);
		}

		// TODO: what is the order of method iheritance? Does the superClass have priority over interfaces?
		for(Methods superMeths : superTypeMethods){
			for(Method superMeth : superMeths){
				if(this.getOverriding(superMeth) == null){
					putMethod(superMeth);
				}
			}
		}
	}

	public DefaultMethods(Collection<? extends Method> methods){
		this(methods, Collections.<Methods>emptyList());
	}

	private void putMethod(Method m){
		List<Method> list = this.methodsByName.get(m.getName());
		if(list == null){
			list = new ArrayList<Method>();
			methodsByName.put(m.getName(), list);
		}
		list.add(m);
		methods.add(m);
	}

	public Iterator<Method> iterator() {
		return methods.iterator();
	}

	public Methods withFilter(Filter filter){
		List<Method> filtered = new ArrayList<Method>();
		for(Method m : methods){
			if(filter.accepts(m)){
				filtered.add(m);
			}
		}
		return new DefaultMethods(filtered);
	}

	public Methods withAccess(final AccessFilter accessFilter) {
		return withFilter(new Filter(){
			public boolean accepts(Method m) {
				return accessFilter.accepts(m.getAccessModifier());
			}
		});
	}

	public Methods withName(final String name) {
		// here we don't use withFilter, because our methods are already sorted by name
		List<Method> list = methodsByName.get(name);
		if(list == null){
			list = Collections.emptyList();
		}
		return new DefaultMethods(list);
	}

	public Methods withAbstract(final boolean isAbstract) {
		return withFilter(new Filter(){
			public boolean accepts(Method m) {
				return m.isAbstract() == isAbstract;
			}
		});
	}

	public Methods withFinal(final boolean isFinal) {
		return withFilter(new Filter(){
			public boolean accepts(Method m) {
				return m.isFinal() == isFinal;
			}
		});
	}

	public Methods withInherited(boolean includeDeclaredInSuperTypes) {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		return methods.size();
	}

	public Method get(int index) {
		return methods.get(index);
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
		List<Method> sameName = methodsByName.get(overridden.getName());
		if(sameName == null){
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
		List<Method> sameName = methodsByName.get(overriding.getName());
		if(sameName == null){
			return null;
		}
		for(Method m : sameName){
			if(overriding.overrides(m)){
				return m;
			}
		}
		return null;
	}

	public List<Method> asList(){
		return new ArrayList<Method>(methods);
	}
}
