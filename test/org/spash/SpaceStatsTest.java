package org.spash;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.spash.SpaceStats;

public class SpaceStatsTest {
    @Test
    public void TotalFindPairsNanos_StartsAtZero() {
        SpaceStats stats = new SpaceStats();

        assertEquals(0L, stats.getTotalFindPairsNanos());
    }

    @Test
    public void AddFindPairsNanos_IncreasesTotalFindPairsNanosByAmount() {
        SpaceStats stats = new SpaceStats();

        stats.addFindPairsNanos(5);

        assertEquals(5L, stats.getTotalFindPairsNanos());
    }

    @Test
    public void RunCount_StartsAtZero() {
        SpaceStats stats = new SpaceStats();

        assertEquals(0, stats.getRunCount());
    }

    @Test
    public void RunCompleted_IncreasesRunCountByOne() {
        SpaceStats stats = new SpaceStats();

        stats.runCompleted();

        assertEquals(1, stats.getRunCount());
    }

    @Test
    public void TotalProcessOverlapsNanos_StartsAtZero() {
        SpaceStats stats = new SpaceStats();

        assertEquals(0L, stats.getTotalProcessOverlapsNanos());
    }

    @Test
    public void AddProcessOverlapsNanos_IncreasesTotalProcessOverlapsNanosNanosByAmount() {
        SpaceStats stats = new SpaceStats();

        stats.addProcessOverlapsNanos(5);

        assertEquals(5L, stats.getTotalProcessOverlapsNanos());
    }

    @Test
    public void TotalPairsFound_StartsAtZero() {
        SpaceStats stats = new SpaceStats();

        assertEquals(0, stats.getTotalPairs());
    }

    @Test
    public void PairFound_IncreasesTotalPairsByOne() {
        SpaceStats stats = new SpaceStats();

        stats.pairFound();

        assertEquals(1, stats.getTotalPairs());
    }

    @Test
    public void TotalOverlaps_StartsAtZero() {
        SpaceStats stats = new SpaceStats();

        assertEquals(0, stats.getTotalOverlaps());
    }

    @Test
    public void OverlapFound_IncreasesTotalOverlapsByOne() {
        SpaceStats stats = new SpaceStats();

        stats.overlapFound();

        assertEquals(1, stats.getTotalOverlaps());
    }

    @Test
    public void TotalPairFilterings_StartsAtZero() {
        SpaceStats stats = new SpaceStats();

        assertEquals(0, stats.getTotalPairsFiltered());
    }

    @Test
    public void PairFiltered_IncreasesTotalPairFilteringsByOne() {
        SpaceStats stats = new SpaceStats();

        stats.pairFiltered();

        assertEquals(1, stats.getTotalPairsFiltered());
    }

    @Test
    public void AddBodiesNanos_StartsAtZero() {
        SpaceStats stats = new SpaceStats();

        assertEquals(0L, stats.getTotalAddBodiesNanos());
    }

    @Test
    public void AddAddBodiesNanos_IncreasesAddBodiesNanosByAmount() {
        SpaceStats stats = new SpaceStats();

        stats.addAddBodiesNanos(5);

        assertEquals(5L, stats.getTotalAddBodiesNanos());
    }

    @Test
    public void ClearBodiesNanos_StartsAtZero() {
        SpaceStats stats = new SpaceStats();

        assertEquals(0L, stats.getTotalClearBodiesNanos());
    }

    @Test
    public void AddClearBodiesNanos_IncreasesClearBodiesNanosByAmount() {
        SpaceStats stats = new SpaceStats();

        stats.addClearBodiesNanos(5);

        assertEquals(5L, stats.getTotalClearBodiesNanos());
    }

    @Test
    public void Reset_RunCountIsNonZero_SetsRunCountToZero() {
        SpaceStats stats = new SpaceStats();
        stats.runCompleted();

        assertEquals(1, stats.getRunCount());

        stats.reset();

        assertEquals(0, stats.getRunCount());
    }

    @Test
    public void Reset_TotalFindPairsNanosIsNonZero_SetsTotalFindPairsNanosToZero() {
        SpaceStats stats = new SpaceStats();
        stats.addFindPairsNanos(10);

        assertEquals(10L, stats.getTotalFindPairsNanos());

        stats.reset();

        assertEquals(0L, stats.getTotalFindPairsNanos());
    }

    @Test
    public void Reset_TotalProcessOverlapsNanosIsNonZero_SetsTotalProcessOverlapsNanosToZero() {
        SpaceStats stats = new SpaceStats();
        stats.addProcessOverlapsNanos(10);

        assertEquals(10L, stats.getTotalProcessOverlapsNanos());

        stats.reset();

        assertEquals(0L, stats.getTotalProcessOverlapsNanos());
    }

    @Test
    public void Reset_TotalPairsIsNonZero_SetsTotalPairsToZero() {
        SpaceStats stats = new SpaceStats();
        stats.pairFound();

        assertEquals(1, stats.getTotalPairs());

        stats.reset();

        assertEquals(0, stats.getTotalPairs());
    }

    @Test
    public void Reset_TotalOverlapsIsNonZero_SetsTotalOverlapsToZero() {
        SpaceStats stats = new SpaceStats();
        stats.overlapFound();

        assertEquals(1, stats.getTotalOverlaps());

        stats.reset();

        assertEquals(0, stats.getTotalOverlaps());
    }

    @Test
    public void Reset_TotalPairFilteringsIsNonZero_SetsTotalPairFilteringsToZero() {
        SpaceStats stats = new SpaceStats();
        stats.pairFiltered();

        assertEquals(1, stats.getTotalPairsFiltered());

        stats.reset();

        assertEquals(0, stats.getTotalPairsFiltered());
    }

    @Test
    public void Reset_TotalAddBodiesNanosIsNonZero_SetsTotalAddBodiesNanosToZero() {
        SpaceStats stats = new SpaceStats();
        stats.addAddBodiesNanos(5);

        assertEquals(5L, stats.getTotalAddBodiesNanos());

        stats.reset();

        assertEquals(0L, stats.getTotalAddBodiesNanos());
    }

    @Test
    public void Reset_TotalClearBodiesNanosIsNonZero_SetsTotalClearBodiesNanosToZero() {
        SpaceStats stats = new SpaceStats();
        stats.addClearBodiesNanos(5);

        assertEquals(5L, stats.getTotalClearBodiesNanos());

        stats.reset();

        assertEquals(0L, stats.getTotalClearBodiesNanos());
    }
}
