package com.extspeeder.example.financialdemo.extra;

import com.speedment.Speedment;
import com.speedment.component.ComponentConstructor;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class ExtraComponentInstaller 
implements ComponentConstructor<ExtraComponent> {

    @Override
    public ExtraComponent create(Speedment speedment) {
        return new ExtraComponent(speedment);
    }

}