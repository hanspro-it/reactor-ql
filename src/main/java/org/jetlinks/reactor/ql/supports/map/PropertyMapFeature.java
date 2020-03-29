package org.jetlinks.reactor.ql.supports.map;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import org.apache.commons.beanutils.PropertyUtils;
import org.jetlinks.reactor.ql.ReactorQLMetadata;
import org.jetlinks.reactor.ql.feature.FeatureId;
import org.jetlinks.reactor.ql.feature.ValueMapFeature;
import org.jetlinks.reactor.ql.supports.ReactorQLContext;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Function;

public class PropertyMapFeature implements ValueMapFeature {

    static String ID = FeatureId.ValueMap.property.getId();

    @Override
    public Function<ReactorQLContext, ? extends Publisher<?>> createMapper(Expression expression, ReactorQLMetadata metadata) {
        Column column = ((Column) expression);
        String name = column.getFullyQualifiedName();

        return ctx -> Mono.justOrEmpty(ctx.getValue(name));
    }

    @Override
    public String getId() {
        return ID;
    }
}
