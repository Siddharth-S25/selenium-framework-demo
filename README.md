# 🚀 Selenium Test Automation Framework

![Java](https://img.shields.io/badge/Java-11+-orange?style=flat-square&logo=java)
![Selenium](https://img.shields.io/badge/Selenium-4.27.0-green?style=flat-square&logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red?style=flat-square)
![Maven](https://img.shields.io/badge/Maven-3.6+-blue?style=flat-square&logo=apache-maven)
![Build](https://img.shields.io/badge/build-passing-brightgreen?style=flat-square)
![License](https://img.shields.io/badge/license-MIT-blue?style=flat-square)

A comprehensive, production-ready test automation framework built using Selenium WebDriver, TestNG, and Page Object Model design pattern. This framework demonstrates professional software testing practices and is designed for scalability, maintainability, and reliability.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Prerequisites](#-prerequisites)
- [Installation](#-installation)
- [Running Tests](#-running-tests)
- [Project Structure](#-project-structure)
- [Framework Architecture](#-framework-architecture)
- [Design Patterns](#-design-patterns)
- [Configuration](#-configuration)
- [Reports](#-reports)
- [Best Practices](#-best-practices)
- [Contributing](#-contributing)
- [License](#-license)
- [Contact](#-contact)

---

## ✨ Features

- ✅ **Page Object Model (POM)** - Clean separation of test logic and page interactions
- ✅ **Configuration-Driven** - Externalized configuration using properties files
- ✅ **Cross-Browser Support** - Chrome, Firefox, Edge with easy extensibility
- ✅ **Thread-Safe Design** - Parallel execution ready using ThreadLocal
- ✅ **Smart Waits** - Intelligent explicit waits, no hard-coded Thread.sleep()
- ✅ **Professional Reporting** - Beautiful HTML reports with ExtentReports
- ✅ **Screenshot on Failure** - Automatic screenshot capture for failed tests
- ✅ **Automatic Driver Management** - WebDriverManager handles driver binaries
- ✅ **Design Patterns** - Singleton, Factory, Builder patterns implemented
- ✅ **CI/CD Ready** - Prepared for Jenkins, GitHub Actions integration
- ✅ **Comprehensive Logging** - Detailed step-by-step execution logs
- ✅ **Error Handling** - Robust exception handling with fallback mechanisms

---

## 🛠 Tech Stack

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 23.0.2 | Programming Language |
| **Selenium WebDriver** | 4.27.0 | Browser Automation |
| **TestNG** | 7.10.2 | Testing Framework |
| **Maven** | 3.9.11 | Build & Dependency Management |
| **WebDriverManager** | 5.9.2 | Automatic Driver Management |
| **ExtentReports** | 5.1.2 | HTML Test Reporting |
| **Log4j** | 2.24.3 | Logging Framework |
| **Apache POI** | 5.3.0 | Excel Data Handling |
| **Jackson** | 2.18.2 | JSON Processing |

---

## 📦 Prerequisites

Before running this project, ensure you have:

1. **Java Development Kit (JDK) 11 or higher**
```bash
   java -version
   # Should show: java version "11" or higher
```

2. **Apache Maven 3.6 or higher**
```bash
   mvn -version
   # Should show: Apache Maven 3.6.x or higher
```

3. **Chrome Browser** (latest version)
    - Or Firefox/Edge if you prefer

4. **Git** (for version control)
```bash
   git --version
```

---

## 🔧 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/Siddharth-S25/selenium-framework-demo.git
cd selenium-framework-demo
```

### 2. Install Dependencies
```bash
mvn clean install -DskipTests
```

This will download all required dependencies (~200MB).

### 3. Verify Installation
```bash
mvn test-compile
```

You should see: `BUILD SUCCESS`

---

## ▶️ Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run Specific Test Class
```bash
mvn test -Dtest=ProductPageTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=ProductPageTest#testCompletePurchaseWithSizeL
```

### Run with Different Browser
```bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
```

### Run in Headless Mode
```bash
mvn test -Dheadless=true
```

### Run Using TestNG XML
```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## 📁 Project Structure
```
selenium-framework-demo/
│
├── src/
│   ├── main/
│   │   ├── java/com/automation/
│   │   │   ├── config/
│   │   │   │   └── ConfigReader.java          # Configuration management (Singleton)
│   │   │   ├── pages/
│   │   │   │   ├── BasePage.java              # Common page methods
│   │   │   │   └── ProductPage.java           # Product page object
│   │   │   ├── reports/
│   │   │   │   └── ExtentReportManager.java   # Report generation
│   │   │   ├── tests/
│   │   │   │   └── BaseTest.java              # Test setup/teardown
│   │   │   └── utils/
│   │   │       ├── DriverManager.java         # WebDriver management (Factory)
│   │   │       └── ScreenshotUtil.java        # Screenshot utilities
│   │   └── resources/
│   │       └── config.properties              # Configuration file
│   │
│   └── test/
│       └── java/com/automation/tests/
│           └── ProductPageTest.java           # Test cases
│
├── test-output/
│   ├── extent-reports/                        # HTML reports
│   └── screenshots/                           # Test screenshots
│
├── pom.xml                                    # Maven configuration
├── testng.xml                                 # TestNG suite configuration
├── .gitignore                                 # Git ignore file
└── README.md                                  # This file
```

---

## 🏗 Framework Architecture
```
┌─────────────────────────────────────────────────────────┐
│                    Test Layer (TestNG)                   │
│               - ProductPageTest.java                     │
│               - Test Cases & Assertions                  │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                  Page Object Layer                       │
│            - BasePage (Abstract Parent)                  │
│            - ProductPage (Concrete Child)                │
│            - Locators & Actions                          │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                Utility & Support Layer                   │
│      - DriverManager - ConfigReader - ReportManager     │
│      - ScreenshotUtil - BaseTest                        │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│               Configuration Layer                        │
│                - config.properties                       │
└─────────────────────────────────────────────────────────┘
```

---

## 🎨 Design Patterns

### 1. **Page Object Model (POM)**
- Separates test logic from UI interactions
- Each page is represented by a class
- Locators and actions are encapsulated

### 2. **Singleton Pattern**
- `ConfigReader` - Ensures single configuration instance
- Thread-safe implementation with double-check locking

### 3. **Factory Pattern**
- `DriverManager` - Creates browser instances based on configuration
- Easy to add new browser support

### 4. **Builder Pattern**
- Method chaining in page objects for fluent test writing
- Example: `page.selectSize("L").addToCart().checkout()`

---

## ⚙️ Configuration

All configurations are managed in `src/main/resources/config.properties`:
```properties
# Application Configuration
app.url=https://react-shopping-cart-67954.firebaseapp.com/
app.name=React Shopping Cart

# Browser Configuration
browser=chrome
headless=false
maximize=true

# Timeout Configuration (in seconds)
implicit.wait=10
explicit.wait=20
page.load.timeout=30

# Screenshot Configuration
screenshot.on.failure=true
screenshot.on.success=false

# Reporting Configuration
report.title=Selenium Test Automation Report
report.name=Test Execution Results
extent.report.folder=test-output/extent-reports
```

### Override at Runtime
```bash
mvn test -Dbrowser=firefox -Dheadless=true
```

---

## 📊 Reports

The framework generates comprehensive HTML reports using ExtentReports.

### Report Location
```
test-output/extent-reports/TestReport_[timestamp].html
```

### Report Features
- ✅ Test execution summary with pass/fail statistics
- ✅ Detailed step-by-step logs
- ✅ Screenshots on failures
- ✅ Execution time and duration
- ✅ System and environment information
- ✅ Filterable by status (pass/fail/skip)

### View Reports

After test execution:
```bash
# Windows
start test-output/extent-reports/TestReport_*.html

# Mac
open test-output/extent-reports/TestReport_*.html

# Linux
xdg-open test-output/extent-reports/TestReport_*.html
```

---

## 💡 Best Practices Implemented

### 1. **Wait Strategies**
- ✅ Explicit waits with ExpectedConditions
- ✅ No hard-coded Thread.sleep()
- ✅ Configurable timeout values
- ✅ Reusable wait methods in BasePage

### 2. **Code Organization**
- ✅ Clear separation of concerns
- ✅ Single Responsibility Principle
- ✅ DRY (Don't Repeat Yourself)
- ✅ Meaningful naming conventions

### 3. **Exception Handling**
- ✅ Try-catch blocks for critical operations
- ✅ Fallback mechanisms (JavaScript click)
- ✅ Informative error messages

### 4. **Maintainability**
- ✅ Centralized configuration
- ✅ Comprehensive Javadoc comments
- ✅ Modular architecture
- ✅ Easy to extend and scale

### 5. **Reporting & Logging**
- ✅ Detailed step-by-step logs
- ✅ Screenshots on failures
- ✅ Professional HTML reports
- ✅ Execution metrics

---

## 🧪 Test Cases

| Test Case | Description | Priority |
|-----------|-------------|----------|
| `testCompletePurchaseWithSizeL` | Verify complete purchase flow with size L filter | High |
| `testAddProductToCart` | Verify product can be added to cart | High |
| `testSizeFilterFunctionality` | Test all size filter options | Medium |
| `testPageTitle` | Verify page title display | Low |
| `testCartTotalDisplay` | Verify cart total calculation | High |
| `testEndToEndPurchaseFlow` | Complete end-to-end purchase scenario | Critical |

---

## 🚀 CI/CD Integration

### GitHub Actions (Ready)

The framework is ready for GitHub Actions integration. Sample workflow:
```yaml
name: Selenium Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
      - name: Run tests
        run: mvn clean test
```

### Jenkins Pipeline
```groovy
pipeline {
    agent any
    stages {
        stage('Checkout') { steps { checkout scm } }
        stage('Build') { steps { sh 'mvn compile' } }
        stage('Test') { steps { sh 'mvn test' } }
        stage('Report') { 
            steps { 
                publishHTML([
                    reportDir: 'test-output/extent-reports',
                    reportFiles: '*.html',
                    reportName: 'Test Report'
                ])
            }
        }
    }
}
```

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Your Name**

- GitHub: https://github.com/Siddharth-S25/selenium-framework-demo
- Email: ersabalesiddharth@gmail.com

---

## 🙏 Acknowledgments

- [Selenium](https://www.selenium.dev/) - Browser automation framework
- [TestNG](https://testng.org/) - Testing framework
- [ExtentReports](https://www.extentreports.com/) - Reporting library
- [WebDriverManager](https://github.com/bonigarcia/webdrivermanager) - Driver management

---

## ⭐ Show Your Support

If you find this project helpful, please give it a ⭐️!

---

**Made with ❤️ for the testing community**

**Last Updated:** January 2026