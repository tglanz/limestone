package org.tglanz.limestone.optimization;

import org.apache.calcite.plan.RelOptRule;
import org.tglanz.limestone.ModuleBootstrap;
import org.tglanz.limestone.optimization.rules.OptimizeAggregateToHaveLessCpuRule;
import org.tglanz.limestone.optimization.rules.OptimizeAggregateToHaveMoreCpuRule;

import java.util.Arrays;

public class OptimizationBootstrap implements ModuleBootstrap {
    public Iterable<RelOptRule> getAllRules() {
        return Arrays.asList(
                // those two are an example! not what we really intend algorithmicly, but they show how the planner
                // choose a correct implementation based on cost
                OptimizeAggregateToHaveLessCpuRule.Instance,
                OptimizeAggregateToHaveMoreCpuRule.Instance
        );
    };
}
