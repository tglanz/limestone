package org.tglanz.limestone.rels;

import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.core.Project;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexNode;

import java.util.List;

public class ProjectLimeRel
        extends Project
        implements LimeRel {

    public ProjectLimeRel(RelOptCluster cluster,
                          RelTraitSet traitSet,
                          RelNode input,
                          List<? extends RexNode> projects,
                          RelDataType rowType) {
        super(cluster, traitSet, input, projects, rowType);
        assert getConvention() == Convention;
    }

    @Override
    @SuppressWarnings("deprecation")
    public Project copy(RelTraitSet traitSet, RelNode input, List<RexNode> projects, RelDataType rowType, int flags) {
        return this.copy(traitSet, input, projects, rowType);
    }

    @Override
    public Project copy(RelTraitSet traitSet, RelNode input, List<RexNode> projects, RelDataType rowType) {
        return new ProjectLimeRel(input.getCluster(), traitSet, input, projects, rowType);
    }
}
