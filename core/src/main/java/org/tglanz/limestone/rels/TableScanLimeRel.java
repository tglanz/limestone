package org.tglanz.limestone.rels;

<<<<<<< HEAD
=======
import org.tglanz.limestone.rules.RulesSets;
>>>>>>> more
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.core.TableScan;
<<<<<<< HEAD
import org.tglanz.limestone.rules.RulesSets;
=======
>>>>>>> more

import java.util.Objects;

public class TableScanLimeRel
        extends TableScan
        implements LimeRel {

    public TableScanLimeRel(RelOptCluster cluster, RelTraitSet traitSet, RelOptTable table) {
        super(
                Objects.requireNonNull(cluster, "cluster must not be null"),
                Objects.requireNonNull(traitSet, "traitSet must not be null"),
                Objects.requireNonNull(table, "table must not be null"));

<<<<<<< HEAD
        assert getConvention() == LimeRel.Convention;
=======
        assert getConvention() == Convention;
>>>>>>> more
    }

    @Override
    public void register(RelOptPlanner planner) {
        RulesSets.standard.forEach(planner::addRule);
        super.register(planner);
    }
}
