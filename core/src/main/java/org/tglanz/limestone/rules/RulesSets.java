package org.tglanz.limestone.rules;

import org.apache.calcite.tools.RuleSet;
import org.apache.calcite.tools.RuleSets;

public class RulesSets {
    public static final RuleSet standard = RuleSets.ofList(
            EnumerableTableScanConverterRule.Instance,
            LogicalFilterConverterRule.Instance,
            LogicalProjectConverterRule.Instance,
            LogicalJoinConverterRule.Instance,
            LogicalAggregateConverterRule.Instance,
            LogicalSortConverterRule.Instance
    );
}
