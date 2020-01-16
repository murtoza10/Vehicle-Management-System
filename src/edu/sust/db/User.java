package edu.sust.db;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Murtoza10 on 9/15/2014.
 */
@Entity
@Table(name = "user", schema = "", catalog = "db_project")
public class User {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "user_id" , unique=true, nullable=false, insertable=true, updatable=true)
    private int userId;

    private String username;
    private String password;
    private int rank;
//    private int ownerId;
    private User user;
    //@Column(name = "user_id" , unique=true, nullable=false, insertable=true, updatable=true)
//    public int getOwnerId() {
//        return ownerId;
//    }
//
//    public void setOwnerId(int ownerId) {
//        this.ownerId = ownerId;
//    }

    private OwnerDetails owner;
    public User() {}
    public User(String username,String password,int rank) {

        this.username=username;
        this.password = password;
        this.rank = rank;


    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername(){
        return username;
    }

    public  void setUsername(String username){ this.username= username; }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "rank")
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }


//    public OwnerDetails getOwner(){
//        return owner;
//    }
//
//    public  void  setOwner(OwnerDetails owner){
//        this.owner=owner;
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        User that = (User) o;
//
//        if (password != that.password) return false;
//        if (rank != that.rank) return false;
//        if (userId != that.userId) return false;
//        if (username != null ? !username.equals(that.username) : that.username != null) return false;
//
//        return true;
//    }

//    @Override
//    public int hashCode() {
//        int result = username != null ? username.hashCode() : 0;
//        result = 31 * result + (password != null ? password.hashCode() : 0);
//        result = 31 * result + rank;
//
//        return result;
//    }
}
