package reflekto;

import java.util.List;

/**
 * An element that can declare its own type variable.
 */
public interface GenericDeclaration extends Parameterizable {

	public List<TypeVariable> getDeclaredTypeParameters();

}
