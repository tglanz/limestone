package tglanz.limestone.rel;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.rel.RelNode;
import tglanz.limestone.rules.LimeTpchToEnumerableTableScanRule;

public interface LimeRel extends RelNode {
    default void registerAllRules(RelOptPlanner planner) {
        planner.addRule(LimeTpchToEnumerableTableScanRule.INSTANCE);
    }
}
