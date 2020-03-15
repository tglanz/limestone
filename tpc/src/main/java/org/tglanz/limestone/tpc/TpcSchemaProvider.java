package org.tglanz.limestone.tpc;

import org.apache.calcite.adapter.tpcds.TpcdsSchema;
import org.apache.calcite.adapter.tpch.TpchSchema;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface TpcSchemaProvider {
    public static final TpcSchemaProvider Instance = new Default();

    public SchemaPlus get(Benchmark benchmark, float scale) throws Exception;

    class Default implements TpcSchemaProvider {

        private static final Logger logger = LogManager.getLogger(TpcSchemaProvider.class);

        @Override
        public SchemaPlus get(Benchmark benchmark, float scale) throws Exception {
            Schema schema;

            switch (benchmark) {
                case DS: schema = new TpcdsSchema(scale); break;
                case H: schema = new TpchSchema(scale, 0, 1, false); break;
                default:
                    throw new Exception(String.format(
                            "no schema found for benchmark: %s", benchmark));
            }

            SchemaPlus rootPlus = CalciteSchema.createRootSchema(false).plus();
            rootPlus.add(benchmark.name(), schema);
            SchemaPlus schemaPlus = rootPlus.getSubSchema(benchmark.name());

            logger.debug(String.format("retrieved schema: %s", schemaPlus.getName()));

            return schemaPlus;
        }
    }
}
