package org.tglanz.limestone.optimization.rels;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptCost;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Aggregate;
import org.apache.calcite.rel.core.AggregateCall;
import org.apache.calcite.rel.metadata.RelMetadataQuery;
import org.apache.calcite.util.ImmutableBitSet;
import org.tglanz.limestone.rels.LimeRel;

import java.util.List;

public class AggregateWithMoreCpuLimeRel
        extends Aggregate
        implements LimeRel {

    public AggregateWithMoreCpuLimeRel(RelOptCluster cluster,
                                       RelTraitSet traitSet,
                                       RelNode input,
                                       ImmutableBitSet groupSet,
                                       List<ImmutableBitSet> groupSets,
                                       List<AggregateCall> aggCalls) {
        super(cluster, traitSet, input, groupSet, groupSets, aggCalls);
        assert getConvention() == Convention;
    }

    @Override
    public Aggregate copy(RelTraitSet traitSet,
                          RelNode input,
                          ImmutableBitSet groupSet,
                          List<ImmutableBitSet> groupSets,
                          List<AggregateCall> aggCalls) {
        return new AggregateWithMoreCpuLimeRel(input.getCluster(), traitSet, input, groupSet, groupSets, aggCalls);
    }

    @Override
    public RelOptCost computeSelfCost(RelOptPlanner planner, RelMetadataQuery mq) {
        // RelOptCost cost = super.computeSelfCost(planner, mq);
        double rows = mq.getRowCount(this);
        double cpu = 8 * rows; // lets say 1, for example add which will take 1 cycles
        double io = 2 * rows; // lets say 2, for example read, write
        return planner.getCostFactory().makeCost(rows, cpu, io);
    }
}
