package de.demo.dao.event;

import de.demo.bean.Vehicle;
import de.demo.dao.SequenceGeneratorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class VehicleListener extends AbstractMongoEventListener<Vehicle> {
    private final SequenceGeneratorDao sequenceGenerator;

    @Autowired
    public VehicleListener(SequenceGeneratorDao sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Vehicle> event) {
        if (event.getSource().getId() == null) {
            event.getSource().setId(sequenceGenerator.generateSequence(Vehicle.SEQUENCE_NAME));
        }
    }
}
