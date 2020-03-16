package org.tglanz.limestone.rules;

import org.tglanz.limestone.rels.LimeRel;
import org.tglanz.limestone.rels.TableScanLimeRel;
import org.apache.calcite.adapter.enumerable.EnumerableConvention;
import org.apache.calcite.adapter.enumerable.EnumerableTableScan;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;

public class EnumerableTableScanRule extends ConverterRule {

    public static final RelOptRule Instance = new EnumerableTableScanRule();

    private EnumerableTableScanRule() {
        super(EnumerableTableScan.class, EnumerableConvention.INSTANCE, LimeRel.Convention, "EnumerableTableScanConverterRule");
        assert getOutConvention() == LimeRel.Convention;
    }

    @Override
    public RelNode convert(RelNode rel) {
        final EnumerableTableScan source = (EnumerableTableScan)rel;

        return new TableScanLimeRel(
                source.getCluster(),
                source.getTraitSet().replace(LimeRel.Convention),
                source.getTable()
        );
    }
}
