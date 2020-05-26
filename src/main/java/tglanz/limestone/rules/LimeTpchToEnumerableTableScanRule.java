package tglanz.limestone.rules;

import org.apache.calcite.adapter.enumerable.EnumerableConvention;
import org.apache.calcite.adapter.enumerable.EnumerableTableScan;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptRuleCall;
import org.apache.calcite.plan.RelOptRuleOperand;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.schema.Table;
import tglanz.limestone.rel.LimeCopyEnumerableToNodeRel;
import tglanz.limestone.rel.LimeTpchTableScanRel;

public class LimeTpchToEnumerableTableScanRule extends RelOptRule {

    public static final LimeTpchToEnumerableTableScanRule INSTANCE = new LimeTpchToEnumerableTableScanRule();

    public LimeTpchToEnumerableTableScanRule() {
        super(
            operand(LimeTpchTableScanRel.class, none())
        );
    }

    @Override
    public void onMatch(RelOptRuleCall relOptRuleCall) {

        LimeTpchTableScanRel lime = relOptRuleCall.rel(0);

        EnumerableTableScan enumerableRel = new EnumerableTableScan(
                lime.getCluster(),
                lime.getTraitSet().replace(EnumerableConvention.INSTANCE),
                lime.getTable(),
                EnumerableTableScan.deduceElementType(lime.getTable().unwrap(Table.class))
        );

        LimeCopyEnumerableToNodeRel copyRel = new LimeCopyEnumerableToNodeRel(
                enumerableRel,
                enumerableRel.getCluster(),
                enumerableRel.getTraitSet()
        );

        relOptRuleCall.transformTo(copyRel);
    }
}
