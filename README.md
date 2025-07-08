\# GPU Factory Test Framework



The GPU Factory Test Framework is a Java 21-based modular validation suite for post-silicon GPU bring-up and production testing. It automates PCIe link verification, thermal monitoring, memory checks, fan speed validation, and power consumption analysis. The framework is designed to integrate seamlessly with CI/CD pipelines and factory automation systems.

---

\## Features



\- PCIe link training validation  

\- Thermal monitoring  

\- GPU memory usage validation  

\- Fan speed verification  

\- Power consumption analysis  

\- Command-line interface for task selection and device ID targeting  

\- Report export in JSON, CSV, and ZIP formats  

\- Unit tests and integration tests using JUnit 5  

\- Docker support for reproducible builds and isolated execution  



---



\## Build Instructions



Ensure Java 21 and Maven are installed.



```bash

mvn clean package





Output:



```bash

target/gpu-factory-test-framework-1.0-SNAPSHOT.jar





\## Running the Framework



java -jar target/gpu-factory-test-framework-1.0-SNAPSHOT.jar --device-id GPU-001 --export



\## Command-Line Flags



| Flag             | Description                                                 |

| ---------------- | ----------------------------------------------------------- |

| `--device-id`    | Set the GPU identifier (default: GPU-001)                   |

| `--tasks`        | Comma-separated list of tasks to run (e.g., `pcie,thermal`) |

| `--export`       | Export report files in JSON, CSV, and ZIP formats           |

| `--summary-only` | Suppress detailed task logs and show only the summary       |



\## Validation Tasks



Each validation is implemented as a class that conforms to the ValidationTask interface.

| Task                    | Class Name                   | Description                         |

| ----------------------- | ---------------------------- | ----------------------------------- |

| PCIe Link Validation    | `PCIeLinkValidation`         | Validates PCIe link training status |

| Thermal Monitoring      | `ThermalMonitorCheck`        | Checks and logs GPU temperature     |

| GPU Memory Usage        | `GpuMemoryUsageValidation`   | Validates used vs. total memory     |

| Fan Speed Validation    | `FanSpeedValidation`         | Validates current fan RPM           |

| Power Consumption Check | `PowerConsumptionValidation` | Logs and checks current wattage     |



\## Exported Reports



When run with the --export flag, the following files are generated in the reports/ directory:



report.json – Full structured test results



report.csv – Tabular report for spreadsheets



report\_bundle.zip – Archived export containing all artifacts



\## Testing



mvn test



\## Test Coverage



TestRunnerTest.java: Unit tests for task sequencing, failures, and multi-GPU flows



IntegrationFlowTest.java: End-to-end validation of task orchestration



JaCoCo code coverage report is generated under target/site/jacoco

To view JaCoCo report:

bash

xdg-open target/site/jacoco/index.html



\## Docker Support



```Build the Image



sudo docker build -t gpu-factory-test .



```Run in Container



sudo docker run --rm gpu-factory-test --device-id GPU-001 --export



To persist exported reports to host:



sudo docker run -v $PWD/reports:/app/reports gpu-factory-test --export



\## Development Notes



Java version: 21

Build tool: Maven

Logging: SLF4J + Logback

JSON export: Jackson

CSV export: OpenCSV

Testing: JUnit 5

Code coverage: JaCoCo

Containerization: Docker (Eclipse Temurin JDK base)



\## Directory Structure



gpu-java-testsuite/

├── src/

│   ├── main/java/com/amd/validation/      # Core logic

│   └── test/java/com/amd/validation/      # JUnit test classes

├── Dockerfile

├── pom.xml

└── README.md



=== Test Summary ===

• PCIeLinkValidation        : PASS (Link OK)

• ThermalMonitorCheck       : PASS (Thermals OK)

• GpuMemoryUsageValidation  : PASS (Used 18.8% of GPU memory)

• FanSpeedValidation        : PASS (Fan speed normal)

• PowerConsumptionValidation : PASS (GPU Power Usage: 110.0W)

Total: 5  Passed: 5  Failed: 0


## About This POC

About This POC
This is a standalone proof-of-concept developed from scratch as a follow-up to technical discussions with AMD. It is intended to demonstrate:

Hands-on development expertise with Java 21

Modular, extensible, test-driven design

Familiarity with system-level GPU validation workflows

Real-world integration of automation, reporting, and containerization

The framework reflects both software engineering capability and domain understanding in GPU validation environments.











