package com.example.pinpoint;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable, Parcelable
{

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_creation_date")
    @Expose
    private String customerCreationDate;
    @SerializedName("customer_password")
    @Expose
    private String customerPassword;
    @SerializedName("customer_number")
    @Expose
    private String customerNumber;
    public final static Parcelable.Creator<Customer> CREATOR = new Creator<Customer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        public Customer[] newArray(int size) {
            return (new Customer[size]);
        }

    }
            ;
    private final static long serialVersionUID = -5156691665494525659L;

    protected Customer(Parcel in) {
        this.customerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customerName = ((String) in.readValue((String.class.getClassLoader())));
        this.customerEmail = ((String) in.readValue((String.class.getClassLoader())));
        this.customerCreationDate = ((String) in.readValue((String.class.getClassLoader())));
        this.customerPassword = ((String) in.readValue((String.class.getClassLoader())));
        this.customerNumber = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Customer() {
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerCreationDate() {
        return customerCreationDate;
    }

    public void setCustomerCreationDate(String customerCreationDate) {
        this.customerCreationDate = customerCreationDate;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(customerId);
        dest.writeValue(customerName);
        dest.writeValue(customerEmail);
        dest.writeValue(customerCreationDate);
        dest.writeValue(customerPassword);
        dest.writeValue(customerNumber);
    }

    public int describeContents() {
        return 0;
    }

}