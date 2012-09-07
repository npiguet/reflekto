package org.nextflection;

public interface Erasable<T extends Erasable> {

	public T withErasure();

	public boolean isErasure();
}
