package reflekto;

import java.util.List;

public interface ArrayType extends ObjectType {

	public ArrayType withErasure();

	public Type getElementType();

	public ArrayType assignVariables(List<TypeVariable> variable, List<Type> value);
}
