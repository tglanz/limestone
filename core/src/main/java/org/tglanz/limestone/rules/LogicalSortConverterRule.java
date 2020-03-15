package org.tglanz.limestone.rules;

import org.tglanz.limestone.rels.LimeRel;
import org.tglanz.limestone.rels.SortLimeRel;
import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalSort;

public class LogicalSortConverterRule extends ConverterRule {

    public static final RelOptRule Instance = new LogicalSortConverterRule();

    private LogicalSortConverterRule() {
        super(LogicalSort.class, Convention.NONE, LimeRel.Convention, "LogicalSortConverterRule");
        assert getOutConvention() == LimeRel.Convention;
    }

    @Override
    public RelNode convert(RelNode rel) {
        final LogicalSort source = (LogicalSort)rel;
        
        final RelNode input = convert(source.getInput(),
                source.getInput().getTraitSet().replace(LimeRel.Convention));

        return new SortLimeRel(
                source.getCluster(),
                source.getTraitSet().replace(LimeRel.Convention),
                input,
                source.getCollation(),
                source.offset,
                source.fetch
        );
    }
}
