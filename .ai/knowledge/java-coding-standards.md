
# Java Coding Standards

## Variable names
Variable names are field names and parameter names (which are a primitive or object reference variable). 
For object references, the variable names should consist of one or two elements of the class name. Only use full class name variable names where they clash.
For primitives, the variable name should be use the name of the value they hold if a primitive

For example a variable for ApplicationContext should be context, but could be applicationContext if necessary.
For example a variable for RequestMappingHandlerMapping should be mapping but could be handlerMapping.

They should not be acronyms or single letters, even in lambdas.
The only case where a single letter variable is allowed is for loop counter variables, for example:

```java
for (int i=0; i<10; i++) {
    // Code
}
```
Or they can be used for exceptions in the catch block, for example:
```java
try {
    // Code
} catch (Exception e) {
    // Handle exception
}
```

## Class Layout
The order in which fields, constructors, methods and inner classes should be declared is as follows:
1. All static fields.
2. All static methods.
3. All static classes, records and enums.
4. All instance fields.
5. All instance constructors.
6. All instance methods.

This order should only be changed in enums where it is necessary to do so for validity.

## Interfaces

Interface names should be prefixed with the letter "I" to indicate it is an interface.
If there is only one implementation of the interface being generated, it would be expected to be the interface name without the "I" unless not appropriate.

## Varargs

- Only use varargs if necessary for clean code.
- A varargs constructor or method should call a corresponding constructor or method that takes a collection and that does the actual work.

## Exceptions

- Wrap checked exceptions from IO/Jackson/reflection calls with
  `com.robindrew.common.util.Java.propagate(e)` inside a `catch (Exception e)` block,
  rather than declaring or rethrowing the checked exception.
- When a lookup by id or name fails (e.g. iterating a list to find a match), throw
  `IllegalArgumentException` with a message that includes the failed key, e.g.
  `"Label not found with id: " + id`. Do not introduce a custom exception type for
  this unless callers need to distinguish it from other `IllegalArgumentException`s.

## Collections

- Use List.of() and List.copyOf() in preference to Collections.unmodifiableList()
- Use Set.of() and Set.copyOf() in preference to Collections.unmodifiableSet()
- Use Map.of() and Map.copyOf() in preference to Collections.unmodifiableMap()
- Use the Google Guava Multimap data structure where a Map is needed with a collection as its value.

## Model Lookups

- For a small in-memory list of domain objects (people, addresses, labels, accounts),
  look up by id/name with a plain linear scan (`for (X x : list) { if
  (x.getId().equals(id)) ... }`) rather than maintaining a separate `Map` index — the
  lists involved are small and a `Map` adds a second source of truth to keep in sync.
- When a name/key is used case-insensitively (e.g. as a map key), normalize it once
  with a small private static helper (`trim()` + `toUpperCase(Locale.ROOT)`) rather
  than repeating the normalization inline at each call site.

## Imports
When making references to static constants or methods, use a static import where it does not clash as this makes the code cleaner.

## Streams

- Each chained method call in a java stream, like filter and map, should be on a separate line
- Never use anonymous methods calls, only single line statements or a call to another method

## Lombok

Lombok is wired into every module via the `io.freefair.lombok` Gradle plugin (applied
from `gradle/java-library.gradle` and `gradle/java-application.gradle`), so no
per-module `compileOnly`/`annotationProcessor` dependency is needed.

- Use the lombok @StandardException annotation when creating exception classes
- Use the lombok @Data annotation for java beans
- Use the lombok @Slf4j annotation instead of declaring static logger fields in
  classes — never write `private static final Logger log = LoggerFactory.getLogger(X.class);`
  by hand; annotate the class `@Slf4j` and use the `log` field it generates.

## Logging

- Time any operation worth reporting (file load/save, batch processing) with Guava's
  `Stopwatch`: `Stopwatch timer = Stopwatch.createStarted();` ... do the work ...
  `timer.stop();` then log the elapsed time via the `Stopwatch`'s `toString()`, e.g.
  `log.info("[Loaded Labels] {} in {}", labels.size(), timer)`.
