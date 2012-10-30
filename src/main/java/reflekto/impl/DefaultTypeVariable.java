package reflekto.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import reflekto.GenericDeclaration;
import reflekto.Type;
import reflekto.TypeName;
import reflekto.TypeVariable;

public class DefaultTypeVariable extends AbstractElement implements TypeVariable {

	private final java.lang.reflect.TypeVariable<?> typeVariable;
	private final GenericDeclaration genericDeclaration;
	private final ReadOnlyReference<List<Type>> declaredBounds;
	private final ReadOnlyReference<List<Type>> actualBounds;

	public DefaultTypeVariable(java.lang.reflect.TypeVariable<?> var, GenericDeclaration declaration, FullReflector creator) {
		super(creator);
		this.typeVariable = var;
		this.genericDeclaration = declaration;
		this.declaredBounds = new LazyInit<List<Type>>(){
			@Override
			protected List<Type> init() {
				return initDeclaredBounds();
			}
		};
		this.actualBounds = declaredBounds;
	}

	/**
	 * Builds a TypeVariable where the bounds are modified so that occurences of any of the
	 * variables in vars are replaced by the corresponding value in values, and any occurence
	 * of the original type variable is replaced by this type variable.
	 */
	protected DefaultTypeVariable(DefaultTypeVariable original, GenericDeclaration declaration, List<TypeVariable> vars, List<Type> values){
		super(original.reflector);
		this.typeVariable = original.typeVariable;
		this.declaredBounds = original.declaredBounds;
		this.genericDeclaration = declaration;

		final List<TypeVariable> safeVars = new ArrayList<TypeVariable>(vars);
		final List<Type> safeValues = new ArrayList<Type>(values);
		safeVars.add(original);
		safeValues.add(this);
		this.actualBounds = new LazyInit<List<Type>>(){
			@Override
			protected List<Type> init() {
				return initActualBounds(safeVars, safeValues);
			}
		};
	}

	public String getName() {
		return typeVariable.getName();
	}

	public GenericDeclaration getDeclaringElement() {
		return genericDeclaration;
	}

	protected List<Type> initDeclaredBounds(){
		List<Type> types = new ArrayList<Type>(typeVariable.getBounds().length);
		for (java.lang.reflect.Type t : typeVariable.getBounds()) {
			types.add(reflector.reflect(t));
		}
		return Collections.unmodifiableList(types);
	}

	protected List<Type> initActualBounds(List<TypeVariable> vars, List<Type> values){
		List<Type> bounds = new ArrayList<Type>(getDeclaredBounds().size());
		for(Type declared : getDeclaredBounds()){
			bounds.add(declared.assignVariables(vars, values));
		}
		return Collections.unmodifiableList(bounds);
	}

	public List<Type> getDeclaredBounds() {
		return declaredBounds.get();
	}

	public Type getDeclaredLeftmostBound() {
		return getDeclaredBounds().get(0);
	}

	public List<Type> getActualBounds() {
		return actualBounds.get();
	}

	public Type getActualLeftmostBound() {
		return getActualBounds().get(0);
	}

	public boolean isGenericInvocation() {
		return declaredBounds != actualBounds;
	}

	public Type withErasure() {
		// JLS §4.6: The erasure of a type variable (§4.4) is the erasure of its leftmost bound.
		return this.getActualLeftmostBound().withErasure();
	}

	/**
	 * Called at the declaration site of this TypeVariable, when its bounds must be re-evaluated.
	 */
	public Type assignVariables(GenericDeclaration declaration, List<TypeVariable> variables, List<Type> values){
		// TODO: call the appropriate method in the reflector instead
		return new DefaultTypeVariable(this, declaration, variables, values);
	}

	/**
	 * If this TypeVariable is part of the variables list then the corresponding value is returned,
	 * otherwise this TypeVarible is returned. This method is supposed to be called when this variable is being
	 * *used* (as opposed to being *declared* as a type parameter).
	 */
	public Type assignVariables(List<TypeVariable> variables, List<Type> values){
		int index = variables.indexOf(this);
		if(index >= 0){
			return values.get(index);
		}
		return this;
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

	@Override
	public String toString(){
		return getGenericName().full();
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
				List<Type> bounds = getActualBounds();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 17;
		result = prime * result + typeVariable.hashCode();
		result = prime * result + genericDeclaration.hashCode();
		result = prime * result + getActualBounds().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()){
			return false;
		}
		DefaultTypeVariable other = (DefaultTypeVariable) obj;
		return this.typeVariable.equals(other.typeVariable) &&
				this.genericDeclaration.equals(other.genericDeclaration) &&
				this.getActualBounds().equals(other.getActualBounds());
	}

	public boolean isSuperTypeOf(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAssignableFrom(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSameType(Type other) {
		if(other == this){
			return true;
		}
		if(other == null || ! (other instanceof TypeVariable)){
			return false;
		}
		TypeVariable that = (TypeVariable)other;
		List<Type> thisBounds = this.getDeclaredBounds();
		List<Type> thatBounds = that.getDeclaredBounds();
		for(int i = 0; i < thisBounds.size(); i ++){
			if(!thisBounds.get(i).isSameType(thatBounds.get(i))){
				return false;
			}
		}
		return true;
	}
}
