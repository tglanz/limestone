package org.tglanz.limestone.optimization;

import org.apache.calcite.plan.RelOptRule;
import org.tglanz.limestone.ModuleBootstrap;
import org.tglanz.limestone.optimization.rules.OptimizeAggregateToHaveLessCpuRule;

import java.util.Arrays;

public class OptimizationBootstrap implements ModuleBootstrap {
    public Iterable<RelOptRule> getAllRules() {
        return Arrays.asList(
                OptimizeAggregateToHaveLessCpuRule.Instance
        );
    };
}
