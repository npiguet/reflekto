package org.nextflection.impl;

import java.util.concurrent.atomic.AtomicReference;

public abstract class LazyInit<T> implements ReadOnlyReference<T> {

	private volatile boolean initialized = false;
	private final AtomicReference<T> ref = new AtomicReference<T>();

	public T get(){
		if(!initialized){
			T value = init();
			// if two threads managed to entered here, make sure that
			// only one gets to set the value
			ref.compareAndSet(null, value);
			initialized = true;
		}
		// must use ref.get() here, to make sure we read the correct value
		// from the AtomicReference in case we had two threads in the block above
		return ref.get();
	}

	protected abstract T init();

}
