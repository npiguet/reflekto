package org.nextflection.impl;

public class FinalReference<T> implements ReadOnlyReference<T> {

	private final T value;

	public FinalReference(T value){
		this.value = value;
	}

	public T get() {
		return value;
	}
}
