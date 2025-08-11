# Playwright Java – E2E Tests

Minimal E2E suite using **Playwright for Java** + **JUnit 5** + **Maven**.

## Requirements
- JDK 21 (Temurin LTS recommended)
- Maven 3.9+

## Project layout
src/test/java/...
└─ pw/mlaszczyk/automation/tests/BaseTest.java # shared setup
└─ pw/mlaszczyk/automation/config/configLoader.java
src/test/resources/config.properties # non-secret config
config/secrets.example.properties # template (tracked)
config/secrets.local.properties # real secrets (ignored)

## Configuration

**Public (tracked):** `src/test/resources/config.properties`
properties
# Browser
browser.headless=false
browser.name=chrome   # chromium | chrome | firefox | webkit

# App
saucedemo.baseUrl=https://www.saucedemo.com
saucedemo.mainPageUrl=https://www.saucedemo.com/inventory.html

# Non-secret test data
saucedemo.invalidusername=<INVALID_USERNAME_FOR_NEGATIVE_TEST>
Secrets (never commit real values):

Local file (dev): config/secrets.local.properties

properties
saucedemo.username=<YOUR_USERNAME>
saucedemo.password=<YOUR_PASSWORD>
Or environment variables (CI):
The loader maps . → _ and uppercases:

pgsql
saucedemo.username  → SAUCEDEMO_USERNAME
saucedemo.password  → SAUCEDEMO_PASSWORD
browser.headless    → BROWSER_HEADLESS
browser.name        → BROWSER_NAME

How to run (local)
mvn clean test
First run downloads Playwright browsers. Control headless/GUI via browser.headless (file or ENV).

BaseTest (what you get)
@BeforeAll → Playwright + browser launch (type/headless from config)

@BeforeEach → new BrowserContext + Page per test

@AfterEach → closes context

@AfterAll → closes browser/Playwright
Your tests just extends BaseTest and use the page field.

CI (Jenkins – recommended)
Use Credentials Binding and bind secrets to:

SAUCEDEMO_USERNAME, SAUCEDEMO_PASSWORD
Build step:

bat
mvn -B -U clean test
(Optional) Allure report:

bat
mvn -B -DskipTests=true verify

Troubleshooting (quick)
Missing config value → set the key in config.properties, secrets.local.properties, or ENV (remember dot → underscore).
4,00 vs 4.00 → enforce Locale.US and/or en-US context, or assert with a regex [,.].
