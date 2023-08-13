package de.demo.service.impl;

import de.demo.bean.Vehicle;
import de.demo.dao.VehicleRepository;
import de.demo.dto.VehicleDto;
import de.demo.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Long saveVehicle(VehicleDto vehicle) {
        final Vehicle vehicleToSave = new Vehicle(vehicle.name(), vehicle.vehicleCategory(), vehicle.vehicleNumber(), vehicle.vehicleIdNumber());
        return vehicleRepository.insert(vehicleToSave).getId();
    }
}
