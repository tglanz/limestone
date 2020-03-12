package org.tglanz.limestone;

import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.schema.SchemaPlus;

import java.util.Map;

public class LimeTableFactory implements org.apache.calcite.schema.TableFactory<LimeTable> {
    public LimeTable create(SchemaPlus schema, String name, Map<String, Object> operand, RelDataType rowType) {

        if (!operand.containsKey("fields")) {
            throw new IllegalArgumentException(
                    String.format("missing `fields` property for table definition: %s", name));
        }

        Map<String, String> fields = (Map<String, String>)operand.get("fields");

        return new LimeTable(fields);
    }
}
