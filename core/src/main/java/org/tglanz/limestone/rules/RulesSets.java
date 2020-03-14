package org.tglanz.limestone.rules;

import org.apache.calcite.tools.RuleSet;
import org.apache.calcite.tools.RuleSets;
import org.tglanz.limestone.rules.LogicalFilterConverterRule;
import org.tglanz.limestone.rules.LogicalJoinConverterRule;
import org.tglanz.limestone.rules.LogicalProjectConverterRule;
import org.tglanz.limestone.rules.LogicalAggregateConverterRule;

public class RulesSets {
    public static final RuleSet standard = RuleSets.ofList(
            LogicalFilterConverterRule.Instance,
            LogicalProjectConverterRule.Instance,
            LogicalJoinConverterRule.Instance,
            LogicalAggregateConverterRule.Instance
    );
}
