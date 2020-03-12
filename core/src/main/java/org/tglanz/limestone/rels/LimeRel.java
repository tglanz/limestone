package org.tglanz.limestone.rels;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.rel.RelNode;

public interface LimeRel extends RelNode {
    public static final Convention Convention = new Convention.Impl("LimeConvention", LimeRel.class);
}
