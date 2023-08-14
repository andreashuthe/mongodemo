package de.demo.bean;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vehicle")
@Data
public class Vehicle {

    @Transient
    public static final String SEQUENCE_NAME = "vehicle_sequence";
    @Id
    private Long id;

    private String name;

    private String vehicleCategory;

    private String vehicleNumber;

    private String vehicleIdNumber;

    public Vehicle(String name, String vehicleCategory, String vehicleNumber, String vehicleIdNumber) {
        super();
        this.name = name;
        this.vehicleCategory = vehicleCategory;
        this.vehicleNumber = vehicleNumber;
        this.vehicleIdNumber = vehicleIdNumber;
    }

}
