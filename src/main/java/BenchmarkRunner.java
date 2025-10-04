package cli;

import algorithms.BoyerMoore;
import metrics.PerformanceTracker;

import java.io.IOException;
import java.util.Random;
import java.util.stream.IntStream;


public class BenchmarkRunner {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            System.err.println("Usage: BenchmarkRunner <n> <mode> <outCsv> [seed]");
            System.err.println("Modes: random | halfMajority | noMajority");
            System.exit(2);
        }

        int n = Integer.parseInt(args[0]);
        String mode = args[1];
        String outCsv = args[2];
        long seed = args.length >= 4 ? Long.parseLong(args[3]) : System.currentTimeMillis();
        Random rnd = new Random(seed);

        Integer[] arr = generateArray(n, mode, rnd);

        PerformanceTracker tracker = new PerformanceTracker();
        long t0 = System.nanoTime();
        Integer maj = BoyerMoore.findMajority(arr, tracker);
        long timeNs = System.nanoTime() - t0;

        // If CSV is empty/new, write header
        try (java.io.File f = new java.io.File(outCsv)) {
            if (!f.exists()) {
                try (java.io.PrintWriter pw = new java.io.PrintWriter(new java.io.FileWriter(outCsv, true))) {
                    pw.println("timestamp,n,timeNs,comparisons,arrayAccesses,assignments,invalidInputs,majorityFound,majorityValue");
                }
            }
        }

        tracker.appendCsvRecord(outCsv, timeNs);

        System.out.println("n=" + n + " mode=" + mode + " timeNs=" + timeNs + " maj=" + maj);
        System.out.println(tracker.toString());
    }

    private static Integer[] generateArray(int n, String mode, Random rnd) {
        switch (mode) {
            case "random":
                return IntStream.range(0, n).map(i -> rnd.nextInt(Math.max(2, n / 10))).boxed().toArray(Integer[]::new);
            case "halfMajority":
                // Create majority element with floor(n/2)+1 occurrences
                int maj = rnd.nextInt(1000000);
                Integer[] arr = new Integer[n];
                int majorityCount = n / 2 + 1;
                for (int i = 0; i < majorityCount; i++) arr[i] = maj;
                for (int i = majorityCount; i < n; i++) arr[i] = rnd.nextInt(Math.max(2, n / 10)) + 1 + maj;
                // Shuffle
                for (int i = n - 1; i > 0; i--) {
                    int j = rnd.nextInt(i + 1);
                    Integer tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
                }
                return arr;
            case "noMajority":
                // ensure no majority by repeating pairs with limited unique values
                Integer[] arr2 = new Integer[n];
                for (int i = 0; i < n; i += 2) {
                    int v = rnd.nextInt(Math.max(2, n / 10));
                    arr2[i] = v;
                    if (i + 1 < n) arr2[i + 1] = (v + 1) % (Math.max(2, n / 10));
                }
                return arr2;
            default:
                throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }
}
