package de.demo.service.impl;

import de.demo.bean.Vehicle;
import de.demo.dao.VehicleRepository;
import de.demo.dto.VehicleDto;
import de.demo.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public VehicleDto provideVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findVehicleById(id);
        return new VehicleDto(vehicle.getId(), vehicle.getName(), vehicle.getVehicleCategory(), vehicle.getVehicleNumber(), vehicle.getVehicleIdNumber());
    }

    @Override
    public List<VehicleDto> provideVehicleByName(String name) {
        List<Vehicle> vehicle = vehicleRepository.findVehiclesByName(name);
        return vehicle.stream().map(v -> new VehicleDto(v.getId(), v.getName(), v.getVehicleCategory(), v.getVehicleNumber(), v.getVehicleIdNumber())).toList();
    }


}
