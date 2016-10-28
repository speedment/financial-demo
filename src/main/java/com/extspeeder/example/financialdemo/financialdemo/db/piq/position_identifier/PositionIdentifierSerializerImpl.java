package com.extspeeder.example.financialdemo.financialdemo.db.piq.position_identifier;

import com.extspeeder.example.financialdemo.financialdemo.db.piq.position_identifier.generated.GeneratedPositionIdentifierSerializerImpl;
import com.speedment.manager.Manager;

/**
 * A {@link org.mapdb.Serializer} class for table {@link
 * com.speedment.config.db.Table} named position_identifier. representing an
 * entity (for example, a row) in the Table
 * financialdemo.db0.piq.position_identifier.
 * <p>
 * This file is safe to edit. It will not be overwritten by the code
 * generator.
 * 
 * @author Speedment
 */
public final class PositionIdentifierSerializerImpl extends GeneratedPositionIdentifierSerializerImpl {
    
    private final static long serialVersionUID = GeneratedPositionIdentifierSerializerImpl.serialVersionUID + 1;
    
    public PositionIdentifierSerializerImpl(final Manager<PositionIdentifier> manager) {
        super(manager);
    }
}