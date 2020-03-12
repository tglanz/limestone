package org.tglanz.limestone;

import com.google.common.collect.ImmutableMap;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptTable;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.TranslatableTable;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.type.SqlTypeName;
import org.tglanz.limestone.rels.LimeRel;
import org.tglanz.limestone.rels.TableScanLimeRel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LimeTable
        extends AbstractTable
        implements TranslatableTable {

    private final Map<String, String> fields;

    public LimeTable(Map<String, String> fields) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        this.fields = builder.putAll(fields).build();
    }

    @Override
    public RelDataType getRowType(RelDataTypeFactory typeFactory) {

        try {
            List<String> fieldNames = new ArrayList<String>(this.fields.size());
            List<RelDataType> fieldTypes = new ArrayList<RelDataType>(this.fields.size());

            for (Map.Entry<String, String> entry : this.fields.entrySet()) {
                String fieldName = entry.getKey();
                String fieldTypeName = entry.getValue();

                SqlTypeName sqlTypeName;
                try {
                    sqlTypeName = Objects.requireNonNull(SqlTypeName.get(fieldTypeName.toUpperCase()));
                } catch (Exception exception) {
                    throw new RuntimeException(
                            String.format("failed to convert sql type name for: %s", fieldTypeName));
                }

                RelDataType relDataType = typeFactory.createSqlType(sqlTypeName);

                fieldNames.add(fieldName);
                fieldTypes.add(relDataType);
            }

            return typeFactory.createStructType(fieldTypes, fieldNames);
        } catch (Exception exception) {
            throw new RuntimeException(String.format("failed to get row type for: %s", this), exception);
        }
    }

    @Override
    public RelNode toRel(RelOptTable.ToRelContext context, RelOptTable relOptTable) {
        final RelOptCluster cluster = context.getCluster();
        final RelTraitSet traitSet = cluster.traitSetOf(LimeRel.Convention);
        return new TableScanLimeRel(cluster, traitSet, relOptTable);
    }
}