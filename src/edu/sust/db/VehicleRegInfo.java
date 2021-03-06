package edu.sust.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Murtoza10 on 9/15/2014.
 */
@Entity
@Table(name = "vehicle_reg_info", schema = "", catalog = "db_project")
public class VehicleRegInfo {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "registration_id" , unique=true, nullable=false, insertable=true, updatable=true)
    private int registrationId;

    private int plateNum;
    private Date dateOfReg;
    private int regFee;
    private String regArea;
    private String vehicleType;
//    private OwnerDetails owner;
    private Vehicle vehicle;
//    private int buildId;

    public VehicleRegInfo(){}
    public VehicleRegInfo(int plateNum, Date dateOfReg,int regFee,String regArea,String vehicleType,Vehicle vehicle ){
//        this.registrationId=registrationId;
        this.plateNum=plateNum;
        this.dateOfReg=dateOfReg;
        this.regFee=regFee;
        this.regArea=regArea;
        this.vehicleType=vehicleType;
        this.vehicle=vehicle;
//        this.owner=owner;
    }


    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    @Basic
    @Column(name = "plate_num")
    public int getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(int plateNum) {
        this.plateNum = plateNum;
    }

    @Basic
    @Column(name = "date_of_reg")
    public Date getDateOfReg() {
        return dateOfReg;
    }

    public void setDateOfReg(Date dateOfReg) {
        this.dateOfReg = dateOfReg;
    }

    @Basic
    @Column(name = "reg_fee")
    public int getRegFee() {
        return regFee;
    }

    public void setRegFee(int regFee) {
        this.regFee = regFee;
    }

    @Basic
    @Column(name = "reg_area")
    public String getRegArea() {
        return regArea;
    }

    public void setRegArea(String regArea) {
        this.regArea = regArea;
    }

    @Basic
    @Column(name = "vehicle_type")
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

//    @Basic
//    @Column(name = "owner_id")
//    public OwnerDetails getOwner() {
//        return owner;
//    }
//
//    public void setOwner(OwnerDetails owner) {
//        this.owner = owner;
//    }
    @Basic
    @Column(name = "chasis_num")
    public Vehicle getVehicle(){
        return  vehicle;
    }

    public void setVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleRegInfo that = (VehicleRegInfo) o;

//        if (buildId != that.buildId) return false;
//        if (ownerId != that.ownerId) return false;
        if (plateNum != that.plateNum) return false;
        if (regFee != that.regFee) return false;
        if (registrationId != that.registrationId) return false;
        if (dateOfReg != null ? !dateOfReg.equals(that.dateOfReg) : that.dateOfReg != null) return false;
        if (regArea != null ? !regArea.equals(that.regArea) : that.regArea != null) return false;
        if (vehicleType != null ? !vehicleType.equals(that.vehicleType) : that.vehicleType != null) return false;

        return true;
    }

//    @Override
//    public int hashCode() {
//        int result = registrationId;
//        result = 31 * result + plateNum;
//        result = 31 * result + (dateOfReg != null ? dateOfReg.hashCode() : 0);
//        result = 31 * result + regFee;
//        result = 31 * result + (regArea != null ? regArea.hashCode() : 0);
//        result = 31 * result + (vehicleType != null ? vehicleType.hashCode() : 0);
////        result = 31 * result + ownerId;
////        result = 31 * result + buildId;
//        return result;
//    }
}
