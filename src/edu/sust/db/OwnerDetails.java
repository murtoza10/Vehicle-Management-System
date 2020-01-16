package edu.sust.db;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Murtoza10 on 9/15/2014.
 */
@Entity
@Table(name = "owner_details", schema = "", catalog = "db_project")
public class OwnerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id" , unique=true, nullable=false, insertable=true, updatable=true)
    private int ownerId;

    private int nationalId;
    private String name;
    private Date dob;
    private String currAddrss;
    private String permAddrss;
    private int user1;
    private Set phones;

    private User user;

    @Basic
    @Column(name = "user_id")
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public OwnerDetails() {}
    public OwnerDetails(int nId,String name, Date dob,String currAddrss,String permAddrss,User user) {
        this.nationalId = nId;
        this.name = name;
        this.dob = dob;
        this.currAddrss= currAddrss;
        this.permAddrss= permAddrss;
        this.user = user;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    @Column(name = "national_id")
    public int getNationalId() {
        return nationalId;
    }

    public void setNationalId(int nationalId) {
        this.nationalId = nationalId;
    }

//    @Basic
//    @Column(name = "user_id")
//    public int getUser1() {
//        return user1;
//    }
//
//    public void setUser1(int userId) {
//        this.user1 = userId;
//    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "dob")
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Basic
    @Column(name = "curr_addrss")
    public String getCurrAddrss() {
        return currAddrss;
    }

    public void setCurrAddrss(String currAddrss) {
        this.currAddrss = currAddrss;
    }

    @Basic
    @Column(name = "perm_addrss")
    public String getPermAddrss() {
        return permAddrss;
    }

    public void setPermAddrss(String permAddrss) {
        this.permAddrss = permAddrss;
    }

    public Set getPhones() {
        return phones;
    }
    public void setPhones( Set phones ) {
        this.phones = phones;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        OwnerDetails that = (OwnerDetails) o;
//
//        if (nationalId != that.nationalId) return false;
//        if (ownerId != that.ownerId) return false;
//        if (currAddrss != null ? !currAddrss.equals(that.currAddrss) : that.currAddrss != null) return false;
//        if (dob != null ? !dob.equals(that.dob) : that.dob != null) return false;
//        if (name != null ? !name.equals(that.name) : that.name != null) return false;
//        if (permAddrss != null ? !permAddrss.equals(that.permAddrss) : that.permAddrss != null) return false;
//
//        return true;
//    }

//    @Override
//    public int hashCode() {
//        int result = ownerId;
//        result = 31 * result + nationalId;
//        result = 31 * result + (name != null ? name.hashCode() : 0);
//        result = 31 * result + (dob != null ? dob.hashCode() : 0);
//        result = 31 * result + (currAddrss != null ? currAddrss.hashCode() : 0);
//        result = 31 * result + (permAddrss != null ? permAddrss.hashCode() : 0);
//        return result;
//    }
}
