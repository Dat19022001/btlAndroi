package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.R;
import com.example.btl.model.Bill;
import com.example.btl.model.ProductCart;

import java.util.List;

public class RecycleViewAdapterBill extends RecyclerView.Adapter<RecycleViewAdapterBill.HomeViewHolder> {

    List <Bill> list;

    private Context context;

    public void setList(List<Bill> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public RecycleViewAdapterBill(List<Bill> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewAdapterBill.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bil,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterBill.HomeViewHolder holder, int position) {
        Bill bill = list.get(position);
        holder.phone.setText(bill.getPhone());
        holder.address.setText(bill.getAddress());
        holder.date.setText(bill.getDate());
        holder.tong.setText(bill.getTong() + "$");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView phone,address,date,tong;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            phone = itemView.findViewById(R.id.Phone);
            address= itemView.findViewById(R.id.Address);
            date = itemView.findViewById(R.id.Date);
            tong = itemView.findViewById(R.id.Tong);
        }
    }

}
