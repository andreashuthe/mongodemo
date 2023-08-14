package de.demo.dto;

import java.util.Objects;

public record CreateVehicleDto(String name, String vehicleCategory, String vehicleNumber, String vehicleIdNumber) {

    @Override
    public int hashCode() {
        return Objects.hash(name, vehicleCategory, vehicleNumber, vehicleIdNumber);
    }
}
