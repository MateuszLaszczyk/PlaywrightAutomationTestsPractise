🧪 Playwright Java Test Automation Project
This project is a test automation framework built using Microsoft Playwright with Java and JUnit 5. It includes modular page objects and configuration management for scalable and maintainable test development.

⚙️ Configuration
Create a file at:
src/test/resources/config.properties

🧩 Dependencies
You’ll need the following libraries:
Playwright for Java
JUnit Jupiter (JUnit 5)
 Maven build system

🚀 Running the Tests
Using Maven:
mvn test
Using Gradle:
./gradlew test
Playwright will automatically download its required browser binaries on the first run.

Generating reports using Allure
Under src/test/resources create allure.properties file with following line:
**allure.results.directory=target/allure-results**
Then run: mvn clean test allure:serve


🛡️ Best Practices
Use the Page Object Model (POM) for reusable UI logic

Store credentials in external files (never hardcode)

Use @BeforeAll/@AfterAll for test suite lifecycle management

Avoid exposing passwords in logs or console output

📌 Future Enhancements

Extend test coverage to other user flows (add to cart, logout, etc.)

Integrate with CI/CD (GitHub Actions, GitLab, Jenkins)

👨‍💻 Author
M.Laszczyk
