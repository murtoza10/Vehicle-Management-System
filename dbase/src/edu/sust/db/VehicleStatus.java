package edu.sust.db;

import javax.persistence.*;

/**
 * Created by Murtoza10 on 9/15/2014.
 */
@Entity
@Table(name = "vehicle_status", schema = "", catalog = "db_project")
public class VehicleStatus {
    private int statusId;
    private String roadPermit;
    private String operationalStatus;
    private int registrationId;

    @Id
    @Column(name = "status_id")
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
    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleStatus that = (VehicleStatus) o;

        if (registrationId != that.registrationId) return false;
        if (statusId != that.statusId) return false;
        if (operationalStatus != null ? !operationalStatus.equals(that.operationalStatus) : that.operationalStatus != null)
            return false;
        if (roadPermit != null ? !roadPermit.equals(that.roadPermit) : that.roadPermit != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusId;
        result = 31 * result + (roadPermit != null ? roadPermit.hashCode() : 0);
        result = 31 * result + (operationalStatus != null ? operationalStatus.hashCode() : 0);
        result = 31 * result + registrationId;
        return result;
    }
}
