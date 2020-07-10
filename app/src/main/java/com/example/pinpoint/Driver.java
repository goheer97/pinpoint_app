package com.example.pinpoint;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Driver implements Serializable, Parcelable
{

    @SerializedName("driver_id")
    @Expose
    private Integer driverId;
    @SerializedName("driver_name")
    @Expose
    private String driverName;
    @SerializedName("driver_contact_no")
    @Expose
    private String driverContactNo;
    @SerializedName("driver_cnic")
    @Expose
    private String driverCnic;
    @SerializedName("driver_address")
    @Expose
    private String driverAddress;
    @SerializedName("driver_joined_date")
    @Expose
    private String driverJoinedDate;
    public final static Parcelable.Creator<Driver> CREATOR = new Creator<Driver>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Driver createFromParcel(Parcel in) {
            return new Driver(in);
        }

        public Driver[] newArray(int size) {
            return (new Driver[size]);
        }

    }
            ;
    private final static long serialVersionUID = 4274476776853553862L;

    protected Driver(Parcel in) {
        this.driverId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.driverName = ((String) in.readValue((String.class.getClassLoader())));
        this.driverContactNo = ((String) in.readValue((String.class.getClassLoader())));
        this.driverCnic = ((String) in.readValue((String.class.getClassLoader())));
        this.driverAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.driverJoinedDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Driver() {
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverContactNo() {
        return driverContactNo;
    }

    public void setDriverContactNo(String driverContactNo) {
        this.driverContactNo = driverContactNo;
    }

    public String getDriverCnic() {
        return driverCnic;
    }

    public void setDriverCnic(String driverCnic) {
        this.driverCnic = driverCnic;
    }

    public String getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(String driverAddress) {
        this.driverAddress = driverAddress;
    }

    public String getDriverJoinedDate() {
        return driverJoinedDate;
    }

    public void setDriverJoinedDate(String driverJoinedDate) {
        this.driverJoinedDate = driverJoinedDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(driverId);
        dest.writeValue(driverName);
        dest.writeValue(driverContactNo);
        dest.writeValue(driverCnic);
        dest.writeValue(driverAddress);
        dest.writeValue(driverJoinedDate);
    }

    public int describeContents() {
        return 0;
    }

}