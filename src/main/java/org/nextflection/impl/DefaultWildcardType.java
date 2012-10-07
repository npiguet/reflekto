package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nextflection.Type;
import org.nextflection.TypeName;
import org.nextflection.TypeVariable;
import org.nextflection.WildcardType;

public class DefaultWildcardType extends AbstractElement implements WildcardType {

	private final java.lang.reflect.WildcardType wildcard;
	private final ReadOnlyReference<List<Type>> upperBound = new LazyInit<List<Type>>(){
		@Override
		protected List<Type> init() {
			return initUpperBounds();
		}
	};
	private final ReadOnlyReference<List<Type>> lowerBound = new LazyInit<List<Type>>(){
		@Override
		protected List<Type> init() {
			return initLowerBounds();
		}
	};

	public DefaultWildcardType(java.lang.reflect.WildcardType wildcard, FullReflector reflector) {
		super(reflector);
		this.wildcard = wildcard;
	}

	private List<Type> initUpperBounds(){
		return initBounds(wildcard.getUpperBounds());
	}

	private List<Type> initLowerBounds(){
		return initBounds(wildcard.getLowerBounds());
	}

	private List<Type> initBounds(java.lang.reflect.Type[] jBounds){
		List<Type> bounds = new ArrayList<Type>(jBounds.length);
		for(java.lang.reflect.Type jBound : jBounds){
			bounds.add(reflector.reflect(jBound));
		}
		return Collections.unmodifiableList(bounds);
	}

	public Type withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}

	public Type withTypeArguments(List<TypeVariable> variables,
			List<Type> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPrimitive() {
		return false;
	}

	public String getName() {
		return getGenericName().full();
	}

	public TypeName getRawName() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeName getGenericName() {
		return new AbstractTypeName(){
			public String full() {
				return buildName(TypeName.Kind.FULL);
			}

			public String simple() {
				return buildName(TypeName.Kind.SIMPLE);
			}

			public String canonical() {
				return buildName(TypeName.Kind.CANONICAL);
			}

			private String buildName(TypeName.Kind kind){
				StringBuilder s = new StringBuilder();
				s.append("?");

				List<Type> bounds = Collections.emptyList();
				if(boundsContainsMoreThanObject(getLowerBounds())){
					s.append(" extends ");
					bounds = getLowerBounds();
				} else if(boundsContainsMoreThanObject(getUpperBounds())){
					s.append(" super ");
					bounds = getUpperBounds();
				}

				boolean printed = false;
				for(Type bound : bounds){
					if(bound.getName().equals("java.lang.Object")){
						continue;
					}
					if(printed){
						s.append(", ");
					}
					printed = true;
					if(bound instanceof TypeVariable){
						s.append(bound.getGenericName().simple());
					} else {
						s.append(bound.getGenericName().get(kind));
					}
				}

				return s.toString();
			}
		};
	}

	private boolean boundsContainsMoreThanObject(List<Type> bounds){
		if(bounds.size() == 0){
			return false;
		}
		if(bounds.size() > 1){
			return true;
		}
		Type leftmost = bounds.get(0);
		// TODO comparing the name to "java.lang.Object" is ugly, find a better way
		return !leftmost.getGenericName().full().equals("java.lang.Object");
	}

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Type> getUpperBounds() {
		return upperBound.get();
	}

	public List<Type> getLowerBounds() {
		return lowerBound.get();
	}

	@Override
	public String toString(){
		return getName();
	}

	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 5;
		// wildcard.hashCode() already hashed the bounds, so we
		// don't need to it ourselves
		result = prime * result + wildcard.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		DefaultWildcardType other = (DefaultWildcardType) obj;
		// this already compares the bounds, so there is not need to do it ourselves
		return other.wildcard.equals(this.wildcard);
	}


}
