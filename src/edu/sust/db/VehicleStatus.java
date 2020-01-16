package edu.sust.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Murtoza10 on 9/15/2014.
 */
@Entity
@Table(name = "vehicle_status", schema = "", catalog = "db_project")
public class VehicleStatus {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "status_id" , unique=true, nullable=false, insertable=true, updatable=true)
    private int statusId;

    private String roadPermit;
    private String operationalStatus;
//    private int registrationId;
    private  VehicleRegInfo reg;

    public VehicleStatus(){}
    public VehicleStatus(String roadPermit,String operationalStatus,VehicleRegInfo reg){
        //this.statusId=statusId;
        this.roadPermit=roadPermit;
        this.operationalStatus=operationalStatus;
        this.reg=reg;
    }


    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "road_permit")
    public String getRoadPermit() {
        return roadPermit;
    }

    public void setRoadPermit(String roadPermit) {
        this.roadPermit = roadPermit;
    }

    @Basic
    @Column(name = "operational_status")
    public String getOperationalStatus() {
        return operationalStatus;
    }

    public void setOperationalStatus(String operationalStatus) {
        this.operationalStatus = operationalStatus;
    }

    @Basic
    @Column(name = "registration_id")
    public VehicleRegInfo getReg() {
        return reg;
    }

    public void setReg(VehicleRegInfo reg) {
        this.reg = reg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleStatus that = (VehicleStatus) o;

//        if (registrationId != that.registrationId) return false;
        if (statusId != that.statusId) return false;
        if (operationalStatus != null ? !operationalStatus.equals(that.operationalStatus) : that.operationalStatus != null)
            return false;
        if (roadPermit != null ? !roadPermit.equals(that.roadPermit) : that.roadPermit != null) return false;

        return true;
    }

//    @Override
//    public int hashCode() {
//        int result = statusId;
//        result = 31 * result + (roadPermit != null ? roadPermit.hashCode() : 0);
//        result = 31 * result + (operationalStatus != null ? operationalStatus.hashCode() : 0);
////        result = 31 * result + registrationId;
//        return result;
//    }
}
