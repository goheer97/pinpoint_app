package com.example.pinpoint;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.SSLContext;

public class VanAdapter extends RecyclerView.Adapter<VanAdapter.VanViewHolder> {
    private Van[] data;
    Context _context;
    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public  VanAdapter(Context context, Van[] data){
        this._context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public VanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_van, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("Adapter: ", "Generated");
//
//                Intent intent = new Intent(_context, Form.class);
//                _context.startActivity(intent);
//            }
//        });
        return new VanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VanViewHolder holder, int position) {


        String  regNo = data[position].getVanRegNo();
        String vanId = data[position].getVanId() +"";
        String model = data[position].getVanModel();


        holder.vanId.setText("Id: " + vanId);
        holder.model.setText("Name: "+ model);
        holder.regNo.setText("Number: "+ regNo);


    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class VanViewHolder extends RecyclerView.ViewHolder{
        TextView regNo;
        TextView vanId;
        TextView model;
        public VanViewHolder(@NonNull View itemView) {
            super(itemView);
            regNo = itemView.findViewById(R.id.regNo);
            vanId = itemView.findViewById(R.id.vanId);
            model = itemView.findViewById(R.id.model);
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
