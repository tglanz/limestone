package org.tglanz.limestone.rules;

import org.tglanz.limestone.rels.LimeRel;
import org.tglanz.limestone.rels.FilterLimeRel;
import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalFilter;

public class LogicalFilterRule extends ConverterRule {

    public static final RelOptRule Instance = new LogicalFilterRule();

    private LogicalFilterRule() {
        super(LogicalFilter.class, Convention.NONE, LimeRel.Convention, "LogicalFilterConverterRule");
        assert getOutConvention() == LimeRel.Convention;
    }

    @Override
    public RelNode convert(RelNode rel) {
        final LogicalFilter source = (LogicalFilter)rel;
        
        final RelNode input = convert(source.getInput(),
                source.getInput().getTraitSet().replace(LimeRel.Convention));

        return new FilterLimeRel(
            source.getCluster(),
            source.getTraitSet().replace(LimeRel.Convention),
            input,
            source.getCondition());
    }
}
