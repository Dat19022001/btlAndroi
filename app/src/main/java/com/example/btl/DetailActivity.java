package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.btl.api.ApiService;
import com.example.btl.model.AddCart;
import com.example.btl.model.Book;
import com.example.btl.model.BookDetail;
import com.example.btl.model.GetCart;
import com.example.btl.model.ProductCart;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    
    ImageView back,image;

    TextView name, author, date, price,pageNumber,des;

    RatingBar ratingBar;

    Button addCart;
    String id;

    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        back = findViewById(R.id.back);
        image = findViewById(R.id.image);
        name = findViewById(R.id.name);
        author = findViewById(R.id.author);
        date = findViewById(R.id.date);
        price = findViewById(R.id.price);
        pageNumber = findViewById(R.id.page);
        des = findViewById(R.id.des);
        ratingBar = findViewById(R.id.rate);
        addCart = findViewById(R.id.add);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        callApi();

        setOnClick();
    }



    private void callApi() {
        ApiService.apiService.getBookId(id).enqueue(new Callback<BookDetail>() {
            @Override
            public void onResponse(Call<BookDetail> call, Response<BookDetail> response) {
                BookDetail bookDetail = response.body();
                Log.d("detail3", bookDetail.getBook().toString());
                if(bookDetail != null && bookDetail.getStatus() == 200){
                    book = bookDetail.getBook();
                    setView();
                }
            }

            @Override
            public void onFailure(Call<BookDetail> call, Throwable t) {
                Toast.makeText(DetailActivity.this,"Call api that bai", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setView() {
        Glide.with(this).load(book.getImgUrl()).into(image);
        name.setText("Name: " + book.getName());
        author.setText("Author: "+ book.getAuthor());
        date.setText("Date: "+ book.getDatePublish());
        price.setText("Price: "+ book.getPrice() + "$");
        pageNumber.setText("PageNumber: "+ book.getPageNumber());
        des.setText(book.getDescription());
        ratingBar.setRating(book.getRate());
    }

    private void setOnClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart();

            }
        });
    }

    private void addCart() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ProductCart productCart = new ProductCart(book.getId(),book.getName(),book.getImgUrl(),
                (int) Float.parseFloat(String.valueOf(book.getPrice())),
                1);
        AddCart addCart1 = new AddCart(uid,productCart);
        ApiService.apiService.addCart(addCart1).enqueue(new Callback<GetCart>() {
            @Override
            public void onResponse(Call<GetCart> call, Response<GetCart> response) {
                GetCart getCart = response.body();
                if(getCart != null && getCart.getStatus() == 200){
                    Toast.makeText(DetailActivity.this,"Thêm vào thành cong",Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(DetailActivity.this,"thêm vào thất bại",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetCart> call, Throwable t) {
                Toast.makeText(DetailActivity.this,"Lỗi",Toast.LENGTH_LONG).show();
            }
        });
    }
}