package reflekto;

public interface TypeName {
	public String full();
	public String simple();
	public String canonical();
	public String get(Kind k);
	
	public static enum Kind {
		FULL {
			@Override
			public String getName(TypeName t) {
				return t.full();
			}
		},
		SIMPLE {
			@Override
			public String getName(TypeName t) {
				return t.simple();
			}
		}, 
		CANONICAL{
			@Override
			public String getName(TypeName t) {
				return t.canonical();
			}
		};
		
		public abstract String getName(TypeName t);
	}
}
