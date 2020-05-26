package tglanz.limestone.rel;

import org.apache.calcite.adapter.enumerable.EnumerableConvention;
import org.apache.calcite.adapter.enumerable.EnumerableRel;
import org.apache.calcite.adapter.enumerable.EnumerableRelImplementor;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.tree.BlockStatement;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.AbstractRelNode;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataType;

import java.util.Arrays;
import java.util.List;

public class LimeCopyEnumerableToNodeRel
            extends AbstractRelNode
            implements EnumerableRel {

    private RelNode input;

    public LimeCopyEnumerableToNodeRel(RelNode input, RelOptCluster cluster, RelTraitSet traitSet) {
        super(cluster, traitSet);
        this.input = input;
    }

    @Override
    public Result implement(EnumerableRelImplementor enumerableRelImplementor, Prefer prefer) {
        EnumerableRel asEnumerable = (EnumerableRel)input;
        return asEnumerable.implement(enumerableRelImplementor, prefer);
    }

    @Override
    public List<RelNode> getInputs() {
        return Arrays.asList(input);
    }

    @Override
    public RelNode copy(RelTraitSet traitSet, List<RelNode> inputs) {
        return new LimeCopyEnumerableToNodeRel(inputs.get(0), this.getCluster(), traitSet);
    }

    @Override
    protected RelDataType deriveRowType() {
        return input.getRowType();
    }

    @Override
    public void replaceInput(int ordinalInParent, RelNode p) {
        assert ordinalInParent == 0;
        input = p;
    }
}
