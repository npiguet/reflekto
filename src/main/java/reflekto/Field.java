package reflekto;

import java.util.List;

public interface Field extends Member {

	public Field withErasure();

	public Field assignVariables(List<TypeVariable> variable, List<Type> value);
}