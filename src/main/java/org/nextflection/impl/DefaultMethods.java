package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.nextflection.AccessFilter;
import org.nextflection.ClassType;
import org.nextflection.Method;
import org.nextflection.Methods;

public class DefaultMethods implements Methods {

	private final Map<String, List<Method>> methodsByName = new HashMap<String, List<Method>>();
	private final List<Method> methods = new ArrayList<Method>();

	public DefaultMethods(Collection<? extends Method> methods, Collection<? extends Methods> superTypeMethods){
		for(Method m : methods){
			putMethod(m);
		}

		// TODO: add the subtype methods if an overridden method doesn't already exists
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

	public Methods withAccess(AccessFilter accessFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	public Methods withName(String name) {
		List<Method> filtered = new ArrayList<Method>();
		for(Method m : methods){
			if(m.getName().equals(name)){
				filtered.add(m);
			}
		}
		return new DefaultMethods(filtered, Collections.<Methods>emptyList());
	}

	public Methods withAbstract(boolean isAbstract) {
		// TODO Auto-generated method stub
		return null;
		// TODO: code it
	}

	public Methods withFinal(boolean isFinal) {
		// TODO Auto-generated method stub
		return null;
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
}
