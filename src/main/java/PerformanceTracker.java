package metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;


public class PerformanceTracker {
    private long comparisons = 0;
    private long arrayAccesses = 0;
    private long assignments = 0;
    private long memoryAllocations = 0;
    private long invalidInputs = 0;
    private boolean majorityFound = false;
    private Integer majorityValue = null;
    private int lastRunN = 0;

    public void incrementComparisons() { comparisons++; }
    public void incrementArrayAccesses() { arrayAccesses++; }
    public void incrementAssignments() { assignments++; }
    public void incrementMemoryAllocations() { memoryAllocations++; }
    public void incrementInvalidInputs() { invalidInputs++; }

    public long getComparisons() { return comparisons; }
    public long getArrayAccesses() { return arrayAccesses; }
    public long getAssignments() { return assignments; }
    public long getMemoryAllocations() { return memoryAllocations; }
    public long getInvalidInputs() { return invalidInputs; }

    public void setMajorityFound(boolean f) { majorityFound = f; }
    public boolean isMajorityFound() { return majorityFound; }
    public void setMajorityValue(Integer v) { majorityValue = v; }
    public Integer getMajorityValue() { return majorityValue; }
    public void setLastRunN(int n) { lastRunN = n; }
    public int getLastRunN() { return lastRunN; }


    public void appendCsvRecord(String filePath, long timeNs) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath, true))) {
            pw.printf("%s,%d,%d,%d,%d,%d,%d,%b,%s%n",
                    Instant.now().toString(),
                    lastRunN,
                    timeNs,
                    comparisons,
                    arrayAccesses,
                    assignments,
                    invalidInputs,
                    majorityFound,
                    majorityValue == null ? "null" : majorityValue.toString()
            );
        }
    }

    @Override
    public String toString() {
        return "PerformanceTracker{" +
                "comparisons=" + comparisons +
                ", arrayAccesses=" + arrayAccesses +
                ", assignments=" + assignments +
                ", memoryAllocations=" + memoryAllocations +
                ", invalidInputs=" + invalidInputs +
                ", majorityFound=" + majorityFound +
                ", majorityValue=" + majorityValue +
                ", lastRunN=" + lastRunN +
                '}';
    }
}
