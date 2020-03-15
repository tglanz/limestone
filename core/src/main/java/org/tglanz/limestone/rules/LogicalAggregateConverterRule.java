package org.tglanz.limestone.rules;

<<<<<<< HEAD
import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalAggregate;
import org.tglanz.limestone.rels.LimeRel;
import org.tglanz.limestone.rels.AggregateLimeRel;
=======
import org.tglanz.limestone.rels.AggregateLimeRel;
import org.tglanz.limestone.rels.LimeRel;
import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalAggregate;
>>>>>>> more

public class LogicalAggregateConverterRule extends ConverterRule {

    public static final RelOptRule Instance = new LogicalAggregateConverterRule();

    private LogicalAggregateConverterRule() {
        super(LogicalAggregate.class, Convention.NONE, LimeRel.Convention, "LogicalAggregateConverterRule");
        assert getOutConvention() == LimeRel.Convention;
    }

    @Override
    public RelNode convert(RelNode rel) {
        final LogicalAggregate source = (LogicalAggregate)rel;

        final RelNode input = convert(
            source.getInput(),
            source.getInput().getTraitSet().replace(LimeRel.Convention));

        return new AggregateLimeRel(
                source.getCluster(),
                source.getTraitSet().replace(LimeRel.Convention),
                input,
                source.indicator,
                source.getGroupSet(),
                source.getGroupSets(),
                source.getAggCallList());
    }

    
}
