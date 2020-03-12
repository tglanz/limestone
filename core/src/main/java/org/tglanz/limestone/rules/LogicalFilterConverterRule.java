package org.tglanz.limestone.rules;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalFilter;
import org.tglanz.limestone.rels.FilterLimeRel;
import org.tglanz.limestone.rels.LimeRel;

public class LogicalFilterConverterRule extends ConverterRule {

    public static final RelOptRule Instance = new LogicalFilterConverterRule();

    private LogicalFilterConverterRule() {
        super(LogicalFilter.class, Convention.NONE, LimeRel.Convention, "LogicalFilterConverterRule");
        assert getOutConvention() == LimeRel.Convention;
    }

    @Override
    public RelNode convert(RelNode rel) {
        final LogicalFilter source = (LogicalFilter)rel;
        final RelTraitSet traitSet = source.getTraitSet().replace(LimeRel.Convention);
        return new FilterLimeRel(source.getCluster(), traitSet, source.getInput(), source.getCondition());
    }
}
