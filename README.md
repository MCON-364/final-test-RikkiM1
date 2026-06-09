[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/VZ9dTvEO)
# MCON-364 Final Exam — Java Streams, Collections & Concurrency

## Overview

This project is the final exam for **MCON-364**. It contains **four independent
programming problems**, each targeting a different area of the Java standard library.
You must implement the bodies of the provided skeleton classes; 
Make sure to submit code that compiles to get credit for completed items.

---

## Problem 1 — `IncidentAnalyzer` (32 pts)

**File:** `src/main/java/.../IncidentAnalyzer.java`

### Scenario
An IT operations team tracks support tickets. Each `SupportTicket` record carries
an `id`, `category`, `Priority` (`HIGH / MEDIUM / LOW`), `minutesToResolve`, and
a `resolved` flag.

### What you must implement

| Method | Description                                                                 |
|---|-----------------------------------------------------------------------------|
| `IncidentAnalyzer(List<SupportTicket>)` | Validate non-null; store a **defensive copy** (`List.copyOf`)               |
| `long getClosedCount()` | Count tickets where `resolved == true`                                      |
| `double getAverageTimeToClose()` | Average `minutesToResolve` of resolved tickets only; return `0.0` when none |
| `Map<String, Long> getCountByCategory()` | Group all tickets by category and count; return an **unmodifiable** map     |
| `List<SupportTicket> getCriticalOpenIncidents()` | Return an **unmodifiable** list                                             |

### Rules
- Express all logic with **streams** — no explicit loops.
- Return types must be **unmodifiable** where the table says so.

---

## Problem 2 — `ProductReviewAnalyzer` (36 pts)

**File:** `src/main/java/.../ProductReviewAnalyzer.java`

### Scenario
An e-commerce platform tags each customer review with a product category string
(e.g., `"electronics"`, `"books"`). You receive a flat `List<String>` of those tags.

### What you must implement

| Method | Description |
|---|---|
| `ProductReviewAnalyzer(List<String>)` | Validate non-null; store a **defensive copy** |
| `Map<String, Long> buildCategoryFrequencyMap()` | Count occurrences of each tag; result must be alphabetically sorted |
| `List<String> getTopNCategories(int n)` | Return the `n` most-reviewed categories, **highest count first** |
| `List<String> getCategoriesStartingWith(char prefix)` | Return sorted category names whose first letter equals `prefix` |
| `Optional<String> getMostReviewedInRange(String from, String to)` | Highest-count category in the **inclusive** alphabetical range `[from, to]`; `Optional.empty()` if the range is empty |

### Rules
- `buildCategoryFrequencyMap()` **must** return an appropriate implementation of the Map interface.
- Use `NavigableMap` slicing operations  — no manual iteration.
- Express logic with **streams and collectors** — no explicit loops.

---

## Problem 3 — `ConcurrentAuctionTracker` (18 pts)

**File:** `src/main/java/.../ConcurrentAuctionTracker.java`

### Scenario
An auction platform receives bid submissions from many bidders simultaneously.
The tracker must maintain bids in **sorted order** (highest first) and remain
**safe under concurrent access**.

### What you must implement

| Method | Description                                                                     |
|---|---------------------------------------------------------------------------------|
| `void submitBid(BidEntry)`| Add a new bid to the tracker; bids must be kept in sorted order (highest first) |
| `List<BidEntry> getTopN(int n)` | Return the top `n` bids (highest first) as an **immutable list**                |
| `int getTotalBids()` | Return how many times `submitBid` has been called                               |
| `void runSimulation(List<String> bidders, int bidsEach)` | Simulate bid submission by multiple bidders|                                     |                                                        |

### Rules
- **No `synchronized` blocks.** Use appropriate concurrent data structures and types.
- `runSimulation` must wait for all tasks to finish before returning. 

---

## Problem 4 — `TelemetryProcessor` (14 pts)

**File:** `src/main/java/.../TelemetryProcessor.java`

### Scenario
A fleet of smart devices continuously emits telemetry readings. Each `TelemetryEvent`
record carries a `deviceId` (String), a numeric `metric` (double), and a
`recordedAtNanos` timestamp (long). Readings arrive faster than they can be processed
synchronously.

### What you must implement

| Method | Description                                                                                                              |
|---|--------------------------------------------------------------------------------------------------------------------------|
| `void submit(TelemetryEvent)` | Enqueue an event for processing; throw `IllegalArgumentException` if `null`; silently discard if called before `start()` |
| `void start(int workerCount)` | Start processing of events                                                                                               |
| `void stop()` | Stop processing of events. All events most be processed                                                                  |
| `int getTotalProcessed()` | Return the running total of fully processed events                                                                       |
| `DoubleSummaryStatistics getStats()` | Return a **fresh snapshot** of summary statistics (count, sum, min, max, average) for all processed metric values        |

### Rules
- **No raw `synchronized` blocks.** Use `java.util.concurrent` building blocks — `BlockingQueue`, `ExecutorService`, `AtomicInteger`, `AtomicReference`, etc.
- `stop()` must guarantee that every event submitted before the call is fully processed before it returns.
- `getStats()` must return a **new, independent** `DoubleSummaryStatistics` object on every call.

---

## Project structure

```
src/
  main/java/.../
    IncidentAnalyzer.java          ← implement this
    ProductReviewAnalyzer.java     ← implement this
    ConcurrentAuctionTracker.java  ← implement this
    TelemetryProcessor.java        ← implement this
    SupportTicket.java           (record, provided)
    Priority.java                (enum, provided)
    TelemetryEvent.java          (record, provided)
    BidEntry.java                (record, provided)
  test/java/.../
    IncidentAnalyzerTest.java    (autograder, 16 tests)
    ProductReviewAnalyzerTest.java (autograder, 18 tests)
    ConcurrentAuctionTrackerTest.java (autograder, 9 tests)
    TelemetryProcessorTest.java  (autograder, 18 tests)
    StarterSmokeTest.java        (compile check, ungraded)
```

---

## Building & running tests locally

A full set of unit tests is provided in `src/test/java/.../`. You can run them with IntelliJ or from the command line with Maven:
```bash
# compile
mvn -B test-compile

# run all tests
mvn test

# run one problem at a time
mvn test -Dtest=IncidentAnalyzerTest
mvn test -Dtest=ProductReviewAnalyzerTest
mvn test -Dtest=ConcurrentAuctionTrackerTest
mvn test -Dtest=TelemetryProcessorTest
```

**Requirements:** Java 21 · Maven 3.8+

---

## Grading

Each test method is graded individually by the GitHub Classroom autograder.

| Test class | Tests | Pts each | Subtotal |
|---|---|---|---|
| `IncidentAnalyzerTest` | 16 | 2 | 32 |
| `ProductReviewAnalyzerTest` | 18 | 2 | 36 |
| `ConcurrentAuctionTrackerTest` | 9 | 2 | 18 |
| `TelemetryProcessorTest` — init & validation | 6 | 1 | 6 |
| `TelemetryProcessorTest` — functional | 4 | 2 | 8 |
| **Total** | **53** | | **100** |

stage, commit and push to main.

Good luck!
