package org.nextflection.impl;

import java.util.concurrent.atomic.AtomicReference;

public abstract class LazyInit<T> implements ReadOnlyReference<T> {

	private  final AtomicReference<T> ref = new AtomicReference<T>();

	public T get(){
		if(ref.get() == null){
			T value = init();
			// if two threads managed to entered here, make sure that
			// only one gets to set the value
			ref.compareAndSet(null, value);
		}
		// must use ref.get() here, to make sure we read the correct value
		// from the AtomicReference in case we had two threads in the block above
		return ref.get();
	}

	protected abstract T init();

}
