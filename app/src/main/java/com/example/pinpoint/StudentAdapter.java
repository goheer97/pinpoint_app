package com.example.pinpoint;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private Student[] data;
    Context _context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public  StudentAdapter(Context context, Student[] data){
        this._context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_student, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Adapter: ", "Generated");
//
//                Intent intent = new Intent(_context, Form.class);
//                _context.startActivity(intent);
//            }
//        });
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {


        int id = data[position].getCustomerChildId();
        String name = data[position].getChildName();
        String schoolId = data[position].getSchoolId();
        String createdDate = data[position].getChildCreated();
        String parentId = data[position].getCustomerId() + "";


        holder.custId.setText("Id: " + id);
        holder.custName.setText("Name: "+ name);
        holder.schoolId.setText("Number: "+ schoolId);
        holder.createdDate.setText("Email: "+ createdDate);
        holder.parentId.setText("Parent ID: "+ parentId);


    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView custName;
        TextView custId;
        TextView schoolId;
        TextView createdDate;
        TextView parentId;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            custName = itemView.findViewById(R.id.custName);
            custId = itemView.findViewById(R.id.custId);
            schoolId = itemView.findViewById(R.id.schoolID);
            createdDate = itemView.findViewById(R.id.createdDate);
            parentId = itemView.findViewById(R.id.parentId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }
}
