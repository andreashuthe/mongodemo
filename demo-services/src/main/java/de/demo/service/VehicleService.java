package de.demo.service;

import de.demo.dto.CreateVehicleDto;
import de.demo.dto.VehicleDto;

import java.util.List;

public interface VehicleService {
    Long createVehicle(CreateVehicleDto vehicle);

    VehicleDto saveVehicle(VehicleDto vehicle);

    VehicleDto provideVehicleById(Long id);

    List<VehicleDto> provideVehicleByName(String name);
}
