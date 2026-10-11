# Plans

A plan is a detailed set of instructions for the AI agent to follow when writing code.

Plans should be written in the .ai/plans directory.

---

## Structure

A well-formed plan has the following sections in order:

1. **Goal** — one or two sentences describing what the feature or fix achieves and why it is needed.
2. **Gradle modules** — which existing modules are affected and whether any new module must be created.
3. **Interfaces** — every new interface, listed before its implementation.
4. **Implementations** — concrete classes that implement the interfaces above.
5. **Spring configuration** — any `@Configuration` class or bean wiring changes required.
6. **Tests** — what must be verified and how.
7. **Out of scope** — explicit list of things the plan intentionally does not cover.

---

## Code Generation

When the goal is to write code, the plan must include all classes to be created with details of the fields and interaction between classes, including how to handle errors.

### Interfaces first

Define all interfaces before their implementations. For each interface state:
- The full package and class name (prefix with `I` per the coding standards).
- Every method signature with parameter names and return types.
- Which existing interfaces it extends, if any.
- A one-line note on why each method exists only when the purpose is not obvious from the name.

### Classes and records

For every class or record, specify:
- Full package and class name.
- Which interface(s) it implements.
- All fields — name, type, and whether it is injected, computed, or constant.
- Which Lombok annotations apply (`@Data`, `@Slf4j`, `@StandardException`, etc.).
- Constructor parameters (use constructor injection for Spring beans, not field injection).
- Every method that is not a simple getter/setter, including its logic in plain language.
- Any static factory methods or helper methods required.

### Ordering

List classes in dependency order — a class that depends on another must appear after it. The agent will create files top-to-bottom, so ordering prevents forward-reference confusion.

### Error handling

State explicitly which error codes or exceptions each method throws and under what condition. Reference existing `ErrorCode` enums where they exist. Do not add generic catch-all handling; only handle errors that callers need to distinguish.

---

## Gradle Modules

For each affected module state:

- Whether the module is `java-library` or `java-application`.
- Any new `implementation`, `api`, or `testImplementation` dependency to add to `build.gradle`.
- If a new module is required: its directory name, the `apply from` line, and how it is wired into `settings.gradle` and the root `build.gradle`.

Keep `build.gradle` files minimal — all other configuration lives in the shared `gradle/` scripts.

---

## Spring Configuration

If beans must be registered or wired:

- Name the `@Configuration` class and its package (always under a `config` sub-package).
- List every `@Bean` method — return type, method name, parameters (other beans), and one line on what it constructs.
- State which existing `@Configuration` or `@Import` chain should pull the new config in.
- Do not use `@ComponentScan`. Do not use `@Autowired` field injection.

---

## Testing

For each test class state:

- Full class name and which class under test it covers.
- Whether it is a pure unit test (JUnit 5 + Mockito + AssertJ only) or an integration test.
- At most one `@SpringBootTest`-annotated class per module; prefer pure unit tests.
- Each test method name (use `snake_case` descriptive names) and what it asserts.
- Any shared setup required (`@BeforeEach`, test fixtures, or helper classes like `TestUser`).

---

## Naming and Conventions

The plan must respect the project's Java coding standards:

- Variable names derive from the type name (one or two words, no acronyms).
- Loop counters may be single letters (`i`, `j`); exception variables may be `e`.
- Stream chains: one method call per line, no anonymous lambdas — extract named methods.
- Collections: use `List.of()`, `Set.of()`, `Map.of()` and their `copyOf` variants.
- Favour `static import` for constants and utility methods where there is no clash.

---

## Clarity rules

- Be specific. Vague instructions ("add appropriate error handling") produce vague code. Name the method, the condition, and the exception.
- Do not describe what is already obvious from the interface name or field type; focus on non-obvious constraints or invariants.
- If a design decision was made between two valid options, note the rejected option and the reason so the agent does not second-guess it.
- If a plan references an existing class, include its fully-qualified name so the agent does not create a duplicate.
- Mark sections that are genuinely uncertain with **[Decision needed]** so the agent pauses rather than guessing.