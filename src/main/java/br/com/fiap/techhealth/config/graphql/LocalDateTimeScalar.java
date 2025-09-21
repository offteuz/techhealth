package br.com.fiap.techhealth.config.graphql;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.GraphQLScalarType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeScalar {

    private static String removeOffset(String value) {
        // Remove o offset, se existir (ex: "2024-09-20T10:00:00-03:00" -> "2024-09-20T10:00:00")
        return value.replaceFirst("(\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2})(\\.\\d+)?([+-]\\d{2}:\\d{2})$", "$1$2");
    }

    public static final GraphQLScalarType LOCAL_DATE_TIME = GraphQLScalarType.newScalar()
            .name("LocalDateTime")
            .coercing(new Coercing<LocalDateTime, String>() {
                @Override
                public String serialize(Object input) {
                    return ((LocalDateTime) input).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                }

                @Override
                public LocalDateTime parseValue(Object input) {
                    String value = removeOffset(input.toString());
                    return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                }

                @Override
                public LocalDateTime parseLiteral(Object input) {
                    if (input instanceof StringValue) {
                        String value = removeOffset(((StringValue) input).getValue());
                        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    }
                    return null;
                }
            })
            .build();
}
