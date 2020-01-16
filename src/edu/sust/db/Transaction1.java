package edu.sust.db;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Transaction1 {
    private final IntegerProperty recno;
    private final IntegerProperty regId;
    private final IntegerProperty tax;
    private final StringProperty dateofpay;

    public Transaction1(int recno,int regId, int tax, String dateofpay){
        this.recno =new SimpleIntegerProperty(recno);
        this.regId = new SimpleIntegerProperty(regId);
        this.tax=new SimpleIntegerProperty(tax);
        this.dateofpay=new SimpleStringProperty(dateofpay);
    }

    public int getRecno() {
        return recno.get();
    }

    public void setRecno(int recno) {
        this.recno.set(recno);
    }

    public IntegerProperty recnoProperty() {
        return recno;
    }

    public int getRegId() {
        return regId.get();
    }

    public void setRegId(int regId) {
        this.regId.set(regId);
    }

    public IntegerProperty regIdProperty() {
        return regId;
    }

    public int getTax() {
        return tax.get();
    }

    public void setTax(int tax) {
        this.tax.set(tax);
    }

    public IntegerProperty taxProperty() {
        return tax;
    }

    public String getDateofpay() {
        return dateofpay.get();
    }

    public void setDateofpay(String dateofpay) {
        this.dateofpay.set(dateofpay);
    }

    public StringProperty dateofpayProperty() {
        return dateofpay;
    }



}
