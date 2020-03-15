package org.tglanz.limestone.rules;

<<<<<<< HEAD
import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalFilter;
import org.tglanz.limestone.rels.FilterLimeRel;
import org.tglanz.limestone.rels.LimeRel;
=======
import org.tglanz.limestone.rels.LimeRel;
import org.tglanz.limestone.rels.FilterLimeRel;
import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalFilter;
>>>>>>> more

public class LogicalFilterConverterRule extends ConverterRule {

    public static final RelOptRule Instance = new LogicalFilterConverterRule();

    private LogicalFilterConverterRule() {
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
