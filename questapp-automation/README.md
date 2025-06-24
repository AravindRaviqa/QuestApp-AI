# QuestApp Automation Framework

A Selenium TestNG automation framework for testing the QuestApp application, built with Maven.

## 🚀 Quick Start

### Prerequisites
- Java 21 or higher
- Maven 3.6+ (or use the included Apache Maven 3.9.6)
- Chrome browser

### Running Tests

#### Basic Test Execution
```bash
# Run all tests
./apache-maven-3.9.6/bin/mvn test

# Clean and run tests
./apache-maven-3.9.6/bin/mvn clean test

# Run specific test class
./apache-maven-3.9.6/bin/mvn test -Dtest=LoginTest

# Run specific test method
./apache-maven-3.9.6/bin/mvn test -Dtest=LoginTest#testSuccessfulLogin
```

#### Using Maven Profiles

##### Chrome Profiles
```bash
# Run with Chrome (default)
./apache-maven-3.9.6/bin/mvn test -Pchrome

# Run with headless Chrome
./apache-maven-3.9.6/bin/mvn test -Pchrome-headless
```

##### Parallel Execution
```bash
# Run tests in parallel (3 threads)
./apache-maven-3.9.6/bin/mvn test -Pparallel
```

##### Test Categories
```bash
# Run smoke tests (when testng-smoke.xml is created)
./apache-maven-3.9.6/bin/mvn test -Psmoke

# Run regression tests (when testng-regression.xml is created)
./apache-maven-3.9.6/bin/mvn test -Pregression
```

#### Advanced Maven Commands

##### With System Properties
```bash
# Run with custom browser
./apache-maven-3.9.6/bin/mvn test -Dtest.browser=chrome -Dtest.headless=true

# Run with custom timeout
./apache-maven-3.9.6/bin/mvn test -Dwebdriver.timeout=30

# Run with custom base URL
./apache-maven-3.9.6/bin/mvn test -Dbase.url=https://questapp.dev/admin
```

##### Test Suite Execution
```bash
# Run specific TestNG suite
./apache-maven-3.9.6/bin/mvn test -Dsurefire.suiteXmlFiles=testng.xml

# Run multiple suites
./apache-maven-3.9.6/bin/mvn test -Dsurefire.suiteXmlFiles=testng.xml,testng-smoke.xml
```

## 📁 Project Structure

```
questapp-automation/
├── src/
│   ├── main/
│   │   └── java/                    # Main source code (if any)
│   └── test/
│       ├── java/
│       │   └── com/questapp/
│       │       ├── config/          # Configuration classes
│       │       ├── pages/           # Page Object Model classes
│       │       └── tests/           # Test classes
│       └── resources/
│           ├── test-images/         # Test data images
│           └── drivers/             # WebDriver executables (optional)
├── target/                          # Maven build output
├── screenshots/                     # Test screenshots
├── test-output/                     # TestNG reports
├── pom.xml                          # Maven configuration
├── testng.xml                       # TestNG suite configuration
└── README.md                        # This file
```

## 🔧 Configuration

### Maven Profiles

The framework includes several Maven profiles for different execution scenarios:

| Profile | Description | Usage |
|---------|-------------|-------|
| `default` | Default configuration | `mvn test` |
| `chrome` | Chrome browser | `mvn test -Pchrome` |
| `chrome-headless` | Headless Chrome | `mvn test -Pchrome-headless` |
| `parallel` | Parallel execution | `mvn test -Pparallel` |
| `smoke` | Smoke test suite | `mvn test -Psmoke` |
| `regression` | Regression test suite | `mvn test -Pregression` |

### System Properties

You can override default settings using system properties:

| Property | Default | Description |
|----------|---------|-------------|
| `test.browser` | chrome | Browser to use |
| `test.headless` | false | Run in headless mode |
| `test.parallel` | false | Enable parallel execution |
| `test.threads` | 2 | Number of parallel threads |
| `webdriver.timeout` | 20 | WebDriver wait timeout |
| `base.url` | https://questapp.dev/admin | Application base URL |

## 📊 Reporting

### TestNG Reports
TestNG generates HTML reports in the `test-output/` directory:
```bash
# Open TestNG report
start test-output/index.html
```

### Allure Reports
Allure provides detailed test reporting:
```bash
# Generate Allure report
./apache-maven-3.9.6/bin/mvn allure:report

# Serve Allure report locally
./apache-maven-3.9.6/bin/mvn allure:serve
```

## 🧹 Cleanup

### Maven Clean
```bash
# Clean all generated files
./apache-maven-3.9.6/bin/mvn clean

# Clean includes screenshots and test-output
./apache-maven-3.9.6/bin/mvn clean
```

## 🔍 Debugging

### Verbose Output
```bash
# Run with debug output
./apache-maven-3.9.6/bin/mvn test -X

# Run with info output
./apache-maven-3.9.6/bin/mvn test -e
```

### Test Debugging
```bash
# Run single test with debug
./apache-maven-3.9.6/bin/mvn test -Dtest=LoginTest -Dmaven.surefire.debug
```

## 📝 Best Practices

### 1. Test Organization
- Group related tests using TestNG groups
- Use descriptive test method names
- Follow Page Object Model pattern

### 2. Configuration Management
- Use `Configuration.java` for environment-specific settings
- Store sensitive data in environment variables
- Use Maven profiles for different environments

### 3. Test Data Management
- Store test data in `src/test/resources`
- Use data providers for parameterized tests
- Clean up test data after tests

### 4. Reporting
- Add meaningful test descriptions
- Use Allure annotations for better reporting
- Take screenshots on test failures

## 🐛 Troubleshooting

### Common Issues

#### 1. WebDriver Issues
```bash
# Update WebDriver
./apache-maven-3.9.6/bin/mvn clean compile
```

#### 2. Test Timeouts
```bash
# Increase timeout
./apache-maven-3.9.6/bin/mvn test -Dwebdriver.timeout=30
```

#### 3. Memory Issues
```bash
# Increase JVM memory
./apache-maven-3.9.6/bin/mvn test -Xmx2g
```

### Logs
- Maven logs: Check console output
- TestNG logs: `test-output/` directory
- Allure logs: `target/allure-results/` directory

## 🤝 Contributing

1. Follow the existing code structure
2. Add appropriate comments and documentation
3. Update this README for new features
4. Test your changes thoroughly

## 📄 License

This project is for internal use at QuestApp.

## 📞 Support

For issues and questions:
1. Check the troubleshooting section
2. Review test logs and reports
3. Contact the automation team 