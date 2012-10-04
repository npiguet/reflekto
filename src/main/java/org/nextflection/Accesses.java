package org.nextflection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Accesses {
	public static final Accesses PRIVATE = new Accesses();
	public static final Accesses PACKAGE = new Accesses();
	public static final Accesses PROTECTED = new Accesses();
	public static final Accesses PUBLIC = new Accesses();
	private static final Accesses[] BASIC = new Accesses[]{PRIVATE, PACKAGE, PROTECTED, PUBLIC};

	private final Collection<Accesses> members;

	private Accesses(){
		this.members = Collections.singleton(this);
	}

	private Accesses(Collection<Accesses> members){
		this.members = members;
	}

	public boolean containsAll(Accesses other){
		return this.members.containsAll(other.members);
	}

	public Collection<Accesses> all(){
		return this.members;
	}

	private Accesses strictest(){
		for(int i = 0; i < BASIC.length; i ++){
			if(this.members.contains(BASIC[i])){
				return BASIC[i];
			}
		}
		throw new IllegalStateException();
	}

	private Accesses mostLenient(){
		for(int i = BASIC.length-1; i >= 0; i --){
			if(this.members.contains(BASIC[i])){
				return BASIC[i];
			}
		}
		throw new IllegalStateException();
	}

	public static Accesses stricterThan(Accesses original){
		Accesses strictest = original.strictest();
		Set<Accesses> members = new HashSet<Accesses>();
		for(int i = 0; i < BASIC.length; i ++){
			members.add(BASIC[i]);
			if(BASIC[i].equals(strictest)){
				break;
			}
		}
		return new Accesses(Collections.unmodifiableSet(members));
	}

	public static Accesses moreLenientThan(Accesses accesses){
		Accesses mostLenient = accesses.mostLenient();
		Set<Accesses> members = new HashSet<Accesses>();
		for(int i = BASIC.length - 1; i >= 0; i--){
			members.add(BASIC[i]);
			if(BASIC[i].equals(mostLenient)){
				break;
			}
		}
		return new Accesses(Collections.unmodifiableSet(members));
	}
}
