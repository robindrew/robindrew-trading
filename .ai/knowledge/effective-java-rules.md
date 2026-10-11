# Java Coding Standards

---

## Chapter 1 — Creating and Destroying Objects

- **Item 1** — Consider static factory methods instead of constructors. They have names, can return subtypes, and allow instance control.
- **Item 2** — Use a Builder when a constructor would need more than a handful of parameters. Builders improve readability and can enforce immutability.
- **Item 3** — Enforce the singleton property with a private constructor or an enum type. The single-element enum approach is the preferred idiom.
- **Item 4** — Enforce non-instantiability of utility classes with a private constructor that throws `AssertionError`.
- **Item 5** — Prefer dependency injection over hardwired resources or static utility classes. Pass dependencies in through the constructor.
- **Item 6** — Avoid creating unnecessary objects. Prefer primitives over boxed primitives; watch out for unintentional autoboxing.
- **Item 7** — Eliminate obsolete object references. Null out references in self-managed memory (e.g. stacks and caches) to prevent memory leaks.
- **Item 8** — Avoid finalizers and cleaners. They are unpredictable and slow. Implement `AutoCloseable` and use try-with-resources instead.
- **Item 9** — Prefer try-with-resources to try-finally. It is shorter, cleaner, and produces better stack traces when multiple exceptions occur.

---

## Chapter 2 — Methods Common to All Objects

- **Item 10** — Obey the general contract when overriding `equals`. It must be reflexive, symmetric, transitive, consistent, and return `false` for `null`.
- **Item 11** — Always override `hashCode` when you override `equals`. Equal objects must have equal hash codes.
- **Item 12** — Always override `toString`. Provide a full and human-readable representation of the object's significant fields.
- **Item 13** — Override `clone` judiciously. Prefer copy constructors or copy factory methods over `Cloneable`; for arrays, `clone` is fine.
- **Item 14** — Consider implementing `Comparable`. If a class has a natural ordering, implement it and keep it consistent with `equals`.

---

## Chapter 3 — Classes and Interfaces

- **Item 15** — Minimise accessibility. Make every class and member as inaccessible as possible. Prefer package-private over public.
- **Item 16** — In public classes, use accessor methods, not public fields. Expose state only through getters (and setters where mutability is intended).
- **Item 17** — Minimise mutability. Classes should be immutable unless there is a compelling reason otherwise. Make fields `private final` by default.
- **Item 18** — Favour composition over inheritance. Inheritance violates encapsulation; use the wrapper (decorator) pattern instead when reuse is needed.
- **Item 19** — Design and document classes for inheritance, or else prohibit it. Document `@Override`-able method semantics; never invoke them from constructors.
- **Item 20** — Prefer interfaces to abstract classes. Java's single-inheritance rule makes abstract classes fragile; interfaces are more flexible.
- **Item 21** — Design interfaces for posterity. Use `default` methods carefully; they can break existing implementations at runtime.
- **Item 22** — Use interfaces only to define types. Do not use interfaces as constant holders (`static final` fields belong in classes or enums).
- **Item 23** — Prefer class hierarchies to tagged classes. Tagged classes (with an enum + switch) are verbose; replace them with a proper hierarchy.
- **Item 24** — Favour static member classes over non-static. A non-static inner class silently holds a reference to its enclosing instance, causing leaks.
- **Item 25** — Limit source files to a single top-level class. Multiple top-level classes in one file can cause compilation ambiguity.

---

## Chapter 4 — Generics

- **Item 26** — Do not use raw types. Raw types exist only for pre-generics compatibility. Use parameterised types everywhere; errors caught at compile-time beat runtime `ClassCastException`.
- **Item 27** — Eliminate unchecked warnings. Every unchecked warning is a potential `ClassCastException`. Suppress with `@SuppressWarnings("unchecked")` only after proving safety, and always add a comment explaining why.
- **Item 28** — Prefer lists to arrays. Arrays are covariant and reified; generics are invariant and erased. Lists catch type errors at compile-time.
- **Item 29** — Favour generic types. Writing your own generic classes avoids casts at the call site and improves safety.
- **Item 30** — Favour generic methods. Static utility methods in particular benefit greatly from generics.
- **Item 31** — Use bounded wildcards to increase API flexibility. Use `? extends T` for producers and `? super T` for consumers (PECS).
- **Item 32** — Combine generics and varargs judiciously. Varargs and generics interact poorly; use `@SafeVarargs` only when the method truly is safe.
- **Item 33** — Consider typesafe heterogeneous containers. When you need a container that stores multiple types safely, parameterise the key, not the container.

