package br.com.fiap.techhealth.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof AccessDeniedGraphQLException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(ex.getMessage()) // "Você não tem permissão para acessar esta consulta."
                    .errorType(graphql.ErrorType.DataFetchingException)
                    .build();
        }

        if (ex instanceof ConsultationNotFoundException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(ex.getMessage()) // "Consulta não encontrada."
                    .errorType(graphql.ErrorType.DataFetchingException)
                    .build();
        }

        return null; // outras exceções seguem o fluxo padrão
    }
}