- Prefix `log.info`/`log.warn` messages that mark a significant lifecycle or
  state-change event (created/loaded/saved/deleted/changed) with a short bracketed
  tag describing the action, e.g. `"[Created Label] name={} id={} type={}"`. Use `{}`
  placeholders, never string concatenation.

## Spring

- Avoid component scanning, do not use the @ComponentScan annotation.
- Always declare the @SpringBootApplication in a unique package (e.g. com.example.module.app)
- Any spring module should have at most a few @Configuration classes
    - Like @SpringBootApplication, place the @Configuration files in a unique package in the module (e.g. com.example.module.config).
    - If more than one @Configuration is helpful, perhaps to group beans, locate them under the same config package (or a sub package of config).
- To load properties, prefer a record with @ConfigurationProperties and loaded from an @Configuration using @EnableConfigurationProperties to using @Value 

## Module Structure

Each Spring Boot service module (e.g. `robindrew-services-financialmanager`,
`robindrew-services-mediamanager`) follows the same package and resource layout.
Deviations should be justified; new modules should default to this shape.

### Package layout

Under the module's root package (`com.robindrew.<module>`):

- Place @Configuration files in a "config" sub-package
- Place @RestController and @Controller in a "controller" sub-package
- Place @Component or @Service objects in feature specific sub-packages
- Place related @Entity and @Repository (e.g. JpaRepository) objects together in features specific sub-packages

- `app` — contains only the `@SpringBootApplication` entry point class
  (e.g. `financialmanager.app.FinancialManagerService`,
  `mediamanager.app.MediaManagerService`). It does nothing but `@Import` the module's
  `@Configuration` classes and call `SpringApplication.run(...)`.
- `config` — the module's `@Configuration` classes, one per logical group of beans
  (e.g. `FinancialManagerComponentConfig` for domain beans and
  `FinancialManagerServletConfig` for the web layer in financialmanager;
  `MediaFileConfig`, `MediaServletConfig`, `MediaSourceConfig`, `FileEncryptionConfig`,
  `AnimatedGifWriterConfig` in mediamanager). The servlet `@Configuration` is also where
  `@ServletComponentScan(basePackageClasses = ...)` is declared and where the admin
  index navigation links are registered via `IndexLinkMap` in a `@PostConstruct` method.
- `component` (or `model` for primarily-data-holding modules) — the domain/business
  logic, grouped into one sub-package per feature area (e.g.
  `component.account`, `component.budget`, `component.label`, `component.report` in
  financialmanager; `model.file.cache`, `model.file.manager`, `model.file.loader`,
  `model.tag`, `model.video` in mediamanager). Beans declared here are wired up from the
  `config` package, not annotated with `@Component`/`@Service` themselves (see Spring
  section above).
- `servlet` (or `web.servlet`) — one `@WebServlet` class per page, matched to its
  Thymeleaf template with `@TemplateResource`. Supporting view-model/helper classes live
  in a `servlet.view` (or `web.servlet.view`) or `servlet.action`
  (`web.servlet.action`) sub-package. A module may also expose a small number of
  Spring MVC `@Controller`/`@RestController` classes directly under a `web` or
  `controller` package for endpoints that don't fit the templated-page model (e.g.
  `mediamanager.web.MediaFileController` for range-request video streaming,
  `financialmanager.component.controller.FinancialManagerRestController` for a JSON API).
- Standalone offline tools (batch `main()` classes not run as part of the web service,
  e.g. `AccountTransformTool`, `FinancialManagerReportGenerator`, `FileEncryptorTool`,
  `MediaConverterTool`) live in their own package (`transform`, `report.cmd`, `tools`)
  clearly separate from the servlet/component code, so it's obvious they aren't part of
  the running application.

### Web layer: servlets and templates

- Every page-rendering servlet extends `AbstractServiceServlet`, declares its route with
  `@WebServlet(urlPatterns = "/PageName")`, and points at its template with
  `@TemplateResource("templates/.../PageName.html")`.
- Servlets that stream raw bytes (images, video frames, CSV downloads) extend
  `AbstractBaseServlet` instead and have no `@TemplateResource`.
- HTML templates live under `src/main/resources/templates/`, mirroring the servlets'
  feature grouping — e.g. `templates/finances/*.html` in financialmanager,
  `templates/photos/*.html` and `templates/videos/*.html` in mediamanager (with the two
  top-level index pages `Photos.html`/`Videos.html` directly under `templates/`).
