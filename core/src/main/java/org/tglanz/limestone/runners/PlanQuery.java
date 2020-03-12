package org.tglanz.limestone.runners;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.calcite.config.Lex;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.externalize.RelWriterImpl;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.tglanz.limestone.rels.LimeRel;
import org.tglanz.limestone.rules.RulesSets;
import org.tglanz.limestone.utils.FileReader;

public class PlanQuery {

    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            // arguments stuff
            if (args.length != 2) {
                throw new IllegalArgumentException(String.format(
                        "expected only 2, ordered arguments, {modelPath} and {query}, got: %s",
                        String.join(", ", args)));
            }

            final String modelPath = args[0];
            final String queryPath = args[1];

            final String query = FileReader.readContent(queryPath);

            // create calcite's environment
            final SchemaPlus schema = createSchema(modelPath);
            final FrameworkConfig frameworkConfig = createFrameworkConfig(schema);
            final Planner planner = Frameworks.getPlanner(frameworkConfig);

            RelNode relNode = planQuery(planner, query);
            relNode = transformRelationalExpression(planner, relNode);
        } catch (Exception ex) {
            logger.fatal(ex);
        }
    }

    /**
     * get the schema according to model's file path
     * @param modelPath model's file path
     * @return schema
     */
    private static SchemaPlus createSchema(String modelPath)
            throws SQLException, ClassNotFoundException {
        logger.debug(String.format("creating schema from model at: %s", modelPath));

        // Q: what is a better way to do this?
        Class.forName("org.apache.calcite.jdbc.Driver");

        Properties properties = new Properties();
        properties.setProperty("model", modelPath);

        try (Connection connection = DriverManager.getConnection("jdbc:calcite:", properties)) {
            CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
            final String schemaName = calciteConnection.getSchema();
            return calciteConnection.getRootSchema().getSubSchema(schemaName);
        }
    }

    private static FrameworkConfig createFrameworkConfig(SchemaPlus schema) {
        final SqlParser.Config parserConfig = SqlParser.configBuilder()
                .setCaseSensitive(false)
                .setLex(Lex.MYSQL)
                .build();

        return Frameworks.newConfigBuilder()
                .parserConfig(parserConfig)
                .ruleSets(RulesSets.standard)
                .defaultSchema(schema)
                .build();
    }

    private static RelNode planQuery(Planner planner, String query)
            throws SqlParseException, ValidationException, RelConversionException {

        logger.info(String.format("planning query\n%s\n", query));

        logger.debug("parsing");
        final SqlNode sqlNode = planner.parse(query);
        planner.validate(sqlNode);

        logger.debug("creating relational expression");
        final RelNode relNode = planner.rel(sqlNode).project();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("initial relational expression");
        relNode.explain(new RelWriterImpl(new PrintWriter(stringWriter)));
        logger.info(stringWriter.toString());

        return relNode;
    }

    private static RelNode transformRelationalExpression(Planner planner, RelNode relNode)
            throws RelConversionException {
        logger.info(String.format("transforming relational expression"));

        RelTraitSet requiredTraitSet = relNode.getCluster().traitSetOf(LimeRel.Convention);
        RelNode transformedRelNode = planner.transform(0, requiredTraitSet, relNode);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("transformed relational expression");
        transformedRelNode.explain(new RelWriterImpl(new PrintWriter(stringWriter)));
        logger.info(stringWriter.toString());

        return transformedRelNode;
    }
}