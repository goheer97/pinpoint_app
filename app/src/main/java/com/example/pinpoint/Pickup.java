package com.example.pinpoint;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pickup implements Serializable, Parcelable
{

    @SerializedName("child_pickup_id")
    @Expose
    private Integer childPickupId;
    @SerializedName("customer_child_id")
    @Expose
    private Integer customerChildId;
    @SerializedName("child_pickup_time")
    @Expose
    private String childPickupTime;
    @SerializedName("child_drop_off_time")
    @Expose
    private String childDropOffTime;
    @SerializedName("trip_id")
    @Expose
    private Object tripId;
    public final static Parcelable.Creator<Pickup> CREATOR = new Creator<Pickup>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Pickup createFromParcel(Parcel in) {
            return new Pickup(in);
        }

        public Pickup[] newArray(int size) {
            return (new Pickup[size]);
        }

    }
            ;
    private final static long serialVersionUID = -9096855087926372091L;

    protected Pickup(Parcel in) {
        this.childPickupId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customerChildId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.childPickupTime = ((String) in.readValue((String.class.getClassLoader())));
        this.childDropOffTime = ((String) in.readValue((String.class.getClassLoader())));
        this.tripId = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public Pickup() {
    }

    public Integer getChildPickupId() {
        return childPickupId;
    }

    public void setChildPickupId(Integer childPickupId) {
        this.childPickupId = childPickupId;
    }

    public Integer getCustomerChildId() {
        return customerChildId;
    }

    public void setCustomerChildId(Integer customerChildId) {
        this.customerChildId = customerChildId;
    }

    public String getChildPickupTime() {
        return childPickupTime;
    }

    public void setChildPickupTime(String childPickupTime) {
        this.childPickupTime = childPickupTime;
    }

    public String getChildDropOffTime() {
        return childDropOffTime;
    }

    public void setChildDropOffTime(String childDropOffTime) {
        this.childDropOffTime = childDropOffTime;
    }

    public Object getTripId() {
        return tripId;
    }

    public void setTripId(Object tripId) {
        this.tripId = tripId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(childPickupId);
        dest.writeValue(customerChildId);
        dest.writeValue(childPickupTime);
        dest.writeValue(childDropOffTime);
        dest.writeValue(tripId);
    }

    public int describeContents() {
        return 0;
    }

}