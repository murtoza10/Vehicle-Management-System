package edu.sust.db;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Table2 {


    private final IntegerProperty regId;
    private final StringProperty manufacturer;
    private final StringProperty model;
    private final StringProperty color;
    private final StringProperty validity;


    public Table2(int regId, String manufacturer,String model,String color,String validity) {

        this.regId = new SimpleIntegerProperty(regId);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.model = new SimpleStringProperty(model);
        this.color = new SimpleStringProperty(color);
        this.validity = new SimpleStringProperty(validity);
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

    public String getManufacturer() {
        return manufacturer.get();
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    public StringProperty manufacturerProperty() {
        return manufacturer;
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public StringProperty modelProperty() {
        return model;
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String color) {
        this.color.set(color);
    }

    public StringProperty colorProperty() {
        return color;
    }

    public String getValidity() {
        return validity.get();
    }

    public void setValidity(String validity) {
        this.validity.set(validity);
    }

    public StringProperty validityProperty() {
        return validity;
    }

}
