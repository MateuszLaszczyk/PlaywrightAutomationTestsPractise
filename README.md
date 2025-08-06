🧪 Playwright Java Test Automation Project
This project is a test automation framework built using Microsoft Playwright with Java and JUnit 5. It includes modular page objects and configuration management for scalable and maintainable test development.

📁 Project Structure
.
├── src
│   ├── main
│   │   └── java
│   │       └── pw/mlaszczyk/automation/pages/pages
│   │           └── LoginPage.java         # Page Object Model for login
│   └── test
│       ├── java
│       │   └── pw/mlaszczyk/automation/tests
│       │       └── LoginTest.java         # JUnit test case for login
│       └── resources
│           └── config.properties          # Environment-specific credentials
├── README.md
└── pom.xml or build.gradle
⚙️ Configuration
Create a file at:
src/test/resources/config.properties

🧩 Dependencies
You’ll need the following libraries:
Playwright for Java
JUnit Jupiter (JUnit 5)
(Optional) Maven or Gradle build system

Example for pom.xml (Maven):

xml
<dependencies>
<dependency>
<groupId>com.microsoft.playwright</groupId>
<artifactId>playwright</artifactId>
<version>1.44.0</version>
</dependency>
<dependency>
<groupId>org.junit.jupiter</groupId>
<artifactId>junit-jupiter</artifactId>
<version>5.10.0</version>
<scope>test</scope>
</dependency>
</dependencies>
Or for build.gradle (Gradle):

groovy
dependencies {
testImplementation 'com.microsoft.playwright:playwright:1.44.0'
testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
}

🚀 Running the Tests
Using Maven:
mvn test
Using Gradle:
./gradlew test
Playwright will automatically download its required browser binaries on the first run.


🧱 Key Components
Component	Description
LoginPage	Page Object Model for the login page
LoginTest	Test class that performs login validation
ConfigLoader	Loads properties from a centralized config file

🛡️ Best Practices
Use the Page Object Model (POM) for reusable UI logic

Store credentials in external files (never hardcode)

Use @BeforeAll/@AfterAll for test suite lifecycle management

Avoid exposing passwords in logs or console output

📌 Future Enhancements
Add reporting (Allure, HTML)

Extend test coverage to other user flows (add to cart, logout, etc.)

Integrate with CI/CD (GitHub Actions, GitLab, Jenkins)

👨‍💻 Author
M.Laszczyk
