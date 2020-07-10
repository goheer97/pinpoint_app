package com.example.pinpoint;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Student implements Serializable, Parcelable
{

    @SerializedName("customer_child_id")
    @Expose
    private Integer customerChildId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("child_name")
    @Expose
    private String childName;
    @SerializedName("school_id")
    @Expose
    private String schoolId;
    @SerializedName("child_created")
    @Expose
    private String childCreated;

    public final static Parcelable.Creator<Student> CREATOR = new Creator<Student>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return (new Student[size]);
        }

    }
            ;
    private final static long serialVersionUID = -5156691665494525659L;

    protected Student(Parcel in) {
        this.customerChildId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.childName = ((String) in.readValue((String.class.getClassLoader())));
        this.childCreated = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Student() {
    }

    public Integer getCustomerChildId() {
        return customerChildId;
    }

    public void setCustomerChildId(Integer customerChildId) {
        this.customerChildId = customerChildId;
    }
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childNamed) {
        this.childName = childName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getChildCreated() {
        return childCreated;
    }

    public void setChildCreated(String childCreated) {
        this.childCreated = childCreated;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(customerId);
        dest.writeValue(customerChildId);
        dest.writeValue(childName);
        dest.writeValue(childCreated);
        dest.writeValue(schoolId);
    }

    public int describeContents() {
        return 0;
    }

}