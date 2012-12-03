package reflekto.impl;

import java.util.List;

import reflekto.ArrayType;
import reflekto.ClassType;
import reflekto.Constructor;
import reflekto.Field;
import reflekto.Method;
import reflekto.Reflector;
import reflekto.Type;
import reflekto.TypeVariable;
import reflekto.WildcardType;

public interface FullReflector extends Reflector {

	public Type reflect(java.lang.reflect.Type type);

	public ClassType reflect(java.lang.reflect.ParameterizedType type);
	public ClassType reflectGenericInvocation(ClassType original, List<Type> actualTypeArguments, boolean asErasure);

	public TypeVariable reflect(java.lang.reflect.TypeVariable<?> var);

	public Constructor reflect(java.lang.reflect.Constructor<?> cons, ClassType declaringClass);

	public Field reflect(java.lang.reflect.Field f, ClassType declaringClass);

	public Method reflect(java.lang.reflect.Method m, ClassType declaringClass);

	public Method reflectErasure(Method original);

	public ArrayType reflectErasure(ArrayType original);

	public WildcardType reflectGenericInvocation(WildcardType original, List<Type> lowerBounds, List<Type> upperBounds);

	public ArrayType reflectGenericInvocation(ArrayType original, Type elementType);

	public void clearTypeCache();
}
