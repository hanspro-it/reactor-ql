package org.jetlinks.reactor.ql.supports.filter;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import org.jetlinks.reactor.ql.ReactorQLMetadata;
import org.jetlinks.reactor.ql.feature.FeatureId;
import org.jetlinks.reactor.ql.feature.FilterFeature;
import org.jetlinks.reactor.ql.supports.ReactorQLContext;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class OrFilter implements FilterFeature {

    static String id = FeatureId.Filter.or.getId();

    @Override
    public BiFunction<ReactorQLContext, Object, Mono<Boolean>> createPredicate(Expression expression, ReactorQLMetadata metadata) {
        OrExpression and = ((OrExpression) expression);

        Expression left = and.getLeftExpression();
        Expression right = and.getRightExpression();

        BiFunction<ReactorQLContext, Object, Mono<Boolean>> leftPredicate = FilterFeature.createPredicateNow(left, metadata);
        BiFunction<ReactorQLContext, Object, Mono<Boolean>> rightPredicate = FilterFeature.createPredicateNow(right, metadata);

        return (ctx, val) -> Mono.zip(leftPredicate.apply(ctx, val), rightPredicate.apply(ctx, val), (v, v2) -> v || v2);
    }


    @Override
    public String getId() {
        return id;
    }
}
