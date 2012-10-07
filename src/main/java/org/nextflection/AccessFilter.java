package org.nextflection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccessFilter {
	public static final AccessFilter PRIVATE = new AccessFilter();
	public static final AccessFilter PACKAGE = new AccessFilter();
	public static final AccessFilter PROTECTED = new AccessFilter();
	public static final AccessFilter PUBLIC = new AccessFilter();
	private static final AccessFilter[] BASIC = new AccessFilter[]{PRIVATE, PACKAGE, PROTECTED, PUBLIC};

	private static final Map<AccessFilter, AccessModifier> MODIFIERS = new HashMap<AccessFilter, AccessModifier>();
	static {
		MODIFIERS.put(PRIVATE, AccessModifier.PRIVATE);
		MODIFIERS.put(PACKAGE, AccessModifier.PACKAGE);
		MODIFIERS.put(PROTECTED, AccessModifier.PROTECTED);
		MODIFIERS.put(PUBLIC, AccessModifier.PUBLIC);
	}

	private final Collection<AccessFilter> members;

	private AccessFilter(){
		this.members = Collections.singleton(this);
	}

	private AccessFilter(Collection<AccessFilter> members){
		this.members = members;
	}

	public boolean accepts(AccessModifier modifier){
		for(AccessFilter filter : members){
			if(modifier.equals(MODIFIERS.get(filter))){
				return true;
			}
		}
		return false;
	}

	private AccessFilter strictest(){
		for(int i = 0; i < BASIC.length; i ++){
			if(this.members.contains(BASIC[i])){
				return BASIC[i];
			}
		}
		throw new IllegalStateException();
	}

	private AccessFilter mostLenient(){
		for(int i = BASIC.length-1; i >= 0; i --){
			if(this.members.contains(BASIC[i])){
				return BASIC[i];
			}
		}
		throw new IllegalStateException();
	}

	public AccessFilter andStricter(){
		AccessFilter strictest = this.strictest();
		Set<AccessFilter> members = new HashSet<AccessFilter>();
		for(int i = 0; i < BASIC.length; i ++){
			members.add(BASIC[i]);
			if(BASIC[i].equals(strictest)){
				break;
			}
		}
		return new AccessFilter(Collections.unmodifiableSet(members));
	}

	public AccessFilter andMoreLenient(){
		AccessFilter mostLenient = this.mostLenient();
		Set<AccessFilter> members = new HashSet<AccessFilter>();
		for(int i = BASIC.length - 1; i >= 0; i--){
			members.add(BASIC[i]);
			if(BASIC[i].equals(mostLenient)){
				break;
			}
		}
		return new AccessFilter(Collections.unmodifiableSet(members));
	}
}
