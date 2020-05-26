package tglanz.limestone.rel;

import org.apache.calcite.adapter.enumerable.EnumerableTableScan;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.core.TableScan;
import org.apache.calcite.schema.Table;

public class LimeTpchTableScanRel extends EnumerableTableScan implements LimeRel {
    public LimeTpchTableScanRel(RelOptCluster cluster, RelTraitSet traitSet, RelOptTable table) {
        super(cluster, traitSet, table, deduceElementType(table.unwrap(Table.class)));
    }

    @Override
    public void register(RelOptPlanner planner) {
        super.register(planner);
        registerAllRules(planner);
    }


}
