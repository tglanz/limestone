package org.tglanz.limestone;

import org.apache.calcite.plan.RelOptRule;

public interface ModuleBootstrap {
    Iterable<RelOptRule> getAllRules();
}
