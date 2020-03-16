package org.tglanz.limestone.optimization;

import org.apache.calcite.plan.RelOptCost;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.commons.lang.NotImplementedException;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * basic, mostly as a poc implementation
 */
public class LimeRelOptCost implements RelOptCost {

    /** used to calculate the unified cost **/
    public static final double WeightOfRows = 1;

    /** used to calculate the unified cost **/
    public static final double WeightOfCpu = 1;

    /** used to calculate the unified cost **/
    public static final double WeightOfIo = 2;

    /** number of rows processed **/
    private final double rows;

    /** usage of cpu **/
    private final double cpu;

    /** usage of io **/
    private final double io;

    /**
     * creates a new instance based on given values
     * <br>
     * uses DefaultWeigths for the weights
     *
     * @param rows rows field
     * @param cpu cpu field
     * @param io io fields
     */
    public LimeRelOptCost(double rows, double cpu, double io) {
        this.rows = rows;
        this.cpu = cpu;
        this.io = io;
    }

    @Override
    public double getRows() {
        return rows;
    }

    @Override
    public double getCpu() {
        return cpu;
    }

    @Override
    public double getIo() {
        return io;
    }

    @Override
    public boolean isInfinite() {
        return Double.isInfinite(getRows()) ||
                Double.isInfinite(getCpu()) ||
                Double.isInfinite(getIo());
    }

    @Override
    public boolean equals(RelOptCost cost) {
        // this != null
        if (cost == null) {
            return false;
        }

        // infinite != anthying
        if (isInfinite() || cost.isInfinite()) {
            return false;
        }

        // check all relevant values
        return getRows() == cost.getRows() && getCpu() == cost.getCpu() && getIo() == cost.getIo();
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s, %s)", getClass().getSimpleName(), getRows(), getCpu(), getIo());
    }

    @Override
    protected LimeRelOptCost clone() {
        return new LimeRelOptCost(getRows(), getCpu(), getIo());
    }

    @Override
    public boolean isEqWithEpsilon(RelOptCost cost) {
        final RelOptCost diff = mergeWith(cost, (a, b) -> a - b);
        final double sum = diff.getRows() + diff.getCpu() + diff.getIo();
        return sum < 1e5;
    }

    @Override
    public boolean isLe(RelOptCost cost) {
        return equals(cost) || isLt(cost);
    }

    @Override
    public boolean isLt(RelOptCost cost) {
        return unifiedCost(this) < unifiedCost(cost);
    }

    @Override
    public RelOptCost plus(RelOptCost cost) {
        return mergeWith(cost, (a, b) -> a + b);
    }

    @Override
    public RelOptCost minus(RelOptCost cost) {
        return mergeWith(cost, (a, b) -> a - b);
    }

    @Override
    public RelOptCost multiplyBy(double factor) {
        return apply(a -> a * factor);
    }

    @Override
    public double divideBy(RelOptCost cost) {
        return unifiedCost(this) / unifiedCost(cost);
    }

    /**
     * merge this cost with another based on a given merge function
     * <br><br>
     *
     * example;
     * <pre>
     * {@link LimeRelOptCost} lhs = new {@link LimeRelOptCost}(1, 0, 1);
     * {@link LimeRelOptCost} rhs = new {@link LimeRelOptCost}(0, 1, 0);
     *
     * {@link LimeRelOptCost} result = lhs.mergeWith(rhs, (a, b) -> a + b);
     *
     * assert result.equals(new {@link LimeRelOptCost}(1, 1, 1));
     * </pre>
     *
     * @param cost cost to merge with
     * @param merger merge function to operate on each corresponding field
     * @return merged cost
     */
    public LimeRelOptCost mergeWith(RelOptCost cost, BiFunction<Double, Double, Double> merger) {
        return new LimeRelOptCost(
                    merger.apply(getRows(), cost.getRows()),
                    merger.apply(getCpu(), cost.getCpu()),
                    merger.apply(getIo(), cost.getIo()));
    }

    /**
     * applies a function on each of this instance's fields
     * <br><br>
     *
     * example;
     * <pre>
     * {@link LimeRelOptCost} cost = new {@link LimeRelOptCost}(0, 1, 2);
     *
     * {@link LimeRelOptCost} result = cost.apply(a -> a * 2);
     *
     * assert result.equals(new {@link LimeRelOptCost}(0, 2, 4));
     * </pre>
     *
     * @param cost cost to merge with
     * @param merger merge function to operate on each corresponding field
     * @return merged cost
     */
    public LimeRelOptCost apply(Function<Double, Double> application) {
        return new LimeRelOptCost(
                application.apply(getRows()),
                application.apply(getCpu()),
                application.apply(getIo()));
    }

    /**
     * calculates a single cost value based on all fields of the instance
     *
     * @param cost to calculate the unified cost value of
     * @return unified cost value
     */
    private double unifiedCost(RelOptCost cost) {
        // not the actual implementation, for now
        final double weightedSum =
                cost.getRows() * WeightOfRows +
                cost.getCpu() * WeightOfCpu +
                cost.getIo() * WeightOfIo;
        return weightedSum / 3;
    }
}
