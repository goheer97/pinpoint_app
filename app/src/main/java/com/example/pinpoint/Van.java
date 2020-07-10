package com.example.pinpoint;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Van implements Serializable, Parcelable
{

    @SerializedName("van_id")
    @Expose
    private Integer vanId;
    @SerializedName("shipment_date")
    @Expose
    private String shipmentDate;
    @SerializedName("van_reg_no")
    @Expose
    private String vanRegNo;
    @SerializedName("van_model")
    @Expose
    private String vanModel;

    public final static Parcelable.Creator<Van> CREATOR = new Creator<Van>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Van createFromParcel(Parcel in) {
            return new Van(in);
        }

        public Van[] newArray(int size) {
            return (new Van[size]);
        }

    }
            ;
    private final static long serialVersionUID = -5156691665494525659L;

    protected Van(Parcel in) {
        this.vanId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.shipmentDate = ((String) in.readValue((String.class.getClassLoader())));
        this.vanModel = ((String) in.readValue((String.class.getClassLoader())));
        this.vanRegNo= ((String) in.readValue((String.class.getClassLoader())));
    }

    public Van() {
    }

    public Integer getVanId() {
        return vanId;
    }

    public void setVanId(Integer vanId) {
        this.vanId = vanId;
    }

    public String getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(String shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public String getVanRegNo() {
        return vanRegNo;
    }

    public void setVanRegNo(String vanRegNo) {
        this.vanRegNo = vanRegNo;
    }

    public String getVanModel() {
        return vanModel;
    }

    public void setVanModel(String vanRegNo) {
        this.vanModel = vanModel;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(vanId);
        dest.writeValue(shipmentDate);
        dest.writeValue(vanModel);
        dest.writeValue(vanRegNo);
    }

    public int describeContents() {
        return 0;
    }

}