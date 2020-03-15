package org.tglanz.limestone.tpc;

import org.tglanz.limestone.rels.LimeRel;
import org.tglanz.limestone.rules.RulesSets;
import org.tglanz.limestone.utils.FileReader;
import org.apache.calcite.config.Lex;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.externalize.RelWriterImpl;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TpcRunner {

    private static final Logger logger = LogManager.getLogger(TpcRunner.class);

    public static void main(String[] args) throws Exception {

        logger.debug(String.format("process args: %s",
                Stream.of(args).collect(Collectors.joining(", "))));

        ApplicationArguments applicationArguments = ApplicationArguments.parse(args);

        if (applicationArguments == null) {
            return;
        }

        logger.debug(String.join("\n", new String[] {
            "application arguments:",
            String.format("  - benchmark: %s", applicationArguments.benchmark),
            String.format("  - scale: %s", applicationArguments.scale),
            String.format("  - query path: %s", applicationArguments.queryPath)
        }));

        final String query = readQuery(applicationArguments.queryPath);
        final SchemaPlus schema = TpcSchemaProvider.Instance.get(
                applicationArguments.benchmark, applicationArguments.scale);
        final FrameworkConfig frameworkConfig = createFrameworkConfig(schema);
        final Planner planner = Frameworks.getPlanner(frameworkConfig);

        RelNode node = planQuery(planner, query);
        node = transformRelationalExpression(planner, node);
    }

    private static String readQuery(String filePath) throws IOException {
        logger.debug(String.format("reading query from path: %s", filePath));
        String query = FileReader.readContent(filePath);

        if (query.endsWith(";")) {
            query = query.substring(0, query.length() - 1);
        }

        logger.debug(String.format("query:\n%s", query));
        return query;
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
