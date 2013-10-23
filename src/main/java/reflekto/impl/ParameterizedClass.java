package reflekto.impl;

import java.util.List;

import reflekto.ClassType;
import reflekto.Methods;
import reflekto.Type;
import reflekto.TypeName;
import reflekto.TypeVariable;

public class ParameterizedClass implements ClassType {

	public boolean isErasure() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPrimitive() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeName getRawName() {
		// TODO Auto-generated method stub
		return null;
	}

	public TypeName getGenericName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String declarationString() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSameType(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSuperTypeOf(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAssignableFrom(Type that) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<TypeVariable> getDeclaredTypeParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isPublic() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isAbstract() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isFinal() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isInterface() {
		// TODO Auto-generated method stub
		return false;
	}

	public Methods methods() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isGenericInvocation() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isInnerClass() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isInnerClassOf(ClassType enclosing) {
		// TODO Auto-generated method stub
		return false;
	}

	public ClassType getEnclosingClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType getDeclaredClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType getSuperClass() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isSuperClassOf(ClassType that) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ClassType> getInterfaces() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Type> getActualTypeParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType getGenericInvocation(List<Type> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType assignVariables(List<TypeVariable> variables,
			List<Type> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType withErasure() {
		// TODO Auto-generated method stub
		return null;
	}

	public ClassType getGenericInvocation(ClassType genericDeclaringClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPackage() {
		// TODO Auto-generated method stub
		return null;
	}

}
