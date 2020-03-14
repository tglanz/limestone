package org.tglanz.limestone.rules;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.logical.LogicalProject;
import org.tglanz.limestone.rels.LimeRel;
import org.tglanz.limestone.rels.ProjectLimeRel;

public class LogicalProjectConverterRule extends ConverterRule {

    public static final RelOptRule Instance = new LogicalProjectConverterRule();

    private LogicalProjectConverterRule() {
        super(LogicalProject.class, Convention.NONE, LimeRel.Convention, "LogicalProjectConverterRule");
        assert getOutConvention() == LimeRel.Convention;
    }

    @Override
    public RelNode convert(RelNode rel) {
        final LogicalProject source = (LogicalProject)rel;
        final RelTraitSet traitSet = source.getTraitSet().replace(LimeRel.Convention);

        final RelNode input = convert(
            source.getInput(),
            source.getInput().getTraitSet().replace(LimeRel.Convention));

        return new ProjectLimeRel(
                source.getCluster(),
                traitSet,
                input,
                source.getProjects(),
                source.getRowType());
    }
}
