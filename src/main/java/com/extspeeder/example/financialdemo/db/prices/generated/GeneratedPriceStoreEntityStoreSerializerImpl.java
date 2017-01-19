package com.extspeeder.example.financialdemo.db.prices.generated;

import com.extspeeder.example.financialdemo.db.prices.PriceStore;
import com.extspeeder.example.financialdemo.db.prices.PriceStoreImpl;
import com.speedment.enterprise.fastcache.runtime.entitystore.EntityStoreSerializer;
import com.speedment.enterprise.fastcache.runtime.entitystore.StringSelection;
import com.speedment.enterprise.fastcache.runtime.util.SerializerUtil;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.StringField;
import com.speedment.runtime.field.trait.HasBooleanValue;
import com.speedment.runtime.field.trait.HasByteValue;
import com.speedment.runtime.field.trait.HasCharValue;
import com.speedment.runtime.field.trait.HasDoubleValue;
import com.speedment.runtime.field.trait.HasFloatValue;
import com.speedment.runtime.field.trait.HasIntValue;
import com.speedment.runtime.field.trait.HasLongValue;
import com.speedment.runtime.field.trait.HasReferenceValue;
import com.speedment.runtime.field.trait.HasShortValue;
import java.math.BigDecimal;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import javax.annotation.Generated;

/**
 * Serializes and deserializes instances of PriceStore.
 * <p>
 * This file has been automatically generated by Speedment. Any changes made to
 * it will be overwritten.
 * <p>
 * The layout of the ByteBuffer:
 * <pre>
 * +--------+--------+------+--------------------+--------+------------------+
 * | Begin  | End    | Len  |               Name |  Usage |             Type |
 * +--------+--------+------+--------------------+--------+------------------+
 * |                      Constant Non-Nullable Fields                       |
 * +--------+--------+------+--------------------+--------+------------------+
 * | 0x0000 | 0x0007 | 0x08 |                 id |   data |             long |
 * | 0x0008 | 0x000B | 0x04 |         value_date |   data |              int |
 * | 0x000C | 0x000F | 0x04 |               open |   data |            float |
 * | 0x0010 | 0x0013 | 0x04 |               high |   data |            float |
 * | 0x0014 | 0x0017 | 0x04 |                low |   data |            float |
 * +--------+--------+------+--------------------+--------+------------------+
 * |                              End Positions                              |
 * +--------+--------+------+--------------------+--------+------------------+
 * | 0x001C | 0x001C | 0x01 |              close | endpos |            Float |
 * | 0x001D | 0x0020 | 0x04 |  instrument_symbol | endpos |           String |
 * +--------+--------+------+--------------------+--------+------------------+
 * |                               Row Storage                               |
 * +--------+--------+------+--------------------+--------+------------------+
 * |                 Constant Nullables and Variable Fields                  |
 * +--------+--------+------+--------------------+--------+------------------+
 * | 0x0021 | 0x7FFE | 0x00 | Variable data area |   data |           byte[] |
 * +--------+--------+------+--------------------+--------+------------------+
 * </pre>
 * <p>
 * Variable data area layout order:
 * close, instrumentSymbol
 * 
 * @author Speedment
 */
@Generated("Speedment")
public class GeneratedPriceStoreEntityStoreSerializerImpl implements EntityStoreSerializer<PriceStore> {
    
    /**
     * Serializes and writes the id of the price_store to the specified buffer.
     * 
     * @param out       the byte buffer to write to
     * @param rowOffset index where the row should start
     * @param value     value to serialize and write
     */
    protected void serializeId(ByteBuffer out, int rowOffset, long value) {
        out.putLong(0 + rowOffset, value);
    }
    
    /**
     * Returns the id of the price_store.
     * 
     * @param in        the byte buffer to read from
     * @param rowOffset index in buffer where the value starts
     * @return          the deserialized value
     */
    protected long deserializeId(ByteBuffer in, int rowOffset) {
        return in.getLong(0 + rowOffset);
    }
    
    /**
     * Serializes and writes the value_date of the price_store to the specified
     * buffer.
     * 
     * @param out       the byte buffer to write to
     * @param rowOffset index where the row should start
     * @param value     value to serialize and write
     */
    protected void serializeValueDate(ByteBuffer out, int rowOffset, int value) {
        out.putInt(8 + rowOffset, value);
    }
    
