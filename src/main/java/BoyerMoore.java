package algorithms;

import metrics.PerformanceTracker;
import java.util.Objects;


public final class BoyerMoore {

    private BoyerMoore() {

    }


    public static Integer findMajority(Integer[] arr, PerformanceTracker tracker) {
        if (tracker == null) tracker = new PerformanceTracker();


        if (arr == null) {
            tracker.incrementInvalidInputs();
            throw new IllegalArgumentException("Input array is null");
        }
        if (arr.length == 0) return null;


        Integer candidate = null;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            tracker.incrementArrayAccesses(); // reading arr[i]
            Integer value = arr[i];
            tracker.incrementComparisons(); // compare count == 0
            if (count == 0) {
                candidate = value;
                tracker.incrementAssignments();
                count = 1;
                tracker.incrementAssignments();
            } else {
                tracker.incrementComparisons(); // candidate.equals?
                if (Objects.equals(candidate, value)) {
                    count++;
                    tracker.incrementAssignments();
                } else {
                    count--;
                    tracker.incrementAssignments();
                }
            }
        }


        if (candidate == null) return null;
        int occurrences = 0;
        for (int i = 0; i < arr.length; i++) {
            tracker.incrementArrayAccesses();
            tracker.incrementComparisons();
            if (Objects.equals(arr[i], candidate)) {
                occurrences++;
                tracker.incrementAssignments();
            }
        }

        if (occurrences > arr.length / 2) {
            tracker.setMajorityFound(true);
            tracker.setMajorityValue(candidate);
            tracker.setLastRunN(arr.length);
            return candidate;
        } else {
            tracker.setMajorityFound(false);
            tracker.setLastRunN(arr.length);
            return null;
        }
    }
}
