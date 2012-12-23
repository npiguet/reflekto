package reflekto;

import java.util.List;


public interface MemberCollection<E extends Member, C extends MemberCollection<E, C>> extends Iterable<E> {

	C withAccess(AccessFilter accessFilter);
	C withName(String name);
	C withAbstract(boolean isAbstract);
	C withFinal(boolean isFinal);
	C withInherited(boolean inherited);
	C withStatic(boolean isStatic);
	C withFilter(Filter<E> filter);
	int size();
	E get(int index);
	E get();
	List<E> asList();

	public interface Filter<T> {
		public boolean accepts(T t);
	}

}
