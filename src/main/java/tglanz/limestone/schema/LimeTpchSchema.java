package tglanz.limestone.schema;

import com.google.common.collect.ImmutableMap;
import org.apache.calcite.adapter.tpch.TpchSchema;
import org.apache.calcite.schema.QueryableTable;
import org.apache.calcite.schema.Table;

import java.util.Map;
import java.util.Objects;

public class LimeTpchSchema extends TpchSchema {

    Map<String, Table> tableMap;

    public LimeTpchSchema(double scaleFactor, int part, int partCount, boolean columnPrefix) {
        super(scaleFactor, part, partCount, columnPrefix);
        ImmutableMap.Builder<String, Table> builder = ImmutableMap.builder();
        for (Map.Entry<String, Table> entry: super.getTableMap().entrySet()) {
            Table originalTable = entry.getValue();
            Table transformedTable = transformTable(originalTable);
            builder.put(entry.getKey(), transformedTable);
        }
        this.tableMap = builder.build();
    }

    private static Table transformTable(Table table) {
        QueryableTable asQueryable = Objects.requireNonNull((QueryableTable)table);
        return new LimeTpchTable(asQueryable);
    }

    @Override
    protected Map<String, Table> getTableMap() {
        return tableMap;
    }
}