---

## Chapter 5 — Enums and Annotations

- **Item 34** — Use enums instead of `int` constants. Enums are a full class; they provide type safety, a namespace, readable `toString`, and the ability to add behaviour.
- **Item 35** — Use instance fields instead of ordinals. Never derive a value associated with an enum from its `ordinal()`; store it in an instance field.
- **Item 36** — Use `EnumSet` instead of bit fields. `EnumSet` is as performant as bit manipulation and far more readable and type-safe.
- **Item 37** — Use `EnumMap` instead of ordinal indexing. Prefer `EnumMap` over arrays indexed by `ordinal()`.
- **Item 38** — Emulate extensible enums with interfaces. Enums themselves cannot be extended, but they can implement interfaces, enabling extensible enum-like behaviour.
- **Item 39** — Prefer annotations to naming patterns. Annotations convey metadata more reliably and are processed by the compiler; naming conventions like `testFoo` are fragile.
- **Item 40** — Consistently use the `@Override` annotation. Use it on every method you believe overrides a supertype method; the compiler will catch mistakes.
- **Item 41** — Use marker interfaces to define types. Prefer a marker interface (e.g. `Serializable`) over a marker annotation when you need to mark instances of a type.

---

## Chapter 6 — Lambdas and Streams

- **Item 42** — Prefer lambdas to anonymous classes. Lambdas are more concise and readable for functional interface implementations.
- **Item 43** — Prefer method references to lambdas. Where a method reference is clearer than a lambda, use it (e.g. `Integer::parseInt` over `x -> Integer.parseInt(x)`).
- **Item 44** — Favour the use of standard functional interfaces. Use `java.util.function` interfaces (`Predicate`, `Function`, `Supplier`, etc.) rather than rolling your own.
- **Item 45** — Use streams judiciously. Streams are not always the right tool; loops are often more readable for side-effecting code or when local variable mutation is required.
- **Item 46** — Prefer side-effect-free functions in streams. Stream pipelines should consist of pure functions; `forEach` should only be used to report results, never to compute them.
- **Item 47** — Prefer `Collection` to `Stream` as a return type. Callers can easily obtain a stream from a collection but not vice versa; return `Collection` when in doubt.
- **Item 48** — Use caution when making streams parallel. Parallel streams can hurt performance or produce wrong results on some pipelines; measure before parallelising.

---

## Chapter 7 — Methods

- **Item 49** — Check parameters for validity. Validate parameters at the start of public and protected methods; document and enforce preconditions eagerly.
- **Item 50** — Make defensive copies when needed. When accepting mutable objects from clients, copy them on the way in (and out if necessary) to maintain invariants.
- **Item 51** — Design method signatures carefully. Choose names carefully, avoid long parameter lists, favour interfaces over classes in parameter types.
- **Item 52** — Use overloading judiciously. Overloading is resolved at compile-time; this can surprise callers. When in doubt, give methods distinct names.
- **Item 53** — Use varargs judiciously. Require at least one argument when it makes no sense to invoke the method with zero; be aware of the array allocation cost.
- **Item 54** — Return empty collections or arrays, not nulls. Returning null for "no results" forces callers to write null checks; return `Collections.emptyList()` instead.
- **Item 55** — Return `Optional`s judiciously. Use `Optional<T>` when a method cannot always return a value and clients must handle that case. Never return an `Optional` of a collection.
- **Item 56** — Write Javadoc for all exposed API elements. Document every public class, interface, constructor, method, and field with a Javadoc comment.

---

## Chapter 8 — General Programming

