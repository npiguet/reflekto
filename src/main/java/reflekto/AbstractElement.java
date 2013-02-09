package reflekto;

import reflekto.impl.FullReflector;


public class AbstractElement {
	protected FullReflector reflector;

	public AbstractElement(FullReflector reflector) {
		this.reflector = reflector;
	}
}
