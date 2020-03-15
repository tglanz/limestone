package org.tglanz.limestone.rels;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelCollation;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Sort;
import org.apache.calcite.rex.RexNode;

public class SortLimeRel
        extends Sort
        implements LimeRel {

    public SortLimeRel(RelOptCluster cluster,
                       RelTraitSet traits,
                       RelNode child,
                       RelCollation collation,
                       RexNode offset,
                       RexNode fetch) {
        super(cluster, traits, child, collation, offset, fetch);
    }

    @Override
    public Sort copy(RelTraitSet traitSet,
                     RelNode newInput,
                     RelCollation newCollation,
                     RexNode offset,
                     RexNode fetch) {
        return new SortLimeRel(
                getCluster(),
                traitSet,
                newInput,
                newCollation,
                offset,
                fetch
        );
    }
}
