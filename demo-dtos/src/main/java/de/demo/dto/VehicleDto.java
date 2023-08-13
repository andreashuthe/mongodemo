package de.demo.dto;

import java.util.Objects;

public record VehicleDto(Long id, String name, String vehicleCategory, String vehicleNumber, String vehicleIdNumber) {

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (!(other instanceof VehicleDto)) {
            return false;
        }
        VehicleDto vehicle = (VehicleDto) other;
        return Objects.equals(vehicle.id, this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
