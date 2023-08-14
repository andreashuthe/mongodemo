package de.demo.service;

import de.demo.dto.VehicleDto;

import java.util.List;

public interface VehicleService {
    Long saveVehicle(VehicleDto vehicle);

    VehicleDto provideVehicleById(Long id);

    List<VehicleDto> provideVehicleByName(String name);
}
