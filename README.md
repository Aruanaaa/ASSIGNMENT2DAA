# Assignment 2 — Boyer-Moore Majority Vote

# Structure
- `src/main/java/algorithms/BoyerMoore.java` — main algorithm implementation
- `src/main/java/metrics/PerformanceTracker.java` — simple metrics collection
- `src/main/java/cli/BenchmarkRunner.java` — CLI benchmark runner, CSV output
- `src/test/java/algorithms/BoyerMooreTest.java` — JUnit5 tests
- `docs/analysis-report.md` — analysis/report content (editable)
- `pom.xml` — Maven project

1. Build:
   mvn clean package

markdown

2. Run tests:
   mvn test

csharp


# CLI Benchmark
Create runnable jar with `mvn package` (shade plugin included), then:
java -cp target/assignment2-boyer-moore-1.0-SNAPSHOT.jar cli.BenchmarkRunner <n> <mode> <outCsv> [seed]

markdown

Modes:
- `random` — random data
- `halfMajority` — constructs an array with a majority element
- `noMajority` — constructs array guaranteed to have no majority

Example:
java -cp target/assignment2-boyer-moore-1.0-SNAPSHOT.jar cli.BenchmarkRunner 10000 random docs/performance-data.csv

shell


# Performance Data
CSV header:
timestamp,n,timeNs,comparisons,arrayAccesses,assignments,invalidInputs,majorityFound,majorityValue

perl


