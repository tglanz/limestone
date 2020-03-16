package org.tglanz.limestone.optimization;

import org.apache.calcite.plan.RelOptCost;
import org.apache.calcite.plan.RelOptCostFactory;
import org.apache.commons.lang.NotImplementedException;

public class LimeRelOptCostFactory implements RelOptCostFactory {

    @Override
    public RelOptCost makeCost(double rowCount, double cpu, double io) {
        throw new NotImplementedException();
    }

    @Override
    public RelOptCost makeHugeCost() {
        throw new NotImplementedException();
    }

    @Override
    public RelOptCost makeInfiniteCost() {
        throw new NotImplementedException();
    }

    @Override
    public RelOptCost makeTinyCost() {
        throw new NotImplementedException();
    }

    @Override
    public RelOptCost makeZeroCost() {
        throw new NotImplementedException();
    }
}
