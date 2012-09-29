package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.nextflection.ClassType;
import org.nextflection.GenericDeclaration;
import org.nextflection.Type;
import org.nextflection.TypeName;
import org.nextflection.TypeVariable;

public class DefaultTypeVariable extends AbstractElement implements TypeVariable {

	private final java.lang.reflect.TypeVariable<?> typeVariable;
	private final GenericDeclaration genericDeclaration;
	private final LazyInit<List<Type>> bounds = new LazyInit<List<Type>>(){
		@Override
		protected List<Type> init() {
			return initBounds();
		}
	};

	public DefaultTypeVariable(java.lang.reflect.TypeVariable<?> var, GenericDeclaration declaration, FullReflector creator) {
		super(creator);
		this.typeVariable = var;
		this.genericDeclaration = declaration;
	}

	public String getName() {
		return typeVariable.getName();
	}

	public GenericDeclaration getDeclaringElement() {
		return genericDeclaration;
	}

	protected List<Type> initBounds(){
		List<Type> types = new ArrayList<Type>(typeVariable.getBounds().length);
		for (java.lang.reflect.Type t : typeVariable.getBounds()) {
			types.add(reflector.reflect(t));
		}
		return Collections.unmodifiableList(types);
	}

	public List<Type> getBounds() {
		return bounds.get();
	}

	public Type getLeftmostBound() {
		return getBounds().get(0);
	}

	public ClassType withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeVariable withTypeArguments(List<TypeVariable> variable, List<Type> value){
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isParameterizable() {
		return true;
	}

	public boolean isPrimitive() {
		return false;
	}

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
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
				return typeVariable.getName();
			}

			public String canonical() {
				return buildName(TypeName.Kind.CANONICAL);
			}

			private String buildName(TypeName.Kind kind){
				List<Type> bounds = getBounds();
				if(bounds.size() == 1 && bounds.get(0).equals(reflector.reflect(Object.class))){
					return typeVariable.getName();
				}
				StringBuilder s = new StringBuilder();
				s.append(typeVariable.getName());
				s.append(" extends ");
				for(int i = 0; i < bounds.size(); i ++){
					if(i > 0){
						s.append(", ");
					}
					s.append(bounds.get(i).getGenericName().get(kind));
				}
				return s.toString();
			}
		};
	}
}
