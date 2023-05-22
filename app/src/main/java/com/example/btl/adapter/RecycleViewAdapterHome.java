package com.example.btl.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.DetailActivity;
import com.example.btl.R;
import com.example.btl.model.Book;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterHome extends RecyclerView.Adapter<RecycleViewAdapterHome.HomeViewHolder> {

    private List<Book> list;

    private Context context;

    public RecycleViewAdapterHome(List<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }

    private ItemListener itemListener;

    public void setList(List<Book> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public Book getBook(int position){
        return list.get(position);
    }

    public RecycleViewAdapterHome() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecycleViewAdapterHome.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterHome.HomeViewHolder holder, int position) {
        Book book = list.get(position);
        Glide.with(context).load(book.getImgUrl()).into(holder.imageView);
        holder.name.setText(book.getName());
        holder.rate.setText(String.valueOf(book.getRate()));
        holder.price.setText(String.valueOf(book.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("id",book.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface ItemListener {

    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView name,rate,price;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            rate = itemView.findViewById(R.id.rate);
            price = itemView.findViewById(R.id.price);
        }
    }
}