- Static assets (images, etc.) live under `src/main/resources/static/`.
- Basic auth, the admin index page, and Thymeleaf templating itself are provided by
  importing `BasicAuthSecurityConfig`, `AdminServletConfig` and `ThymeleafTemplateConfig`
  from `robindrew-spring` in the servlet `@Configuration` class — modules should not
  reimplement this.

### Application property files

- `src/main/resources/application.properties` is the default/base profile: server port,
  module-specific file/directory locations wired via `@Value`, Swagger/OpenAPI paths
  (`springdoc.swagger-ui.path`, `springdoc.api-docs.path`), and basic-auth credentials
  (`auth.username`, `auth.password`).
- Profile-specific overrides use the `application-<PROFILE>.properties` naming
  convention, e.g. `application-DEV.properties`, activated via
  `spring.profiles.active=<PROFILE>`. Keep these small — only the properties that
  actually differ per environment (e.g. stats-component polling frequency/cache
  capacity).
- `logback.xml` and `banner.txt` also live in `src/main/resources`, alongside the
  properties files.
- Mutable data the application reads/writes at runtime (rule sets, report definitions,
  budgets, caches) is **not** bundled as a classpath resource — it lives in a plain
  `data/` directory at the module root (e.g.
  `robindrew-services-financialmanager/data/RuleSet.xml`) and is referenced from
  `application.properties` via `@Value`-injected `File`/path properties, never
  hard-coded in Java.

### File-backed caches and managers

Classes that own an in-memory collection backed by a file on disk (e.g.
`taskmanager.component.label.LabelCache`, `component.person.PersonManager`,
`component.address.AddressManager`, `component.event.CalendarEventManager`;
`financialmanager.component.account.AccountManager`,
`component.property.PropertyManager`; `mediamanager.model.file.cache.MediaFileCacheFile`,
`model.tag.TagCacheFile`) share this shape:

- Load the file's contents into a thread-safe collection (`ConcurrentHashMap`,
  `CopyOnWriteArrayList`, `CopyOnWriteArraySet`) from a `@PostConstruct` method (or the
  constructor, for classes not managed as a Spring-bean lifecycle dependency).
- Persist the whole collection back to disk from a single private `write()` (or
  `save()`) method, called at the end of every mutating public method — never leave a
  mutation unpersisted.
- Call `file.getParentFile().mkdirs()` immediately before writing, so a missing data
  directory doesn't fail the first save.
- Keep the cache's state on the instance, not in a `static` field — a class-level
  static registry (mixing a global cache with per-instance beans) makes the class
  harder to test and reason about; construct one cache instance and wire it through
  Spring/constructor injection like any other bean.
- For JSON-backed caches using Jackson, add a small private `getMapper()` method
  returning a fresh `ObjectMapper` rather than holding one as a field.

## REST
- The @RestController annotated class should have an @Tag to describe the group of operations.
- When writing endpoints for a @RestController, make sure they all have an @Operation annotation.

## Unit Testing
- Write a unit test to cover every class other than simple record, enum or pojo objects.
- Unit tests should use JUnit 5.
- Unit tests can use Mockito.
- Unit tests can use Assert4j.
- Unit tests should avoid using any spring boot test annotation (e.g. @SpringBootTest, @DataJpaTest, @WebMvcTest, etc)
- At most one spring boot test annotation per module.
- Ideally only a few classes should be annotated with a spring boot test annotation across an entire project.

## Spotless
- Ensure that spotlessApply is run across any new or edited java code.
- A full build will call spotlessApply, but if one was not run builds across edited modules

## REST Standards
- Use @RestController for api endpoints
- The controller class should have @Tag annotation populated
- Each endpoint should have an @Operation annotation describing the operation
- @RequestParam should have the "name" explicitly set
- @PathVariable should have the "name" explicitly set

## HTTP Client Calls
- Use WebClient for http calls
- For each call, first create a class which encapsulates the call with a method that has a request and response object
- The request class should have all path, query, header, body parameters in it
- The response class should wrap the returned data (typically an object, but might not be editable)
