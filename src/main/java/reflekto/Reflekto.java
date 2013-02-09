package reflekto;


public class Reflekto {

	private static final Reflector REFLECTOR = new DefaultReflector();

	public static Type reflect(Class<?> clazz) {
		return REFLECTOR.reflect(clazz);
	}
}
