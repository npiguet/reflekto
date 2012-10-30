package reflekto;

import java.util.List;

public interface TypeVariable extends Type {

	public String getName();
	public GenericDeclaration getDeclaringElement();
	public List<Type> getDeclaredBounds();
	public Type getDeclaredLeftmostBound();
	public List<Type> getActualBounds();
	public Type getActualLeftmostBound();
	public boolean isGenericInvocation();
	public Type assignVariables(GenericDeclaration declaration, List<TypeVariable> vars, List<Type> values);
}
