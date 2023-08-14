package de.demo.app.controller;

import de.demo.dto.VehicleDto;
import de.demo.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public VehicleDto createVehicle(Principal principal, VehicleDto vehicleDto) {
        Long id = vehicleService.saveVehicle(vehicleDto);
        return vehicleService.provideVehicleById(id);
    }

    @GetMapping("/name/{name}")
    public List<VehicleDto> getVehicleByName(Principal principal, @PathVariable String name) {
        return vehicleService.provideVehicleByName(name);
    }
}
