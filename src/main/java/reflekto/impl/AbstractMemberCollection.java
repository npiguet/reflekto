package reflekto.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import reflekto.AccessFilter;
import reflekto.Member;
import reflekto.MemberCollection;

public abstract class AbstractMemberCollection<E extends Member, C extends MemberCollection<E, C>> implements MemberCollection<E, C> {

	private final Map<String, List<E>> byName;
	private final List<E> iterable = new ArrayList<E>();

	public AbstractMemberCollection(boolean named, Collection<? extends E> declared, Collection<? extends C> allInherited){
		if(named){
			byName = new HashMap<String, List<E>>();
		} else {
			byName = null;
		}

		for(E e : declared){
			put(e);
		}

		// TODO: what is the order of member iheritance? Does the superClass have priority over interfaces?
		for(C oneInherited : allInherited){
			for(E inherited : oneInherited){
				if(!inherited.isPrivate() && canInheritedMemberBeAdded(inherited)){
					put(inherited);
				}
			}
		}
	}

	protected abstract boolean canInheritedMemberBeAdded(E member);
	protected abstract C buildSubset(Collection<? extends E> subset);

	protected void put(E m){
		if(byName != null){
			List<E> list = this.byName.get(m.getName());
			if(list == null){
				list = new ArrayList<E>();
				byName.put(m.getName(), list);
			}
			list.add(m);
		}
		iterable.add(m);
	}

	public Iterator<E> iterator() {
		return iterable.iterator();
	}

	public C withFilter(MemberCollection.Filter<E> filter){
		return buildSubset(filter(filter));
	}

	protected List<E> filter(MemberCollection.Filter<E> filter){
		List<E> filtered = new ArrayList<E>();
		for(E e : this){
			if(filter.accepts(e)){
				filtered.add(e);
			}
		}
		return filtered;
	}

	public C withName(final String name) {
		return buildSubset(withNameAsList(name));
	}

	protected List<E> withNameAsList(final String name){
		if(byName != null){
			// here we don't use withFilter, because our methods are already sorted by name
			List<E> list = byName.get(name);
			if(list == null){
				list = Collections.emptyList();
			}
			return list;
		}
		return filter(new MemberCollection.Filter<E>(){
			public boolean accepts(E e) {
				return e.getName().equals(name);
			}
		});
	}

	public C withAccess(final AccessFilter accessFilter) {
		return withFilter(new MemberCollection.Filter<E>(){
			public boolean accepts(E e) {
				return accessFilter.accepts(e.getAccessModifier());
			}
		});
	}

	public C withAbstract(final boolean isAbstract) {
		return withFilter(new MemberCollection.Filter<E>(){
			public boolean accepts(E e) {
				return e.isAbstract() == isAbstract;
			}
		});
	}

	public C withFinal(final boolean isFinal) {
		return withFilter(new MemberCollection.Filter<E>(){
			public boolean accepts(E e) {
				return e.isFinal() == isFinal;
			}
		});
	}

	public C withInherited(boolean includeDeclaredInSuperTypes) {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		return iterable.size();
	}

	public E get(int index) {
		return iterable.get(index);
	}

	public List<E> asList(){
		return new ArrayList<E>(iterable);
	}

	public C withStatic(boolean isStatic) {
		// TODO Auto-generated method stub
		return null;
	}

	public E get() {
		if(this.size() == 0){
			return null;
		} else if (this.size() == 1){
			return this.get(0);
		}
		throw new IllegalStateException("This collection contains more than one object (" + this.size() + ")");
	}
}
