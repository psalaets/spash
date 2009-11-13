package org.spash;

public class SpaceStats {
    private int runs;
    private long totalFindPairsNanos;
    private long totalProcessOverlapsNanos;
    private long totalAddBodiesNanos;
    private long totalClearBodiesNanos;
    private int totalPairs;
    private int totalOverlaps;
    private int totalPairsFiltered;

    public long getTotalFindPairsNanos() {
        return totalFindPairsNanos;
    }

    public void addFindPairsNanos(long nanos) {
        totalFindPairsNanos += nanos;
    }

    public int getRunCount() {
        return runs;
    }

    public void runCompleted() {
        runs += 1;
    }

    public long getTotalProcessOverlapsNanos() {
        return totalProcessOverlapsNanos;
    }

    public void addProcessOverlapsNanos(long nanos) {
        totalProcessOverlapsNanos += nanos;
    }

    public int getTotalPairs() {
        return totalPairs;
    }

    public void pairFound() {
        totalPairs += 1;
    }

    public int getTotalOverlaps() {
        return totalOverlaps;
    }

    public void overlapFound() {
        totalOverlaps += 1;
    }

    public int getTotalPairsFiltered() {
        return totalPairsFiltered;
    }

    public void pairFiltered() {
        totalPairsFiltered += 1;
    }

    public long getTotalAddBodiesNanos() {
        return totalAddBodiesNanos;
    }

    public void addAddBodiesNanos(long amount) {
        totalAddBodiesNanos += amount;
    }

    public long getTotalClearBodiesNanos() {
        return totalClearBodiesNanos;
    }

    public void addClearBodiesNanos(long amount) {
        totalClearBodiesNanos += amount;
    }

    public void reset() {
        runs = 0;
        totalFindPairsNanos = 0;
        totalProcessOverlapsNanos = 0;
        totalPairsFiltered = 0;
        totalPairs = 0;
        totalOverlaps = 0;
        totalAddBodiesNanos = 0;
        totalClearBodiesNanos = 0;
    }

    public String getReport() {
        StringBuilder report = new StringBuilder();
        report.append(averageAddBodiesTime());
        report.append(averageFindPairsTime());
        report.append(averageClearBodiesTime());
        report.append(averageBroadPhaseTime());
        report.append("\nSpace\n");
        report.append(averageProcessOverlapsTime());
        report.append(averageCycleTime());
        report.append(averagePairs());
        report.append(averageOverlaps());
        report.append(averagePairsFiltered());
        return report.toString();
    }

    private static final String FORMAT = "%.4f";

    private String averageOverlaps() {
        return reportCount("avg overlaps", totalOverlaps);
    }

    private String averagePairsFiltered() {
        return reportCount("avg pairs filtered", totalPairsFiltered);
    }

    private String averagePairs() {
        return reportCount("avg pairs", totalPairs);
    }

    private String reportCount(String label, int count) {
        float average = (float) count / runs;
        return String.format(label + ": " + FORMAT, average) + "\n";
    }

    private String averageProcessOverlapsTime() {
        return reportTime("processOverlaps", totalProcessOverlapsNanos);
    }
    
    private String averageCycleTime() {
        return reportTime("time in space (add + processOverlaps + clear)", totalProcessOverlapsNanos + totalAddBodiesNanos + totalClearBodiesNanos);
    }

    private String averageBroadPhaseTime() {
        return reportTime("time in broadphase (add + find pairs + clear)", totalFindPairsNanos + totalAddBodiesNanos + totalClearBodiesNanos);
    }

    private String averageFindPairsTime() {
        return reportTime("findPairs", totalFindPairsNanos);
    }

    private String averageAddBodiesTime() {
        return reportTime("add bodies", totalAddBodiesNanos);
    }

    private String averageClearBodiesTime() {
        return reportTime("clear bodies", totalClearBodiesNanos);
    }

    private String reportTime(String label, long nanos) {
        double averageNanos = (double) nanos / runs;
        double averageMillis = averageNanos / 1000000;
        String nanosReport = String.format(label + ": " + FORMAT + " ns, ", averageNanos);
        String millisReport = String.format(FORMAT + " ms", averageMillis);
        return nanosReport + millisReport + "\n";
    }
}
