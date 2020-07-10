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

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {
    private Customer[] data;
    Context _context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public  CustomerAdapter(Context context, Customer[] data){
        this._context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_customer, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Adapter: ", "Generated");
//
//                Intent intent = new Intent(_context, Form.class);
//                _context.startActivity(intent);
//            }
//        });
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {


            int id = data[position].getCustomerId();
            String name = data[position].getCustomerName();
            String number = data[position].getCustomerNumber();
            String email = data[position].getCustomerEmail();

            holder.custId.setText("Id: " + id);
            holder.custName.setText("Name: "+ name);
            holder.custNumber.setText("Number: "+ number);
            holder.custEmail.setText("Email: "+ email);


    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder{
        TextView custName;
        TextView custId;
        TextView custNumber;
        TextView custEmail;
        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            custName = itemView.findViewById(R.id.custName);
            custId = itemView.findViewById(R.id.custId);
            custEmail = itemView.findViewById(R.id.custEmail);
            custNumber = itemView.findViewById(R.id.custNumber);

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
