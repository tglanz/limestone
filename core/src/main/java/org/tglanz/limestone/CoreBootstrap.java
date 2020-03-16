package org.tglanz.limestone;

import org.apache.calcite.plan.RelOptRule;
import org.tglanz.limestone.rules.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoreBootstrap implements ModuleBootstrap {
    public Iterable<RelOptRule> getAllRules(){
        return Arrays.asList(
                EnumerableTableScanRule.Instance,
                LogicalFilterRule.Instance,
                LogicalProjectRule.Instance,
                LogicalJoinRule.Instance,
                LogicalAggregateRule.Instance,
                LogicalSortRule.Instance
        );
    }
}
