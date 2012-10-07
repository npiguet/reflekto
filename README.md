Nextflection : reflection for the java language
===============================================

What is Nextflection
--------------------

Nextflection is a library that is intended to be used as a replacement for the reflection API included in the JDK. It provides an intuitive support for generics, and includes some features that are not present in java.lang.reflect.

What's the point? Isn't java.lang.reflect good enough?
------------------------------------------------------
Not if you look at it from the java language point of view. When you look at the in details, it becomes pretty clear that the classes in java.lang.reflect are actually a thin wrapper around the class file format. It contains all sorts of strange, inconvenient methods. Here are a bunch of examples of what I mean:

 - Visibility modifiers are exposed as a bitfield. This bitfield actually includes all modifiers, including static, final, abstract, synchronized, volatile, native, etc... most of which are completely unrelated to the others. The bitfield corresponds exactly to what is written in the .class file

 - When a class contains multiple overloaded methods, there is no easy way to figure out which one would be called by a java program given a list of parameter types. There is no way to find which of those methods has the most specific signature as defined in the JLS. 

 - There is no way to figure out if a specific class or member should be visible, when trying to look at it from another class (eg: is `AbstractList.removeRange(int, int)` visible from `java.util.HashSet`?).

 - There is no way to find out if a method overrides another. This matter is made even worse due to the poor support for generics (see below).

Generics in particular, only have a very limited support in java.lang.reflect through methods like getGenericFoo(), and are very inconvenient to work with.

 - When a Type is a generic invocation of a parameterized class, and that therefore the type variables have been assigned a value, there is no easy way to figure out the actual types of fields, method arguments, return types, etc... eg: in `Map<String, Integer>`, what is the return type of the `keySet()` method?

 - When a method that declares a type parameter T returns an object of a type using that type variable, there is no way to find out what is the actual return type. If I call `Arrays.asList()` (declared as `public static <T> List<T> asList(T... values)`) with a `String[]` parameter, what is the actual return type of the method?

These are only a few example of the problems that are left unsolved by the java reflection API, but they clearly expose the major difference between java.lang.reflect and Nextflection. One is a low level wrapper for the .class file format, and the other is a high level introspection library for the Java language.