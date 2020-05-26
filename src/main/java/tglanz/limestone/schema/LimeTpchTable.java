package tglanz.limestone.schema;

import org.apache.calcite.adapter.java.AbstractQueryableTable;
import org.apache.calcite.linq4j.QueryProvider;
import org.apache.calcite.linq4j.Queryable;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.QueryableTable;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.TranslatableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import tglanz.limestone.rel.LimeConvention;
import tglanz.limestone.rel.LimeTpchTableScanRel;

public class LimeTpchTable
    extends AbstractQueryableTable
    implements TranslatableTable {

    private final QueryableTable sourceTable;

    public LimeTpchTable(QueryableTable sourceTable) {
        super(sourceTable.getElementType());
        this.sourceTable = sourceTable;
    }

    @Override
    public <T> Queryable<T> asQueryable(QueryProvider queryProvider, SchemaPlus schemaPlus, String s) {
        return sourceTable.asQueryable(queryProvider, schemaPlus, s);
    }

    @Override
    public RelNode toRel(RelOptTable.ToRelContext toRelContext, RelOptTable relOptTable) {
        return new LimeTpchTableScanRel(
                toRelContext.getCluster(),
                toRelContext.getCluster().traitSetOf(LimeConvention.INSTANCE),
                relOptTable);
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory relDataTypeFactory) {
        return sourceTable.getRowType(relDataTypeFactory);
    }
}
