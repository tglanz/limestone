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
        super(LogicalJoin.class, Convention.NONE, LimeRel.Convention,  "LogicalJoinConverterRule");
        assert getOutConvention() == LimeRel.Convention;
    }

    @Override
    public RelNode convert(RelNode rel) {
        final LogicalJoin source = (LogicalJoin)rel;

        final RelNode left = convert(source.getLeft(),
            source.getLeft().getTraitSet().replace(LimeRel.Convention));
        
        final RelNode right = convert(source.getRight(),
            source.getLeft().getTraitSet().replace(LimeRel.Convention));

        return new JoinLimeRel(
                source.getCluster(),
                source.getTraitSet().replace(LimeRel.Convention),
                left,
                right,
                source.getCondition(),
                source.getVariablesSet(),
                source.getJoinType()
        );
    }
}
