package com.extspeeder.example.financialdemo.extra;

import static com.speedment.common.injector.State.RESOLVED;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.enterprise.datastore.generator.component.TypeSerializerComponent;
import com.speedment.generator.translator.component.TypeMapperComponent;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class ExtraComponent {

    @ExecuteBefore(RESOLVED)
    public void installTypeMappers(TypeMapperComponent typeMappers) {
        typeMappers.install(Integer.class, DateIntToShortMapper::new);
        typeMappers.install(String.class, BuySellMapper::new);
        typeMappers.install(String.class, OrderTypeMapper::new);
        typeMappers.install(String.class, StatusMapper::new);
        typeMappers.install(String.class, CohortTypeMapper::new);
    }
    
    @ExecuteBefore(RESOLVED)
    public void installSerializers(TypeSerializerComponent serializers) {
        serializers.install(new BuySellSerializer());
        serializers.install(new OrderTypeSerializer());
        serializers.install(new StatusSerializer());
        serializers.install(new CohortTypeSerializer());
    }

}