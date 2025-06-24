# CI/CD Setup Guide for QuestApp Automation

This guide provides comprehensive instructions for setting up Continuous Integration and Continuous Deployment (CI/CD) pipelines for the QuestApp automation framework using Maven profiles.

## 📋 Table of Contents

1. [Overview](#overview)
2. [Maven Profiles](#maven-profiles)
3. [GitHub Actions Setup](#github-actions-setup)
4. [Jenkins Setup](#jenkins-setup)
5. [Azure DevOps Setup](#azure-devops-setup)
6. [GitLab CI/CD Setup](#gitlab-cicd-setup)
7. [Configuration Options](#configuration-options)
8. [Troubleshooting](#troubleshooting)

## 🎯 Overview

The CI/CD configurations are designed to work with the Maven profiles defined in `pom.xml`:

- **Smoke Tests**: Quick validation of critical functionality
- **Integration Tests**: Full test suite with different browser configurations
- **Parallel Tests**: Performance testing with parallel execution
- **Regression Tests**: Comprehensive testing for main branch
- **Security Tests**: Security-focused test scenarios
- **Performance Tests**: Load and performance testing

## 🏗️ Maven Profiles

### Available Profiles

| Profile | Description | Command |
|---------|-------------|---------|
| `smoke` | Quick smoke tests | `mvn test -Psmoke` |
| `chrome` | Chrome browser tests | `mvn test -Pchrome` |
| `chrome-headless` | Headless Chrome tests | `mvn test -Pchrome-headless` |
| `parallel` | Parallel test execution | `mvn test -Pparallel` |
| `regression` | Full regression suite | `mvn test -Pregression` |

### Profile Configuration

Each profile can be customized with system properties:

```bash
# Run with headless mode
mvn test -Psmoke -Dtest.headless=true

# Run with specific browser
mvn test -Pchrome -Dbrowser=chrome

# Run with custom timeout
mvn test -Pregression -Dtimeout=30
```

## 🚀 GitHub Actions Setup

### Prerequisites

1. GitHub repository with the automation code
2. GitHub Actions enabled
3. Secrets configured (if needed)

### Setup Steps

1. **Copy the workflow file**:
   ```bash
   cp .github/workflows/ci-cd.yml .github/workflows/
   ```

2. **Configure secrets** (optional):
   - Go to Settings → Secrets and variables → Actions
   - Add any required secrets (API keys, credentials, etc.)

3. **Customize the workflow**:
   - Edit `.github/workflows/ci-cd.yml`
   - Modify branch names, schedules, or job configurations

### Workflow Features

- **Multi-stage pipeline**: Build → Smoke → Integration → Parallel → Regression
- **Matrix testing**: Chrome and headless Chrome configurations
- **Scheduled runs**: Daily regression tests at 2 AM UTC
- **Artifact management**: Test reports and screenshots
- **Allure reporting**: Automated report generation

### Usage

```bash
# Manual trigger
git push origin main

# View results
# Go to Actions tab in GitHub repository
```

## 🔧 Jenkins Setup

### Prerequisites

1. Jenkins server with required plugins:
   - Pipeline
   - Git
   - Maven Integration
   - HTML Publisher
   - Workspace Cleanup

2. Tools configured:
   - JDK 21
   - Maven 3.9.6
   - Chrome browser

### Setup Steps

1. **Create a new Pipeline job**:
   - Go to Jenkins → New Item
   - Select "Pipeline"
   - Name: "QuestApp-Automation"

2. **Configure the pipeline**:
   - Pipeline script from SCM
   - SCM: Git
   - Repository URL: Your repository URL
   - Script path: `Jenkinsfile`

3. **Configure tools**:
   - Go to Manage Jenkins → Global Tool Configuration
   - Add JDK 21
   - Add Maven 3.9.6

### Pipeline Features

- **Declarative pipeline**: Modern Jenkins pipeline syntax
- **Parallel execution**: Multiple test configurations
- **Environment management**: Proper tool configuration
- **Reporting**: HTML and Allure reports
- **Notifications**: Email and Slack integration (configurable)

### Usage

```bash
# Trigger build
# Go to Jenkins job and click "Build Now"

# View results
# Click on build number → View test results
```

## ☁️ Azure DevOps Setup

### Prerequisites

1. Azure DevOps organization and project
2. Repository connected to Azure DevOps
3. Build agents with required tools

### Setup Steps

1. **Create a new pipeline**:
   - Go to Pipelines → New Pipeline
   - Select "Azure Repos Git" or "GitHub"
   - Choose your repository

2. **Configure the pipeline**:
   - Select "Existing Azure Pipelines YAML file"
   - Path: `/azure-pipelines.yml`

3. **Configure variables**:
   - Go to Edit → Variables
   - Add any required variables

### Pipeline Features

- **Multi-stage pipeline**: Build, test, report stages
- **Environment deployments**: Integration environment
- **Artifact publishing**: Test reports and results
- **Scheduled triggers**: Daily regression tests
- **Conditional execution**: Branch-specific behavior

### Usage

```bash
# Manual trigger
# Go to Pipelines → Run pipeline

# View results
# Click on pipeline run → View artifacts
```

## 🐳 GitLab CI/CD Setup

### Prerequisites

1. GitLab repository
2. GitLab CI/CD enabled
3. Docker support (for Selenium services)

### Setup Steps

1. **Copy the configuration**:
   ```bash
   cp .gitlab-ci.yml ./
   ```

2. **Configure variables** (optional):
   - Go to Settings → CI/CD → Variables
   - Add any required variables

3. **Set up runners** (if needed):
   - Configure shared runners or project-specific runners

### Pipeline Features

- **Docker-based execution**: Containerized test environment
- **Selenium services**: Chrome browser automation
- **Caching**: Maven dependencies caching
- **Artifacts**: Test reports and screenshots
- **Scheduled pipelines**: Daily regression tests

### Usage

```bash
# Automatic trigger on push
git push origin main

# Manual trigger
# Go to CI/CD → Pipelines → Run pipeline

# View results
# Go to CI/CD → Jobs → View job logs
```

## ⚙️ Configuration Options

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `JAVA_VERSION` | Java version to use | `21` |
| `MAVEN_VERSION` | Maven version | `3.9.6` |
| `TEST_BROWSER` | Default browser | `chrome` |
| `TEST_HEADLESS` | Headless mode | `true` |
| `TEST_PARALLEL` | Parallel execution | `false` |

### System Properties

| Property | Description | Default |
|----------|-------------|---------|
| `test.headless` | Run tests in headless mode | `false` |
| `browser` | Browser to use | `chrome` |
| `timeout` | Test timeout in seconds | `20` |
| `parallel` | Enable parallel execution | `false` |

### Customization Examples

```bash
# GitHub Actions
- name: Run Custom Tests
  run: ./mvnw test -Psmoke -Dtest.headless=true -Dtimeout=30

# Jenkins
sh './mvnw test -Pregression -Dbrowser=chrome -Dtest.headless=true'

# Azure DevOps
- script: './mvnw test -Pparallel -Dtest.headless=true'
  displayName: 'Run Parallel Tests'

# GitLab CI
script:
  - ./mvnw test -Psmoke -Dtest.headless=true
```

## 🔍 Troubleshooting

### Common Issues

#### 1. Maven Dependencies Not Found
```bash
# Solution: Clear Maven cache
./mvnw clean
rm -rf ~/.m2/repository
./mvnw dependency:resolve
```

#### 2. Chrome/WebDriver Issues
```bash
# Solution: Update Chrome and ChromeDriver
# Ensure Chrome version matches ChromeDriver version
```

#### 3. Test Failures in CI
```bash
# Solution: Check logs and screenshots
# Verify test data and environment
# Check for timing issues
```

#### 4. Memory Issues
```bash
# Solution: Increase JVM memory
export MAVEN_OPTS="-Xmx2g -XX:MaxPermSize=512m"
```

### Debug Commands

```bash
# Run with debug output
./mvnw test -Psmoke -X

# Run specific test class
./mvnw test -Dtest=LoginTest

# Run with detailed logging
./mvnw test -Psmoke -Dwebdriver.log.level=DEBUG
```

### Log Analysis

1. **TestNG Reports**: Check `target/surefire-reports/`
2. **Allure Reports**: Check `target/allure-report/`
3. **Screenshots**: Check `screenshots/` directory
4. **CI Logs**: Check pipeline/job logs

## 📊 Monitoring and Reporting

### Available Reports

1. **TestNG Reports**: HTML reports with test results
2. **Allure Reports**: Rich test reporting with trends
3. **Screenshots**: Visual evidence of test failures
4. **CI/CD Metrics**: Build times, success rates

### Report Access

- **GitHub Actions**: Artifacts tab in Actions
- **Jenkins**: Build page → Test Results
- **Azure DevOps**: Pipeline run → Artifacts
- **GitLab**: Job page → Artifacts

## 🔄 Best Practices

1. **Branch Strategy**: Use feature branches and merge requests
2. **Test Data**: Use isolated test data for CI/CD
3. **Environment**: Separate test environments for different stages
4. **Monitoring**: Set up alerts for test failures
5. **Documentation**: Keep test documentation updated

## 📞 Support

For issues or questions:

1. Check the troubleshooting section
2. Review CI/CD logs and reports
3. Verify Maven profile configurations
4. Test locally before pushing to CI/CD

---

**Note**: This setup provides a robust CI/CD foundation that can be customized based on your specific requirements and infrastructure. 