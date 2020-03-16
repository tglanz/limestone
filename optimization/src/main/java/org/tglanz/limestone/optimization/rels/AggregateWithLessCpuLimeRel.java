package org.tglanz.limestone.optimization.rels;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptCost;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.AggregateCall;
import org.apache.calcite.rel.metadata.RelMetadataQuery;
import org.apache.calcite.util.ImmutableBitSet;
import org.tglanz.limestone.rels.AggregateLimeRel;

import java.util.List;

public class AggregateWithLessCpuLimeRel extends AggregateLimeRel {

    public AggregateWithLessCpuLimeRel(RelOptCluster cluster,
                                       RelTraitSet traitSet,
                                       RelNode input,
                                       ImmutableBitSet groupSet,
                                       List<ImmutableBitSet> groupSets,
                                       List<AggregateCall> aggCalls) {
        super(cluster, traitSet, input, groupSet, groupSets, aggCalls);
        assert getConvention() == Convention;
    }

    @Override
    public RelOptCost computeSelfCost(RelOptPlanner planner, RelMetadataQuery mq) {
        // this is an example, not actal logic
        RelOptCost cost = super.computeSelfCost(planner, mq);
        RelOptCost newCost = planner.getCostFactory().makeCost(
                cost.getRows(),
                cost.getCpu() / 2,
                cost.getIo());
        return newCost;
    }
}
