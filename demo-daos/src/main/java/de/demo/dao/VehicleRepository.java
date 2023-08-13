package de.demo.dao;

import de.demo.bean.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle, Long> {

    @Query("{name:'?0'}")
    Vehicle findItemByName(String name);

    @Query(value="{vehicleCategory:'?0'}", fields="{'name' : 1, 'vehicleNumber' : 1}")
    List<Vehicle> findAll(String vehicleCategory);

    @Override
    long count();

    @Override Vehicle insert(Vehicle vehicle);
}
