package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.nextflection.AccessFilter;
import org.nextflection.ClassType;
import org.nextflection.Method;
import org.nextflection.Methods;

public class DefaultMethods implements Methods {

	private final List<Method> methods;

	public DefaultMethods(Collection<? extends Method> methods, Collection<? extends Methods> superTypeMethods){
		this.methods = new ArrayList<Method>(methods);
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
	}

	public Methods withFinal(boolean isFinal) {
		// TODO Auto-generated method stub
		return null;
	}

	public Methods withDeclaredInSuperTypes(boolean includeDeclaredInSuperTypes) {
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

	public ClassType getOwnerType() {
		// TODO Auto-generated method stub
		return null;
	}

}
