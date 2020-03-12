package org.tglanz.limestone.rules;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalJoin;
import org.tglanz.limestone.rels.JoinLimeRel;
import org.tglanz.limestone.rels.LimeRel;

public class LogicalJoinConverterRule extends ConverterRule {

    public static final RelOptRule Instance = new LogicalJoinConverterRule();

    private LogicalJoinConverterRule() {
        super(LogicalJoin.class, Convention.NONE, LimeRel.Convention,  "LimeLogicalJoinConverterRule");
        assert getOutConvention() == LimeRel.Convention;
    }

    @Override
    public RelNode convert(RelNode rel) {
        final LogicalJoin source = (LogicalJoin)rel;
        final RelOptCluster cluster = source.getCluster();
        final RelTraitSet traitSet = source.getTraitSet().replace(LimeRel.Convention);

        return new JoinLimeRel(
                cluster,
                traitSet,
                source.getLeft(),
                source.getRight(),
                source.getCondition(),
                source.getVariablesSet(),
                source.getJoinType()
        );
    }
}
