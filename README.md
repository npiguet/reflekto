# Refleckto : Reflection for the java language #

## Introduction ##

The java reflection API is the api that allows programs to discover their own structure at runtime. The java reflection API, just like the English language, works fine and intuitively in some cases. However, in some other cases it can get just as inconsistent and complicated as the English language.

Reflekto is the Esperanto translation of "reflection". Reflekto, just like Esperanto, provides a consistent, intuitive, predictable and simple way for java programs to discover their own structure at runtime.


## What's wrong with the java reflection API (java.lang.reflect and friends) ? ##

### It is nothing but a thin wrapper around the class file format ###
There are several cases where java.lang.reflect leaks the implementation details of the class file format it is based on. The most striking example are visibility modifiers. They are exposed as a bitfield which includes all modifiers, including static, final, abstract, synchronized, volatile, native, etc... most of which are completely unrelated to the others. The bitfield corresponds exactly to what is written in the .class file.

### Its support for inheritance is minimal ###
The only tools that `java.lang.Class` gives you to figure out inheritance are the `getSuperClass()` and `getInterfaces()` methods. Inheritance of methods and fields is completely ignored and has to be figured out manually by the user. `getMethods()` (and its analog `getFields()`) does return inherited methods, but only the public ones.

### Its support for overriding is inexistant ###
The reflection API also does not provide any way to find out if a method overrides another, which is a pretty difficult problem to solve. This matter is made even worse due to the poor support for generics (see below).

### Its support for class and members visibility is minimal ###
Visibility is a fundamental concept on Object oriented languages such as Java. The reflection API only exposes that as a bitfield on each individual element. This makes it difficult to figure out if that element is accessible from a particular class or method. For example, it is very hard to figure out if `AbstractList.removeRange(int, int)` is visible or not from `ArrayList`.

### Overloading is not handled at all ###
The java language specification defines quite cleary which method in a set of methods with the same name should actually be invoked when a specific list of argument types is passed. The java compiler can figure it out at compile time but java.lang.reflect does not even attempt to handle that and does not provide any way to do the same at runtime.

### Its support for generics is below minimal ###
Generics support was bolted on top of the existing API in java 1.5. Methods like `getGenericFields()`, `getGenericInterfaces()`, `getGenericSuperclass()` exist in parallel to the non-generic version.

When a Type is a generic invocation (`Map<String, Integer>`) of a parameterized type (`Map<K, V>`) and that therefore the type variables have been assigned a value, there is no easy way to figure out the actual types of fields, methods arguments, return types, etc... eg: in `Map<String, Integer>`, what is the return type of the `keySet()` method?

When a method that declares a type parameter `<T>` returns an object of a type using that type variable, there is no way to find out what is the actual return type. If I call `Arrays.asList()` (declared as `public static <T> List<T> asList(T... values)`) with a `String[]` parameter, the java reflection API does not provide any way to find the actual return type of the method (`List<String>` in this case)?

## How does Reflekto help (aka: Show me some examples) ##
Let's look at a few examples of complex problems that Reflekto will solves for you
### Members inheritance and overriding ###
Getting all methods of a class:
``` java
Methods methods = reflect(ArrayList.class).methods();
// methods contains all the methods declared in ArrayList plus all the
// methods of all super-types  that are visible  from ArrayList and not 
// overridden. Which means it contains AbstractList.removeRange(int, int)
// (protected), but not AbstractList.outOfBoundsMsg(int) (private)
```
Finding out if a method overrides another:
``` java
Method inSubclass = reflect(Integer.class).methods().getExact("intValue");
Method inSuperClass = reflect(Number.class).methods().getExact("intValue");
inSubClass.overrides(inSuperclass); // true
``` 

### Members visibility
Is `AbstractList.removeRange(int,int)` visible from `ArrayList`? What about from `Integer`?
``` java
ClassType intClass = reflect(int.class);
Method removeRange = reflect(AbstractList.class).methods().getExact("removeRange", intClass, intClass);
removeRange.isVisibleFrom(reflect(ArrayList.class)); // true
removeRange.isVisibleFrom(reflect(Integer.class)); // false
```

### Overloading and method resolution
Which version of `ArrayList.remove()` should I call, given an argument of type `int`? What about `Integer`?
``` java
reflect(ArrayList.class).methods().getMostSpecific("remove", reflect(Integer.class)); // returns remove(Object)
reflect(ArrayList.class).methods().getMostSpecific("remove", reflect(int.class)); // returns remove(int)
```

### Generics
