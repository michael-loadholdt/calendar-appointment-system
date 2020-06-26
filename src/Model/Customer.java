/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import javafx.scene.control.Alert;

/**
 *
 * @author micha
 */
public class Customer{
    
    //Instance Variables
    private int customerId;
    private String customerName;
    private int addressId;
    private int active;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    
    //Constructors
    //Default Constructor
    public Customer() {    
    }

    //No ID Constructor
    public Customer(String customerName, int addressId, int active, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {    
        setCustomerName(customerName);
        setAddressId(addressId);
        setActive(active);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setLastUpdateBy(lastUpdateBy);
    }

    //Full Constructor
    public Customer(int customerId, String customerName, int addressId, int active, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        setCustomerId(customerId);
        setCustomerName(customerName);
        setAddressId(addressId);
        setActive(active);
        setCreateDate(createDate);
        setCreatedBy(createdBy);
        setLastUpdate(lastUpdate);
        setLastUpdateBy(lastUpdateBy);
    }
    
    //Getters and Setters

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if(!customerName.isEmpty()){
        this.customerName = customerName;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Customer Name is required.");
            alert.showAndWait();
        }
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
              
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    @Override
    public String toString(){
        
        return getCustomerName();
    }

}
