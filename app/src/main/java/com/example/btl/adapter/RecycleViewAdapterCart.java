package com.example.btl.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.PaymentActivity;
import com.example.btl.R;

import com.example.btl.api.ApiService;
import com.example.btl.model.GetCart;
import com.example.btl.model.ProductCart;
import com.example.btl.model.UpdateCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecycleViewAdapterCart extends RecyclerView.Adapter<RecycleViewAdapterCart.HomeViewHolder> {

    List <ProductCart> list;

    private Context context;

    public void setList(List<ProductCart> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public RecycleViewAdapterCart(List<ProductCart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewAdapterCart.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterCart.HomeViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ProductCart productCart = list.get(position);
        Glide.with(context).load(productCart.getProductImg()).into(holder.imageView);
        holder.name.setText(productCart.getProductName());
        holder.price.setText(productCart.getProductPrice()+"$");
        holder.quantity.setText(String.valueOf(productCart.getQuantity()));
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity1 = Integer.parseInt(holder.quantity.getText().toString());
                if(quantity1 > 1){
                    quantity1 --;
                }
                UpdateCart update = new UpdateCart(uid,"minus",productCart.getProductId());
                updateCart(update);
                holder.quantity.setText(String.valueOf(quantity1));
            }
        });
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity1 = Integer.parseInt(holder.quantity.getText().toString());
                quantity1 ++;
                UpdateCart update = new UpdateCart(uid,"add",productCart.getProductId());
                updateCart(update);
                holder.quantity.setText(String.valueOf(quantity1));
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =  new AlertDialog.Builder(context);
                builder.setTitle("Thông báo xóa");
                builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiService.apiService.deleteProduct(uid,productCart.getProductId()).enqueue(new Callback<GetCart>() {
                            @Override
                            public void onResponse(Call<GetCart> call, Response<GetCart> response) {
                                GetCart getCart = response.body();
                                if(getCart != null && getCart.getStatus() == 200){
                                    Toast.makeText(context,"Xóa thành công",Toast.LENGTH_LONG).show();
                                    list.remove(position);
                                    setList(list);
                                }else {
                                    Toast.makeText(context,"Xóa thất bại",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<GetCart> call, Throwable t) {
                                Toast.makeText(context,"Xóa thất bại",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView,delete;
        private TextView name,price,quantity;

        private ImageView minus,plus;


        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
            delete = itemView.findViewById(R.id.delete);

        }
    }

    private void updateCart(UpdateCart update){
        ApiService.apiService.updateCart(update).enqueue(new Callback<GetCart>() {
            @Override
            public void onResponse(Call<GetCart> call, Response<GetCart> response) {
                if(response.body() == null){
                    Toast.makeText(context,"Fail",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetCart> call, Throwable t) {
                Toast.makeText(context,"Fail",Toast.LENGTH_LONG).show();
            }
        });
    }

}