- **Item 57** — Minimise the scope of local variables. Declare them where first used; initialise them in the declaration.
- **Item 58** — Prefer for-each loops to traditional for loops. The enhanced for loop is clearer and less error-prone for iterating over collections and arrays.
- **Item 59** — Know and use the libraries. Familiarise yourself with `java.lang`, `java.util`, `java.io`, and their sub-packages before writing utility code from scratch.
- **Item 60** — Avoid `float` and `double` for exact answers. Use `BigDecimal`, `int`, or `long` for monetary and other precision-sensitive calculations.
- **Item 61** — Prefer primitive types to boxed primitives. Boxed primitives have three drawbacks: they can be `null`, they are slower, and `==` compares identity rather than value.
- **Item 62** — Avoid strings where other types are more appropriate. Strings are poor substitutes for enums, aggregate types, or capability keys.
- **Item 63** — Beware the performance of string concatenation. Using `+` to concatenate many strings runs in quadratic time; use `StringBuilder` instead.
- **Item 64** — Refer to objects by their interfaces. Declare fields and return types as interface types (e.g. `List<Foo>`, not `ArrayList<Foo>`) to keep your options open.
- **Item 65** — Prefer interfaces to reflection. Reflective access surrenders compile-time type checking and is slower; use it only when there is no alternative.
- **Item 66** — Use native methods judiciously. JNI methods are rarely needed for performance and they sacrifice safety and portability.
- **Item 67** — Optimise judiciously. Do not optimise prematurely. Write clear, correct code first; profile before optimising.
- **Item 68** — Adhere to generally accepted naming conventions. Follow the Java naming conventions: `CamelCase` for types, `camelCase` for members, `SCREAMING_SNAKE_CASE` for constants.

---

## Chapter 9 — Exceptions

- **Item 69** — Use exceptions only for exceptional conditions. Never use exceptions for ordinary control flow; they are intended for truly unexpected situations.
- **Item 70** — Use runtime exceptions for all expected and unexpected errors. Reserve `Error` for JVM-level failures.
- **Item 71** — Avoid use of checked exceptions. If callers cannot realistically recover, wrap and rethrow a checked exception in an unchecked exception.
- **Item 72** — Favour the use of standard runtime exceptions where possible. Prefer `IllegalArgumentException`, `IllegalStateException`, `NullPointerException`, `IndexOutOfBoundsException`, etc.
- **Item 73** — Throw exceptions appropriate to the abstraction. Catch lower-level exceptions and translate them into higher-level ones that make sense to callers.
- **Item 74** — Document all exceptions thrown by each method. Use `@throws` Javadoc tags for both checked and unchecked exceptions.
- **Item 75** — Include failure-capture information in detail messages. The exception message should contain the values of all fields that contributed to the failure.
- **Item 76** — Strive for failure atomicity. A failing method should leave the object in the state it was in before the invocation. Use defensive copies or validate before mutating.
- **Item 77** — Do not ignore exceptions. An empty `catch` block is almost always wrong. At minimum, include a comment explaining why ignoring the exception is safe.

---

## Chapter 10 — Concurrency

- **Item 78** — Synchronise access to shared mutable data. Without synchronisation, there is no guarantee a second thread will ever see a change written by the first.
- **Item 79** — Avoid excessive synchronisation. Do not call alien methods from within a `synchronized` block; it can cause deadlock or data corruption.
- **Item 80** — Prefer executors, tasks, and streams to threads. Use the `java.util.concurrent` `Executor` framework rather than creating `Thread` objects directly.
- **Item 81** — Prefer concurrency utilities to `wait` and `notify`. Use higher-level abstractions such as `ConcurrentHashMap`, `BlockingQueue`, and `CountDownLatch`.
- **Item 82** — Document thread safety. Every class must state its thread-safety level: immutable, unconditionally thread-safe, conditionally thread-safe, not thread-safe, or thread-hostile.
- **Item 83** — Use lazy initialisation judiciously. Use the initialisation-on-demand holder idiom for static fields and double-checked locking for instance fields only when necessary.
- **Item 84** — Do not depend on the thread scheduler. Programs that rely on the scheduler for correctness or performance are non-portable and fragile.

---

## Chapter 11 — Serialization

- **Item 85** — Prefer alternatives to Java serialization. Java serialization is a security risk. Use JSON (Jackson, Gson), Protocol Buffers, or similar cross-platform formats instead.
- **Item 86** — Implement `Serializable` with great caution. It decreases flexibility, increases bugs, and increases the security attack surface.
- **Item 87** — Consider using a custom serialized form. The default serialized form is rarely optimal; define `writeObject`/`readObject` or switch to `Externalizable` when the default form is misleading.
- **Item 88** — Write `readObject` methods defensively. Treat `readObject` as another constructor: validate invariants and make defensive copies of mutable components.
- **Item 89** — For instance control, prefer enum types to `readResolve`. If you must prevent multiple instances via serialization, enums are the safest mechanism.
- **Item 90** — Consider serialization proxies instead of serialized instances. The serialization proxy pattern eliminates most of the risks and limitations of standard serialization.