    /**
     * Returns the value_date of the price_store.
     * 
     * @param in        the byte buffer to read from
     * @param rowOffset index in buffer where the value starts
     * @return          the deserialized value
     */
    protected int deserializeValueDate(ByteBuffer in, int rowOffset) {
        return in.getInt(8 + rowOffset);
    }
    
    /**
     * Serializes and writes the open of the price_store to the specified
     * buffer.
     * 
     * @param out       the byte buffer to write to
     * @param rowOffset index where the row should start
     * @param value     value to serialize and write
     */
    protected void serializeOpen(ByteBuffer out, int rowOffset, float value) {
        out.putFloat(12 + rowOffset, value);
    }
    
    /**
     * Returns the open of the price_store.
     * 
     * @param in        the byte buffer to read from
     * @param rowOffset index in buffer where the value starts
     * @return          the deserialized value
     */
    protected float deserializeOpen(ByteBuffer in, int rowOffset) {
        return in.getFloat(12 + rowOffset);
    }
    
    /**
     * Serializes and writes the high of the price_store to the specified
     * buffer.
     * 
     * @param out       the byte buffer to write to
     * @param rowOffset index where the row should start
     * @param value     value to serialize and write
     */
    protected void serializeHigh(ByteBuffer out, int rowOffset, float value) {
        out.putFloat(16 + rowOffset, value);
    }
    
    /**
     * Returns the high of the price_store.
     * 
     * @param in        the byte buffer to read from
     * @param rowOffset index in buffer where the value starts
     * @return          the deserialized value
     */
    protected float deserializeHigh(ByteBuffer in, int rowOffset) {
        return in.getFloat(16 + rowOffset);
    }
    
    /**
     * Serializes and writes the low of the price_store to the specified buffer.
     * 
     * @param out       the byte buffer to write to
     * @param rowOffset index where the row should start
     * @param value     value to serialize and write
     */
    protected void serializeLow(ByteBuffer out, int rowOffset, float value) {
        out.putFloat(20 + rowOffset, value);
    }
    
    /**
     * Returns the low of the price_store.
     * 
     * @param in        the byte buffer to read from
     * @param rowOffset index in buffer where the value starts
     * @return          the deserialized value
     */
    protected float deserializeLow(ByteBuffer in, int rowOffset) {
        return in.getFloat(20 + rowOffset);
    }
    
    /**
     * Serializes and writes the close of the price_store to the specified
     * buffer.
     * 
     * @param out          the byte buffer to write to
     * @param rowOffset    index where the row should start
     * @param begin        offset to the position for the particular column
     * @param endPosOffset offset to the position for the end index
     * @param value        value to serialize and write
     * @return             last index of the string (exclusive)
     */
    protected int serializeClose(
            ByteBuffer out,
            int rowOffset,
            int begin,
            int endPosOffset,
            Float value) {
        if (value == null) {
            out.put(rowOffset + endPosOffset, (byte)(-begin));
            return begin;
        } else {
            out.putFloat(begin + rowOffset, value);
            out.put(rowOffset + endPosOffset, (byte)(begin + 4));
            return begin + 4;
        }
    }
    
    /**
     * Returns the close of the price_store.
     * 
     * @param in        the byte buffer to read from
     * @param rowOffset index in buffer where the value starts
     * @param end       last index of the value (exclusive). Negative if null.
     * @return          the deserialized value
     */
    protected Float deserializeClose(ByteBuffer in, int rowOffset, int end) {
        if (end < 0) {
            return null;
        } else {
            return in.getFloat(rowOffset + end - 4);
        }
    }
    
    /**
     * Serializes and writes the instrument_symbol of the price_store to the
     * specified buffer, returning the index of the next value.
     * 
     * @param out          the byte buffer to write to
     * @param rowOffset    index in buffer where the row starts
     * @param begin        offset to the position for the particular column
     * @param endPosOffset offset to the position for the end index
     * @param value        the value to serialize
     * @return             last index of the string (exclusive)
     */
    protected int serializeInstrumentSymbol(
            ByteBuffer out,
            int rowOffset,
            int begin,
            int endPosOffset,
            String value) {
        return SerializerUtil.serializeByteArrayPutInt(out, rowOffset, begin, value.getBytes(StandardCharsets.UTF_8), endPosOffset);
    }
    
