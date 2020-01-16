package edu.sust.db;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Murtoza10 on 9/15/2014.
 */
@Entity
public class Vehicle {
    private String engineNum;
    private String chasisNum;
    private Date validTill;
//    private int registrationId;
//    private int ownerId;
    private int buildId;
    private String color;
    private OwnerDetails owner;
//    private VehicleRegInfo reg;
    private VehicleBuild build;

    public Vehicle() {}
    public Vehicle(String engineNum,String chasisNum, Date validTill,String color,OwnerDetails owner,VehicleBuild build) {
        this.engineNum = engineNum;
        this.chasisNum = chasisNum;
        this.validTill = validTill;
//        this.registrationId= registrationId;
//        this.buildId= buildId;
        this.color = color;
        this.owner=owner;
//        this.reg=reg;
        this.build=build;
    }


    @Basic
    @Column(name = "engine_num")
    public String getEngineNum() {
        return engineNum;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    @Id
    @Column(name = "chasis_num")
    public String getChasisNum() {
        return chasisNum;
    }

    public void setChasisNum(String chasisNum) {
        this.chasisNum = chasisNum;
    }

    @Basic
    @Column(name = "valid_till")
    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

//    @Basic
//    @Column(name = "registration_id")
//    public VehicleRegInfo getReg() {
//        return reg;
//    }
//
//    public void setReg(VehicleRegInfo reg) {
//        this.reg = reg;
//    }

    @Basic
    @Column(name = "owner_id")
    public OwnerDetails getOwner() {
        return owner;
    }

    public void setOwner(OwnerDetails owner) {
        this.owner = owner;
    }

//    @Basic
//    @Column(name = "build_id")
//    public int getBuildId() {
//        return buildId;
//    }
//
//    public void setBuildId(int buildId) {
//        this.buildId = buildId;
//    }

    @Basic
    @Column(name = "build_id")
    public VehicleBuild getBuild() {
        return build;
    }

    public void setBuild(VehicleBuild build) {
        this.build = build;
    }

    @Basic
    @Column(name = "color")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        if (buildId != vehicle.buildId) return false;
//        if (ownerId != vehicle.ownerId) return false;
//        if (registrationId != vehicle.registrationId) return false;
        if (chasisNum != null ? !chasisNum.equals(vehicle.chasisNum) : vehicle.chasisNum != null) return false;
        if (color != null ? !color.equals(vehicle.color) : vehicle.color != null) return false;
        if (engineNum != null ? !engineNum.equals(vehicle.engineNum) : vehicle.engineNum != null) return false;
        if (validTill != null ? !validTill.equals(vehicle.validTill) : vehicle.validTill != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = engineNum != null ? engineNum.hashCode() : 0;
        result = 31 * result + (chasisNum != null ? chasisNum.hashCode() : 0);
        result = 31 * result + (validTill != null ? validTill.hashCode() : 0);
//        result = 31 * result + registrationId;
//        result = 31 * result + ownerId;
        result = 31 * result + buildId;
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
