package org.tglanz.limestone.rels;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Aggregate;
<<<<<<< HEAD
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexNode;
=======
>>>>>>> more
import org.apache.calcite.util.ImmutableBitSet;
import org.apache.calcite.rel.core.AggregateCall;

import java.util.List;

public class AggregateLimeRel
        extends Aggregate
        implements LimeRel {

    public AggregateLimeRel(RelOptCluster cluster,
                            RelTraitSet traitSet,
                            RelNode input,
                            boolean indicator,
                            ImmutableBitSet groupSet,
                            List<ImmutableBitSet> groupSets,
                            List<AggregateCall> aggCalls) {
        super(cluster, traitSet, input, groupSet, groupSets, aggCalls);
<<<<<<< HEAD
        assert getConvention() == LimeRel.Convention;
=======
        assert getConvention() == Convention;
>>>>>>> more
    }

    @Override
    public Aggregate copy(RelTraitSet traitSet, 
                          RelNode input, 
                          ImmutableBitSet groupSet, 
                          List<ImmutableBitSet> groupSets, 
                          List<AggregateCall> aggCalls) {
        return new AggregateLimeRel(input.getCluster(), traitSet, input, indicator, groupSet, groupSets, aggCalls);
    }
}
