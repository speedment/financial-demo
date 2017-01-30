package com.extspeeder.example.financialdemo.extra;

import com.speedment.common.codegen.model.Method;
import com.speedment.common.injector.Injector;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.enterprise.datastore.generator.serializer.TypeSerializer;
import com.speedment.enterprise.datastore.generator.serializer.TypeSerializerUtil;
import static com.speedment.enterprise.datastore.generator.serializer.TypeSerializerUtil.constantSizeDeserializer;
import static com.speedment.enterprise.datastore.generator.serializer.TypeSerializerUtil.constantSizeNullableDeserializer;
import static com.speedment.enterprise.datastore.generator.serializer.TypeSerializerUtil.constantSizeNullableSerializer;
import static com.speedment.enterprise.datastore.generator.serializer.TypeSerializerUtil.constantSizeSerializer;
import static com.speedment.enterprise.datastore.generator.serializer.TypeSerializerUtil.variableSizeDeserializer;
import static com.speedment.enterprise.datastore.generator.serializer.TypeSerializerUtil.variableSizeSerializer;
import com.speedment.generator.translator.TranslatorSupport;
import com.speedment.generator.translator.component.TypeMapperComponent;
import com.speedment.runtime.config.Column;
import com.speedment.runtime.config.Table;
import java.lang.reflect.Type;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
abstract class AbstractTypeSerializer implements TypeSerializer {

    private @Inject Injector injector;
    private @Inject TypeMapperComponent typeMappers;
    
    AbstractTypeSerializer() {}
    
    protected final Type typeOf(Column column) {
        return TypeSerializerUtil.typeOf(typeMappers, column);
    }
    
    protected final Method newConstantSizeDeserializer(Column column) {
        return constantSizeDeserializer(getSupport(column), column);
    }
    
    protected final Method newConstantSizeNullableDeserializer(Column column) {
        return constantSizeNullableDeserializer(getSupport(column), column);
    }
    
    protected final Method newConstantSizeSerializer(Column column) {
        return constantSizeSerializer(getSupport(column), column);
    }
    
    protected final Method newConstantSizeNullableSerializer(Column column) {
        return constantSizeNullableSerializer(getSupport(column), column);
    }
    
    protected final Method newVariableSizeDeserializer(Column column) {
        return variableSizeDeserializer(getSupport(column), column);
    }
    
    protected final Method newVariableSizeSerializer(Column column) {
        return variableSizeSerializer(getSupport(column), column);
    }

    protected final TranslatorSupport<Table> getSupport(Column column) {
        return new TranslatorSupport<>(injector, column.getParentOrThrow());
    }
}