    /**
     * Returns the instrument_symbol of the price_store.
     * 
     * @param in        the byte buffer to read from
     * @param rowOffset index in buffer where the row starts
     * @param begin     where in the row the field begins
     * @param end       where in the row the field ends (exclusive)
     * @return          the deserialized value
     */
    protected String deserializeInstrumentSymbol(
            ByteBuffer in,
            int rowOffset,
            int begin,
            int end) {
        return selectStringInstrumentSymbol(in, rowOffset, begin, end)
            .toString(StringSelection.Encoding.UTF_8);
    }
    
    @Override
    public StringSelection selectString(ByteBuffer in, int rowOffset, StringField<PriceStore, ?> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        if ("instrument_symbol".equals(colId.getColumnName())) {
            return selectStringInstrumentSymbol(in, rowOffset, Math.abs(in.get(rowOffset + 28)), in.getInt(rowOffset + 29));
        }
        
        throw new UnsupportedOperationException(
            String.format("Unknown column '%s'.", colId)
        );
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserializeReference(ByteBuffer in, int rowOffset, HasReferenceValue<PriceStore, ?, T> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        if (colId instanceof PriceStore.Identifier) {
            final PriceStore.Identifier _id = (PriceStore.Identifier) colId;
            switch (_id) {
                case CLOSE             : return (T) deserializeClose(in,              rowOffset, in.get(rowOffset + 28));
                case INSTRUMENT_SYMBOL : return (T) deserializeInstrumentSymbol(in,   rowOffset, Math.abs(in.get(rowOffset + 28)), in.getInt(rowOffset + 29));
                default : throw new UnsupportedOperationException(
                    String.format("Unknown enum constant '%s'.", _id)
                );
            }
        } else {
            final String _colName = colId.getColumnName();
            switch (_colName) {
                case "close"             : return (T) deserializeClose(in,            rowOffset, in.get(rowOffset + 28));
                case "instrument_symbol" : return (T) deserializeInstrumentSymbol(in, rowOffset, Math.abs(in.get(rowOffset + 28)), in.getInt(rowOffset + 29));
                default : throw new UnsupportedOperationException(
                    String.format("Unknown column name '%s'.", _colName)
                );
            }
        }
    }
    
    @Override
    public boolean deserializeBoolean(ByteBuffer in, int rowOffset, HasBooleanValue<PriceStore, ?> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        throw new UnsupportedOperationException(
            String.format("Unknown enum constant '%s'.", colId.getColumnName())
        );
    }
    
    @Override
    public byte deserializeByte(ByteBuffer in, int rowOffset, HasByteValue<PriceStore, ?> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        throw new UnsupportedOperationException(
            String.format("Unknown enum constant '%s'.", colId.getColumnName())
        );
    }
    
    @Override
    public double deserializeDouble(ByteBuffer in, int rowOffset, HasDoubleValue<PriceStore, ?> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        throw new UnsupportedOperationException(
            String.format("Unknown enum constant '%s'.", colId.getColumnName())
        );
    }
    
    @Override
    public char deserializeChar(ByteBuffer in, int rowOffset, HasCharValue<PriceStore, ?> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        throw new UnsupportedOperationException(
            String.format("Unknown enum constant '%s'.", colId.getColumnName())
        );
    }
    
    @Override
    public short deserializeShort(ByteBuffer in, int rowOffset, HasShortValue<PriceStore, ?> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        throw new UnsupportedOperationException(
            String.format("Unknown enum constant '%s'.", colId.getColumnName())
        );
    }
    
    @Override
    public float deserializeFloat(ByteBuffer in, int rowOffset, HasFloatValue<PriceStore, ?> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        if (colId instanceof PriceStore.Identifier) {
            final PriceStore.Identifier _id = (PriceStore.Identifier) colId;
            switch (_id) {
                case OPEN : return deserializeOpen(in,   rowOffset);
                case HIGH : return deserializeHigh(in,   rowOffset);
                case LOW  : return deserializeLow(in,    rowOffset);
                default : throw new UnsupportedOperationException(
                    String.format("Unknown enum constant '%s'.", _id)
                );
            }
        } else {
            final String _colName = colId.getColumnName();
            switch (_colName) {
                case "open" : return deserializeOpen(in, rowOffset);
                case "high" : return deserializeHigh(in, rowOffset);
                case "low"  : return deserializeLow(in,  rowOffset);
                default : throw new UnsupportedOperationException(
                    String.format("Unknown column name '%s'.", _colName)
                );
            }
        }
    }
    
    @Override
    public int deserializeInt(ByteBuffer in, int rowOffset, HasIntValue<PriceStore, ?> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        if ("value_date".equals(colId.getColumnName())) {
            return deserializeValueDate(in, rowOffset);
        }
        
        throw new UnsupportedOperationException(
            String.format("Unknown column '%s'.", colId)
        );
    }
    
    @Override
    public long deserializeLong(ByteBuffer in, int rowOffset, HasLongValue<PriceStore, ?> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        if ("id".equals(colId.getColumnName())) {
            return deserializeId(in, rowOffset);
        }
        
        throw new UnsupportedOperationException(
            String.format("Unknown column '%s'.", colId)
        );
    }
    
    @Override
    public PriceStore deserialize(ByteBuffer in, int rowOffset) {
        final int closeEnd            = in.get(rowOffset + 28);
        final int instrumentSymbolEnd = in.getInt(rowOffset + 29);
        
        final PriceStore entity = newEntity();
        entity.setId(deserializeId(in, rowOffset));
        entity.setValueDate(deserializeValueDate(in, rowOffset));
        entity.setOpen(deserializeOpen(in, rowOffset));
        entity.setHigh(deserializeHigh(in, rowOffset));
        entity.setLow(deserializeLow(in, rowOffset));
        entity.setClose(deserializeClose(in, rowOffset, closeEnd));
        entity.setInstrumentSymbol(deserializeInstrumentSymbol(in, rowOffset, Math.abs(closeEnd), instrumentSymbolEnd));
        return entity;
    }
    
    @Override
    public int serialize(ByteBuffer out, int rowOffset, PriceStore entity) throws BufferOverflowException {
        int startPos = 33;
        
        serializeId(out, rowOffset, entity.getId());
        serializeValueDate(out, rowOffset, entity.getValueDate());
        serializeOpen(out, rowOffset, entity.getOpen());
        serializeHigh(out, rowOffset, entity.getHigh());
        serializeLow(out, rowOffset, entity.getLow());
        startPos = serializeClose(out, rowOffset, startPos, 28, entity.getClose());
        startPos = serializeInstrumentSymbol(out, rowOffset, startPos, 29, entity.getInstrumentSymbol());
        return startPos;
    }
    
    protected PriceStore newEntity() {
        return new PriceStoreImpl();
    }
    
    /**
     * Returns a selection of the instrument_symbol of the price_store.
     * 
     * @param in        the byte buffer to read from
     * @param rowOffset index in buffer where the row starts
     * @param begin     where in the row the field begins
     * @param end       where in the row the field ends (exclusive)
     * @return          the deserialized value
     */
    protected StringSelection selectStringInstrumentSymbol(
            ByteBuffer in,
            int rowOffset,
            int begin,
            int end) {
        final int len = end - begin;
        return StringSelection.create(in, rowOffset + begin, len);
    }
    
    @Override
    public boolean isNull(ByteBuffer in, int rowOffset, Field<PriceStore> field) {
        final ColumnIdentifier<PriceStore> colId = field.identifier();
        if (colId instanceof PriceStore.Identifier) {
            final PriceStore.Identifier _id = (PriceStore.Identifier) colId;
            switch (_id) {
                case ID                : return false;
                case VALUE_DATE        : return false;
                case OPEN              : return false;
                case HIGH              : return false;
                case LOW               : return false;
                case CLOSE             : return in.getInt(rowOffset + 28) < 0;
                case INSTRUMENT_SYMBOL : return false;
                default : throw new UnsupportedOperationException(
                    String.format("Unknown enum constant '%s'.", _id)
                );
            }
        } else {
            final String _colName = colId.getColumnName();
            switch (_colName) {
                case "id"                : return false;
                case "value_date"        : return false;
                case "open"              : return false;
                case "high"              : return false;
                case "low"               : return false;
                case "close"             : return in.getInt(rowOffset + 28) < 0;
                case "instrument_symbol" : return false;
                default : throw new UnsupportedOperationException(
                    String.format("Unknown column name '%s'.", _colName)
                );
            }
        }
    }
    
    @Override
    public void close() {
        
    }
}