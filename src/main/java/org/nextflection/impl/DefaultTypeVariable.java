package org.nextflection.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.nextflection.ClassType;
import org.nextflection.GenericDeclaration;
import org.nextflection.ObjectType;
import org.nextflection.Type;
import org.nextflection.TypeName;
import org.nextflection.TypeVariable;

public class DefaultTypeVariable extends AbstractElement implements TypeVariable {

	private java.lang.reflect.TypeVariable<?> typeVariable;
	private AtomicReference<List<Type>> bounds = new AtomicReference<List<Type>>();
	private GenericDeclaration genericDeclaration;

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

	public List<Type> getBounds() {
		if(bounds.get() == null){
			// build the bounds lazily so as not to actively build a giant tree of types
			List<Type> types = new ArrayList<Type>();
			for (java.lang.reflect.Type t : typeVariable.getBounds()) {
				if(t instanceof Class){
					types.add(reflector.reflect((Class<?>)t));
				} else if (t instanceof java.lang.reflect.TypeVariable){
					// TODO
				} else if (t instanceof java.lang.reflect.ParameterizedType){
					// TODO: this is a bit fucked up currently, but should basically be
					// reflector.reflect((java.lang.reflect.ParameterizedType)t);
				} else if (t instanceof java.lang.reflect.GenericArrayType){
					// TODO
				} else {
					throw new IllegalStateException("Bounds " + t + " of type " + t.getClass().getName() + " are not supported");
				}
			}

			// if two threads entered here, make sure that only one gets to set the value
			bounds.compareAndSet(null, types);
		}
		return Collections.unmodifiableList(bounds.get());
	}

	public Type getLeftmostBound() {
		return getBounds().get(0);
	}

	public ClassType withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeVariable withTypeArgument(TypeVariable variable, ObjectType value) {
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
