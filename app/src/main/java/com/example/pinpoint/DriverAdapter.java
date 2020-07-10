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

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {
    private Driver[] data;
    Context _context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public  DriverAdapter(Context context, Driver[] data){
        this._context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_driver, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Adapter: ", "Generated");
//
//                Intent intent = new Intent(_context, Form.class);
//                _context.startActivity(intent);
//            }
//        });
        return new DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {


        int id = data[position].getDriverId();
        String name = data[position].getDriverName();
        String number = data[position].getDriverContactNo();
        String address = data[position].getDriverAddress();
        String cnic = data[position].getDriverCnic();

        holder.custId.setText("Id: " + id);
        holder.custName.setText("Name: "+ name);
        holder.custNumber.setText("Number: "+ number);
        holder.custAddress.setText("Address: "+ address);
        holder.custCnic.setText("Cnic: " + cnic);


    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class DriverViewHolder extends RecyclerView.ViewHolder{
        TextView custName;
        TextView custId;
        TextView custNumber;
        TextView custAddress;
        TextView custCnic;
        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            custName = itemView.findViewById(R.id.custName);
            custId = itemView.findViewById(R.id.custId);
            custAddress = itemView.findViewById(R.id.custAddress);
            custNumber = itemView.findViewById(R.id.custNumber);
            custCnic = itemView.findViewById(R.id.custCnic);

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
