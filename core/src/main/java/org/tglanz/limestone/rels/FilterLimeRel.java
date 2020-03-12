package org.tglanz.limestone.rels;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Filter;
import org.apache.calcite.rex.RexNode;

public class FilterLimeRel
        extends Filter
        implements LimeRel {

    public FilterLimeRel(RelOptCluster cluster,
                         RelTraitSet traitSet,
                         RelNode input,
                         RexNode condition) {
        super(cluster, traitSet, input, condition);
    }

    @Override
    public Filter copy(RelTraitSet traitSet, RelNode input, RexNode condition) {
        return new FilterLimeRel(input.getCluster(), traitSet, input, condition);
    }
}
