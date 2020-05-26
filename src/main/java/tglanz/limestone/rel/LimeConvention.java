package tglanz.limestone.rel;

import org.apache.calcite.plan.Convention;
import org.apache.calcite.rel.RelNode;

public class LimeConvention extends Convention.Impl {
    public static final LimeConvention INSTANCE = new LimeConvention();

    public LimeConvention() {
        super("LimeConvention", LimeRel.class);
    }
}
