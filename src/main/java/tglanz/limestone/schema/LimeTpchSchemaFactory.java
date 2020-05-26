package tglanz.limestone.schema;

import org.apache.calcite.adapter.tpch.TpchSchema;
import org.apache.calcite.adapter.tpch.TpchSchemaFactory;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.util.Util;

import java.util.Map;

public class LimeTpchSchemaFactory extends TpchSchemaFactory {
    @Override
    public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
        double scale = Util.first((Double)operand.get("scale"), 1.0D);
        int part = Util.first((Integer)operand.get("part"), 1);
        int partCount = Util.first((Integer)operand.get("partCount"), 1);
        boolean columnPrefix = Util.first((Boolean)operand.get("columnPrefix"), true);
        return new LimeTpchSchema(scale, part, partCount, columnPrefix);
    }
}
