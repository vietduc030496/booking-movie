# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & test

Java 17, Spring Boot 3.5.3, Maven multi-module (use `./mvnw` on Unix/Git Bash, `mvnw.cmd` in PowerShell). CI (`.github/workflows/maven.yml`) runs `mvn clean install` from the repo root.

```bash
./mvnw clean install                 # full build + tests (same as CI)
./mvnw install -DskipTests           # build/install all modules without tests
./mvnw -pl admin-service -am test    # test one module (plus the modules it depends on)
./mvnw -pl admin-service -am test -Dtest=TheaterAdminServiceTest -Dsurefire.failIfNoSpecifiedTests=false
./mvnw -pl admin-service -am test -Dtest='TheaterAdminServiceTest#add_new_default_theater*' -Dsurefire.failIfNoSpecifiedTests=false
./mvnw -pl site spring-boot:run      # run the app (install the other modules first)
```

- `customer-service` depends on the MoMo SDK jar in `customer-service/lib/momopayment-1.0.jar`. It is installed into the local Maven repo by `maven-install-plugin` bound to the **`clean`** phase, so the first build on a machine must include `clean` (e.g. `./mvnw clean install`), otherwise dependency resolution fails.
- Each module's `pom.xml` uses `spring-boot-starter-parent` as its parent, **not** the root `pom.xml`. Dependencies declared in the root POM are not inherited by modules — add dependencies to the module that needs them.

## Runtime dependencies

`site/src/main/resources/application.yml` is the only real config. Running the app expects locally:
- PostgreSQL at `localhost:5432/booking-movie` (postgres/postgres; override URL with `SPRING_DATASOURCE_URL`). Schema is managed by Hibernate `ddl-auto: update`; `db/init/data.sql` runs on every startup (`sql.init.mode: always`, deferred until after Hibernate).
- Elasticsearch at `localhost:9200`, Redis at `localhost:6379`.
- MoMo payment sandbox settings in `site/src/main/resources/environment.properties`.

Tests do not need these: service tests are pure Mockito unit tests, and `movie-core` repository tests use `@DataJpaTest` on H2 with `MovieCoreTestApplication` as the boot config.

## Architecture

Despite the "microservices" wording in the README, this is a **single deployable** (`site`) assembled from library modules:

```
site  ──►  customer-service ─┐
      ──►  admin-service    ─┼──►  movie-core  (+ common)
      ──►  common           ─┘
```

- **`common`** (`com.ntu.common`): cross-cutting pieces — `UrlConstant` (all route paths), message-key constants, `BusinessException` / `ResourceNotFoundException`, `MessageSourceConfig` + static `I18n` helper, `CaffeineCacheUtil` (in-memory cache), Redis config, captcha/date utils.
- **`movie-core`** (`com.ntu.moviecore.domain.<area>`): JPA entities, Spring Data repositories, and request/response DTOs, grouped by domain area (`authentication`, `booking`, `movie`, `theater`, `material` = provinces/wards, `setting` = banners, `elasticsearch`). No services.
- **`customer-service`** / **`admin-service`**: Spring `@Service` business logic for the customer-facing side and the admin back office respectively (customer side also holds auth/`UserDetailsService` and MoMo payment).
- **`site`** (`com.ntu.site`): the Spring Boot app. `SiteApplication` component-scans all `com.ntu.*` packages and enables JPA/Elasticsearch repositories under `com.ntu.moviecore.domain` — new modules/packages must fit these scan roots. Contains controllers, security, filters, Thymeleaf templates, static assets and i18n bundles.

### Web layer conventions (`site`)
- Mostly server-rendered Thymeleaf. View controllers live under `api/customer/view` and `api/admin/view`; JSON endpoints under `api/*/rest`. Routes come from `UrlConstant`: views under `/views/...`, REST under `/api/v1/...`. Templates resolve from `templates/views/` (fragments in `views/fragments/general*.html`).
- Admin forms are full-page posts: controllers extend `BaseAdminViewController` and use `submit(...)` / `execute(...)`, which redirect back to the list page with a flash `successMessage` / `errorMessage` holding an **i18n key**. Services signal rule violations by throwing `BusinessException(messageKey)`; the key is shown to the user.
- `GlobalViewControllerAdvice` maps not-found exceptions to the 404 view and everything else to 500.
- Security (`SecurityConfig`): form login at `/views/account/login`, CSRF disabled, `/views/admin/**` and `/api/v1/admin/**` require `ROLE_ADMIN`, and `CaptchaValidationFilter` runs before the username/password filter.
- JSON uses `SNAKE_CASE` property naming globally.

### i18n
Message bundles are `site/src/main/resources/i18n/{error,messages,validate}_{en,vi}.properties` (default locale English). When adding a message key, add it to both `en` and `vi` files, and to the matching constants class in `common` if it is referenced from Java.

### Caching
Reference data (provinces, wards, province→wards map, theaters by province, banners, genres) is loaded into `CaffeineCacheUtil` at startup by `site/.../AppStartupRunner`, and services read through it via keys in `CacheAttributeKeyConstant`. When writing services that modify this data, keep the corresponding cache entry consistent.
