package org.tglanz.limestone.optimization.rules;

import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.tglanz.limestone.optimization.rels.AggregateWithLessCpuLimeRel;
import org.tglanz.limestone.optimization.rels.AggregateWithMoreCpuLimeRel;
import org.tglanz.limestone.rels.AggregateLimeRel;
import org.tglanz.limestone.rels.LimeRel;

public class OptimizeAggregateToHaveMoreCpuRule extends ConverterRule {

    public static final RelOptRule Instance = new OptimizeAggregateToHaveMoreCpuRule();

    private OptimizeAggregateToHaveMoreCpuRule() {
        super(AggregateLimeRel.class, LimeRel.Convention, LimeRel.Convention, "OptimizeAggregateToHaveMoreCpuRule");
        assert getOutConvention() == LimeRel.Convention;
    }

    @Override
    public RelNode convert(RelNode rel) {
        final AggregateLimeRel source = (AggregateLimeRel)rel;

        final RelNode input = convert(
            source.getInput(),
            source.getInput().getTraitSet().replace(LimeRel.Convention));

        return new AggregateWithMoreCpuLimeRel(
                source.getCluster(),
                source.getTraitSet().replace(LimeRel.Convention),
                input,
                source.getGroupSet(),
                source.getGroupSets(),
                source.getAggCallList());
    }

    
}
