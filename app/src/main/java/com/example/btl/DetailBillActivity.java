package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.adapter.RecycleViewAdapterCart;
import com.example.btl.adapter.RecycleViewAdapterDetailBill;
import com.example.btl.model.ProductCart;

import java.util.ArrayList;
import java.util.List;

public class DetailBillActivity extends AppCompatActivity {
    
    private RecyclerView recyclerView;
    
    private ImageView back;

    private TextView tong;

    float total = 0;

    List<ProductCart> productCarts = new ArrayList<>();

    RecycleViewAdapterDetailBill adapterDetailBill;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bill);
        recyclerView = findViewById(R.id.recycleView);
        back = findViewById(R.id.back);
        tong = findViewById(R.id.tong);
        Intent intent = getIntent();
        productCarts = (List<ProductCart>)intent.getSerializableExtra("listBill");

        for (ProductCart p: productCarts){
            total += (p.getProductPrice()*p.getQuantity()) ;
        }
        tong.setText(total+ "$");

        LinearLayoutManager manager = new LinearLayoutManager(DetailBillActivity.this,RecyclerView.VERTICAL,false);
        adapterDetailBill = new RecycleViewAdapterDetailBill(productCarts,this);
        recyclerView.setLayoutManager(manager);
        adapterDetailBill.setList(productCarts);
        recyclerView.setAdapter(adapterDetailBill);


        setOnClick();
    }

    private void setOnClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}