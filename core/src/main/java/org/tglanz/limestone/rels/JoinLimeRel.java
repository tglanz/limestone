package org.tglanz.limestone.rels;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.CorrelationId;
import org.apache.calcite.rel.core.Join;
import org.apache.calcite.rel.core.JoinRelType;
import org.apache.calcite.rex.RexNode;

import java.util.Set;

public class JoinLimeRel
        extends Join
        implements LimeRel {

    public JoinLimeRel(RelOptCluster cluster,
                       RelTraitSet traitSet,
                       RelNode left,
                       RelNode right,
                       RexNode condition,
                       Set<CorrelationId> variablesSet,
                       JoinRelType joinType) {
        super(cluster, traitSet, left, right, condition, variablesSet, joinType);
    }

    @Override
    public Join copy(RelTraitSet traitSet, RexNode conditionExpr,
                     RelNode left, RelNode right, JoinRelType joinType, boolean semiJoinDone) {
        return new JoinLimeRel(getCluster(), traitSet, left, right, conditionExpr, getVariablesSet(), joinType);
    }
}
