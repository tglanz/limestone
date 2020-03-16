package org.tglanz.limestone.optimization;

import org.apache.calcite.plan.RelOptCost;
import org.apache.calcite.plan.RelOptCostFactory;
import org.apache.commons.lang.NotImplementedException;

public class LimeRelOptCostFactory implements RelOptCostFactory {

    /** cost with zero values **/
    public static final LimeRelOptCost ZeroCost = new LimeRelOptCost(0, 0, 0);

    /** cost with infinite values **/
    public static final RelOptCost InfiniteCost = ZeroCost.apply(a -> Double.POSITIVE_INFINITY);
    /** cost with tiny values **/
    public static final RelOptCost TinyCost = ZeroCost.apply(a -> 1E-5);

    /** cost with huge values **/
    public static final RelOptCost HugeCost = ZeroCost.apply(a -> 1E-20);

    @Override
    public RelOptCost makeCost(double rowCount, double cpu, double io) {
        return new LimeRelOptCost(rowCount, cpu, io);
    }

    @Override
    public RelOptCost makeHugeCost() {
        return HugeCost;
    }

    @Override
    public RelOptCost makeInfiniteCost() {
        return InfiniteCost;
    }

    @Override
    public RelOptCost makeTinyCost() {
        return TinyCost;
    }

    @Override
    public RelOptCost makeZeroCost() {
        return ZeroCost;
    }
}
