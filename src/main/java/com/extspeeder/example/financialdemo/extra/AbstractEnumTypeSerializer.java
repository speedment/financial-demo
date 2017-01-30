package com.extspeeder.example.financialdemo.extra;

import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Method;
import static com.speedment.common.codegen.util.Formatting.block;
import com.speedment.enterprise.datastore.generator.serializer.Allocation;
import com.speedment.enterprise.datastore.generator.serializer.AllocationOffset;
import com.speedment.enterprise.datastore.generator.serializer.EndPointerType;
import static com.speedment.enterprise.datastore.generator.serializer.TypeSerializerUtil.PARAM_IN;
import static com.speedment.enterprise.datastore.generator.serializer.TypeSerializerUtil.PARAM_OUT;
import static com.speedment.enterprise.datastore.generator.serializer.TypeSerializerUtil.PARAM_VALUE;
import com.speedment.runtime.config.Column;
import java.nio.charset.StandardCharsets;
import static java.util.Objects.requireNonNull;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
abstract class AbstractEnumTypeSerializer<E extends Enum<E>> extends AbstractTypeSerializer {

    private final Class<E> enumClass;
    private final E[] values;
    
    AbstractEnumTypeSerializer(Class<E> enumClass, E[] values) {
        this.enumClass = requireNonNull(enumClass);
        this.values    = requireNonNull(values);
    }
    
    @Override
    public boolean canSerialize(Column column) {
        return typeOf(column).getTypeName()
            .equals(enumClass.getTypeName());
    }

    @Override
    public boolean isConstantSize(Column column) {
        return true;
    }

    @Override
    public boolean isNonNullableAndConstantSize(Column column) {
        return !column.isNullable();
    }

    @Override
    public Allocation perTableAllocation(Column column) {
        return Allocation.empty();
    }

    @Override
    public Allocation perRowAllocation(Column column) {
        return Allocation.empty();
    }

    @Override
    public Allocation perCellAllocation(Column column) {
        return Allocation.constant(ordinalBytes());
    }

    @Override
    public Method makeDeserialize(Column column, AllocationOffset offset, EndPointerType endPointerType) {
        return newConstantSizeDeserializer(column)
            .add("switch (" + PARAM_IN + ".get" + bufferMethodName() + "(" + offset.cellOffset() + ") + " + offset.tableOffset() + ") " + block(
                Stream.of(values)
                    .map(value -> "case " + value.ordinal() + " : return " + enumClass.getSimpleName() + "." + value.name() + ";")
            ), "throw new IllegalStateException(\"Unexpected " + enumClass.getSimpleName() + " ordinal in serialized data.\");");
    }

    @Override
    public Method makeSerialize(Column column, AllocationOffset offset, EndPointerType endPointerType) {
        return newConstantSizeSerializer(column)
            .add("switch (" + PARAM_VALUE + ") " + block(
                Stream.of(values)
                    .map(c -> serializeConstant(offset, c))
            ), "throw new IllegalStateException(\"Unexpected enum constant '\" + " + PARAM_VALUE + " + \"'.\");");
    }
    
    private String serializeConstant(AllocationOffset offset, E value) {
        final String name  = value.name();
        
        return "case " + name + " : " + 
            PARAM_OUT + ".put" + bufferMethodName() + "(" + offset.cellOffset() + ", " + conversion() + value.ordinal() + "); return;"
        ;
    }

    @Override
    public Stream<Import> imports() {
        return Stream.of(
            Import.of(enumClass),
            Import.of(StandardCharsets.class)
        );
    }
    
    private String conversion() {
        final int bytes = ordinalBytes();
        
        switch (bytes) {
            case 4 : return "";
            case 2 : return "(short) ";
            case 1 : return "(byte) ";
        }
        
        throw new IllegalStateException(
            "A pointer of " + bytes + " bytes is illegal."
        );
    }
    
    private String bufferMethodName() {
        final int bytes = ordinalBytes();
        
        switch (bytes) {
            case 4 : return "Int";
            case 2 : return "Short";
            case 1 : return "";
        }
        
        throw new IllegalStateException(
            "A pointer of " + bytes + " bytes is illegal."
        );
    }
    
    private int ordinalBytes() {
        final int size = values.length;
        
        if (size > Short.MAX_VALUE) 
            return Integer.BYTES;
        else if (size > Byte.MAX_VALUE) 
            return Short.BYTES;
        else
            return Byte.BYTES;
    }
    
}