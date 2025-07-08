# GPU Factory Test Framework – Requirements

This is a Java-based GPU test framework for validating AMD Instinct hardware (POC).

---

## System Requirements

| Component  | Version          | Install Notes                    |
|------------|------------------|----------------------------------|
| Java       | OpenJDK 21       | `sudo apt install openjdk-21-jdk` |
| Maven      | Maven 3.6+       | `sudo apt install maven`         |
| Docker     | Latest (optional)| `sudo apt install docker.io`     |

> Make sure Docker group permissions are updated:  
> `sudo usermod -aG docker $USER && reboot`

---

## 📦 Maven Dependencies (Auto-resolved)

All Java dependencies are defined in `pom.xml`. Maven will download them automatically:
- `org.slf4j:slf4j-api:2.0.12`
- `ch.qos.logback:logback-classic:1.4.14`
- `com.opencsv:opencsv:5.9`
- `com.fasterxml.jackson.core:jackson-databind:2.17.1`
- `org.junit.jupiter:junit-jupiter:5.10.2`
- `org.jacoco:jacoco-maven-plugin:0.8.10`

---

## 🔍 Build & Test

```bash
# Clean and build project (with shaded JAR)
mvn clean package

# Run unit tests
mvn test

# Generate code coverage report
mvn verify
open target/site/jacoco/index.html

# Run executable JAR
java -jar target/gpu-factory-test-framework-1.0-SNAPSHOT-shaded.jar

# Run in Docker (after building image)
sudo docker build -t gpu-factory-test .
sudo docker run --rm gpu-factory-test
