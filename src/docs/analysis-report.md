# Analysis Report — Boyer–Moore Majority Vote
*Student:* Аруана  
*Pair assignment:* Pair 3 — Linear Array Algorithms (Student A — Boyer–Moore)

---

## 1. Algorithm Overview 
Boyer–Moore Majority Vote algorithm finds a candidate that could be a majority element (element appearing > n/2 times) in a single pass using two variables: `candidate` and `count`. The algorithm performs a linear scan to select a candidate and a second scan to verify that the candidate actually appears more than n/2 times.

Pseudocode:
1. Initialize `candidate = null`, `count = 0`.
2. For each element x:
    - If `count == 0`, set `candidate = x`, `count = 1`.
    - Else if `candidate == x`, `count++`, else `count--`.
3. Verify candidate by counting occurrences; if > n/2, return candidate, else indicate no majority.

Use-cases: majority detection in streams, voting consensus, data summarization when majority threshold is strict (> n/2).

---

## 2. Complexity Analysis 

### Time Complexity
- Candidate selection phase: one loop over n elements → Θ(n) time.
- Verification phase: another loop over n elements → Θ(n) time.
- Total: Θ(n) + Θ(n) = Θ(n).
- Best case: Even if array has immediate majority, algorithm still conducts two passes → Ω(n).
- Worst case: Still performs two passes → O(n).
  Therefore:
- Θ(n), O(n), Ω(n).

### Space Complexity
- Uses constant extra space: `candidate`, `count`, a few primitive counters and the `PerformanceTracker` (if used outside the algorithm for metrics).
- Auxiliary space: O(1).
- No recursion used.

### Notes on operation counts
- For precise empirical validation, the implementation tracks:
    - array accesses,
    - comparisons,
    - assignments.
      These counts grow linearly with n.

---

## 3. Code Review 

### Strengths
- Clean separation: algorithm (`algorithms`), metrics (`metrics`), CLI (`cli`).
- Defensive input validation: `null` array triggers explicit exception; empty array returns `null`.
- PerformanceTracker enables empirical validation and CSV export.
- Unit tests cover common edge-cases and a null input check.

### Potential inefficiencies & suggestions
- Currently uses two passes; this is inherent for exact majority verification. If probabilistic acceptance is allowed, verification could be sampled, but that's beyond assignment scope.
- Using `Objects.equals` ensures safe null comparisons; avoid additional boxing/unboxing where possible for primitives. For performance-critical contexts with large primitive arrays, an `int[]` specialized implementation would be slightly faster (avoids auto-boxing).
- The tracker increments every primitive operation; when benchmarking at large n this adds overhead. Provide a mode to disable tracking during tight microbenchmarks, or only record coarse metrics (time + occasional sampling).

### Space optimizations
- For integer arrays, provide an `int[]` overloaded variant to avoid `Integer[]` overhead.
- Avoid constructing large temporary structures in benchmark generator (current generator is in-place).

---

## 4. Empirical Results  — How to reproduce
### Benchmark plan
- Run benchmarks for n ∈ {100, 1_000, 10_000, 100_000}
- For each n and distribution (random, halfMajority, noMajority) run 5 iterations, record median timeNs and metrics.
- Use `BenchmarkRunner`:
  java -cp target/... cli.BenchmarkRunner 10000 random docs/perf.csv
- - Construct plots (timeNs vs n) on a log-log scale — should produce a straight line with slope ≈ 1 (linear).

### Expected observations
- Time should scale linearly with n.
- Operation counts (comparisons, arrayAccesses, assignments) are linear in n; ratios between counts provide insight into constant factors.
- When tracking is enabled, overhead of tracking will slightly increase time; compare runs with tracking disabled (add a boolean to skip increments) to measure overhead.

---

## 5. Conclusion 
- Boyer–Moore Majority Vote is optimal for exact majority detection in terms of time complexity (Θ(n)) and space (O(1)).
- The provided implementation is production-quality for assignment scope: clear, tested, benchmarkable, and instrumented.
- Recommendations:
- Add `int[]` specialized overload for performance (avoid autoboxing).
- Add an option to disable detailed tracking during microbenchmarks.
- Use JMH for high-accuracy microbenchmarks if precise timing is required.
