package edu.sust.db;

import javax.persistence.*;

/**
 * Created by Murtoza10 on 9/15/2014.
 */
@Entity
@Table(name = "phone", schema = "", catalog = "db_project")
public class Phone {
//    private int pId;
    @Id
    @Column(name = "phone_num")
    private String phone;
//    private int ownerId;
//    private OwnerDetails owner;

//    @Id
//    @Column(name = "p_id")
//    public int getpId() {
//        return pId;
//    }
//
//    public void setpId(int pId) {
//        this.pId = pId;
//    }

    public Phone(){}
    public Phone(String phone){
        this.phone=phone;
//        this.owner=owner;
    }

    @Id
    @Column(name = "phone_num")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    @Basic
//    @Column(name = "owner")
//    public OwnerDetails getOwner() {
//        return owner;
//    }
//
//    public void setOwner(OwnerDetails owner) {
//        this.owner = owner;
//    }

//    @Basic
//    @Column(name = "owner_id")
//    public int getOwnerId() {
//        return ownerId;
//    }
//
//    public void setOwnerId(int ownerId) {
//        this.ownerId = ownerId;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone1 = (Phone) o;

//        if (ownerId != phone1.ownerId) return false;
//        if (pId != phone1.pId) return false;
        if (phone != phone1.phone) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (phone != null ? phone.hashCode() : 0);
//        result = 31 * result + phone;
//        result = 31 * result + ownerId;
        return result;
    }
}
