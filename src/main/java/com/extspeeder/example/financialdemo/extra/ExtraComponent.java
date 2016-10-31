package com.extspeeder.example.financialdemo.extra;

import com.speedment.Speedment;
import com.speedment.component.Component;
import com.speedment.component.TypeMapperComponent;
import com.speedment.internal.core.platform.component.impl.AbstractComponent;
import com.speedment.internal.license.AbstractSoftware;
import static com.speedment.internal.license.OpenSourceLicense.APACHE_2;
import com.speedment.license.Software;

/**
 *
 * @author Emil Forslund
 * @since  1.0.0
 */
public final class ExtraComponent extends AbstractComponent {

    public ExtraComponent(Speedment speedment) {
        super(speedment);
    }

    @Override
    public void onResolve() {
        final TypeMapperComponent typeMappers = getSpeedment().getTypeMapperComponent();
        typeMappers.install(BuySellMapper::new);
        typeMappers.install(OrderTypeMapper::new);
        typeMappers.install(StatusMapper::new);
    }

    @Override
    public Class<ExtraComponent> getComponentClass() {
        return ExtraComponent.class;
    }

    @Override
    public Software asSoftware() {
        return AbstractSoftware.with("Extra Component", "1.0.0", APACHE_2);
    }

    @Override
    public Component defaultCopy(Speedment speedment) {
        return new ExtraComponent(speedment);
    }
}