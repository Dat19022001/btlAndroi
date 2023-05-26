package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.btl.api.ApiService;
import com.example.btl.model.GetOrder;
import com.example.btl.model.Order;
import com.example.btl.model.ProductCart;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {

    EditText Date,Address,Phone;

    ImageView back;
    Button Payment;

    TextView Tong;

    List<ProductCart> productCarts;
    float total = 0;
    String date ="";
    String phone= "";

    String address="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Date = findViewById(R.id.Date);
        Address = findViewById(R.id.Address);
        Phone = findViewById(R.id.Phone);
        Payment = findViewById(R.id.Thanhtoan);
        Tong = findViewById(R.id.tong);
        back = findViewById(R.id.back);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        date = day+"-"+(month+1)+"-"+year;
        Date.setText(date);

        Intent intent = getIntent();

        productCarts = (List<ProductCart>)intent.getSerializableExtra("listSp");

        for (ProductCart p: productCarts){
            total += (p.getProductPrice()*p.getQuantity()) ;
        }
        Tong.setText(total+ "$");



        setOnClick();

    }

    private void setOnClick() {
        Payment.setOnClickListener(new View.OnClickListener() {

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

            @Override
            public void onClick(View view) {

                phone = Phone.getText().toString().trim();
                address = Address.getText().toString().trim();
                Order order1 = new Order(uid,phone,date,address,total,productCarts);
                ApiService.apiService.order(order1).enqueue(new Callback<GetOrder>() {
                    @Override
                    public void onResponse(Call<GetOrder> call, Response<GetOrder> response) {
                        GetOrder getOrder = response.body();
                        if(getOrder != null && getOrder.getStatus() == 200){
                            String link = getOrder.getLink();
                            Log.d("link",link);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(link));
                            startActivity(i);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrder> call, Throwable t) {

                    }
                });
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}