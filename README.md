# Playwright Java – E2E Tests

Minimal E2E suite using **Playwright for Java** + **JUnit 5** + **Maven**.

---

## Requirements
- **JDK 21** (Temurin LTS recommended)
- **Maven 3.9+**

---

## Project layout
```text
src/
└── test/
    ├── java/
    │   └── pw/mlaszczyk/automation/tests/BaseTest.java            # shared setup
    │   └── pw/mlaszczyk/automation/config/ConfigLoader.java       # config loader (class name matches file)
    └── resources/
        └── config.properties                                      # non-secret config
config/
├── secrets.example.properties                                     # template (tracked)
└── secrets.local.properties                                       # real secrets (git-ignored)
```

> If your loader class is named differently, keep the filename/class in sync (e.g. `ConfigLoader`).

---

## Configuration

### Public (tracked)
**`src/test/resources/config.properties`**
```properties
# Browser
browser.headless=false
browser.name=chrome   # chromium | chrome | firefox | webkit

# App
saucedemo.baseUrl=https://www.saucedemo.com
saucedemo.mainPageUrl=https://www.saucedemo.com/inventory.html

# Non-secret test data
saucedemo.invalidusername=<INVALID_USERNAME_FOR_NEGATIVE_TEST>
```

### Secrets (never commit real values)

#### Local file (dev): `config/secrets.local.properties`
```properties
saucedemo.username=<YOUR_USERNAME>
saucedemo.password=<YOUR_PASSWORD>
```

#### Environment variables (CI)
The loader maps **dots → underscores** and uppercases keys. Examples:

```
saucedemo.username  → SAUCEDEMO_USERNAME
saucedemo.password  → SAUCEDEMO_PASSWORD
browser.headless    → BROWSER_HEADLESS
browser.name        → BROWSER_NAME
```

> Precedence suggestion: **ENV** → `secrets.local.properties` → `config.properties`.

---

## How to run (local)
```bash
mvn clean test
```
- On first run, Playwright downloads browsers automatically.
- Control headless/GUI via config or environment variables, e.g.:
  - Linux/macOS: `BROWSER_HEADLESS=true mvn clean test`
  - Windows (PowerShell): `$env:BROWSER_HEADLESS='true'; mvn clean test`

---

## BaseTest (what you get)
- `@BeforeAll` → Create `Playwright` and launch the browser (type/headless from config)
- `@BeforeEach` → New `BrowserContext` + `Page` per test
- `@AfterEach` → Close context
- `@AfterAll` → Close browser and Playwright

Your tests simply **extend `BaseTest`** and use the shared `page` field.

---

## CI (Jenkins – recommended)
Use **Credentials Binding** and bind secrets to environment variables:
- `SAUCEDEMO_USERNAME`, `SAUCEDEMO_PASSWORD`

Example (Windows agent):
```bat
mvn -B -U clean test
```

(Optional) Allure report generation (if configured in your `pom.xml`):
```bat
mvn -B -DskipTests=true verify
```

> Tip: archive `target/surefire-reports/**` and any Allure results (`target/allure-results/**`) as build artifacts.

---

## Troubleshooting
- **Missing config value** → Set the key in `config.properties`, `secrets.local.properties`, or as ENV (remember **dot → underscore** when using ENV).
- **`4,00` vs `4.00`** (locale issues) → Enforce US locale in assertions or context:
  - Java side: `Locale.setDefault(Locale.US);`
  - Playwright context: `new Browser.NewContextOptions().setLocale("en-US")`.
- **Chrome not available on CI** → Use `browser.name=chromium` (bundled) or install Chrome on the agent.
- **Headed mode not opening** → Ensure `browser.headless=false` and the runner has a display (use Xvfb on Linux if needed).

---

## License
MIT
