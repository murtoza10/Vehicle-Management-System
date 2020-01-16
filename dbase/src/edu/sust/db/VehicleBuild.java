package edu.sust.db;

import javax.persistence.*;

/**
 * Created by Murtoza10 on 9/15/2014.
 */
@Entity
@Table(name = "vehicle_build", schema = "", catalog = "db_project")
public class VehicleBuild {
    private int buildId;
    private String manufacturer;
    private String engineType;
    private String fuelType;
    private String wheelType;
    private String model;
    private String yearOfManufacture;

    @Id
    @Column(name = "build_id")
    public int getBuildId() {
        return buildId;
    }

    public void setBuildId(int buildId) {
        this.buildId = buildId;
    }

    @Basic
    @Column(name = "manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Basic
    @Column(name = "engine_type")
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @Basic
    @Column(name = "fuel_type")
    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    @Basic
    @Column(name = "wheel_type")
    public String getWheelType() {
        return wheelType;
    }

    public void setWheelType(String wheelType) {
        this.wheelType = wheelType;
    }

    @Basic
    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "year_of_manufacture")
    public String getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(String yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleBuild that = (VehicleBuild) o;

        if (buildId != that.buildId) return false;
        if (engineType != null ? !engineType.equals(that.engineType) : that.engineType != null) return false;
        if (fuelType != null ? !fuelType.equals(that.fuelType) : that.fuelType != null) return false;
        if (manufacturer != null ? !manufacturer.equals(that.manufacturer) : that.manufacturer != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (wheelType != null ? !wheelType.equals(that.wheelType) : that.wheelType != null) return false;
        if (yearOfManufacture != null ? !yearOfManufacture.equals(that.yearOfManufacture) : that.yearOfManufacture != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = buildId;
        result = 31 * result + (manufacturer != null ? manufacturer.hashCode() : 0);
        result = 31 * result + (engineType != null ? engineType.hashCode() : 0);
        result = 31 * result + (fuelType != null ? fuelType.hashCode() : 0);
        result = 31 * result + (wheelType != null ? wheelType.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (yearOfManufacture != null ? yearOfManufacture.hashCode() : 0);
        return result;
    }
}
