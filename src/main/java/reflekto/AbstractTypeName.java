package reflekto;


public abstract class AbstractTypeName implements TypeName {

	public String get(Kind k) {
		return k.getName(this);
	}

}
