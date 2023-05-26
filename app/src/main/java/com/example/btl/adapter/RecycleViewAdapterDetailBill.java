package com.example.btl.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.R;
import com.example.btl.api.ApiService;
import com.example.btl.model.GetCart;
import com.example.btl.model.ProductCart;
import com.example.btl.model.UpdateCart;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleViewAdapterDetailBill extends RecyclerView.Adapter<RecycleViewAdapterDetailBill.HomeViewHolder> {

    List <ProductCart> list;

    private Context context;

    public void setList(List<ProductCart> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public RecycleViewAdapterDetailBill(List<ProductCart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewAdapterDetailBill.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterDetailBill.HomeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ProductCart productCart = list.get(position);
        Glide.with(context).load(productCart.getProductImg()).into(holder.imageView);
        holder.name.setText(productCart.getProductName());
        holder.price.setText(productCart.getProductPrice()+"$");
        holder.quantity.setText(String.valueOf(productCart.getQuantity()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name,price,quantity;




        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);

        }
    }

}
