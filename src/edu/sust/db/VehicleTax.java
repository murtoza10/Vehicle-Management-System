package edu.sust.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Murtoza10 on 9/15/2014.
 */
@Entity
@Table(name = "vehicle_tax", schema = "", catalog = "db_project")
public class VehicleTax {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "record_no" , unique=true, nullable=false, insertable=true, updatable=true)
    private int recordNo;

    private int taxAmount;
    private Date dateOfPayment;
//    private int registrationId;
    private VehicleRegInfo reg;

    public VehicleTax(){}
    public VehicleTax(int taxAmount,Date dateOfPayment,VehicleRegInfo reg){
        //this.recordNo=recordNo;
        this.taxAmount=taxAmount;
        this.dateOfPayment=dateOfPayment;
        this.reg=reg;
    }


    public int getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(int recordNo) {
        this.recordNo = recordNo;
    }

    @Basic
    @Column(name = "tax_amount")
    public int getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(int taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Basic
    @Column(name = "date_of_payment")
    public Date getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(Date dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
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

        VehicleTax that = (VehicleTax) o;

        if (recordNo != that.recordNo) return false;
//        if (registrationId != that.registrationId) return false;
        if (taxAmount != that.taxAmount) return false;
        if (dateOfPayment != null ? !dateOfPayment.equals(that.dateOfPayment) : that.dateOfPayment != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = recordNo;
        result = 31 * result + taxAmount;
        result = 31 * result + (dateOfPayment != null ? dateOfPayment.hashCode() : 0);
//        result = 31 * result + registrationId;
        return result;
    }
}
