package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.adapter.RecycleViewAdapterHome;
import com.example.btl.api.ApiService;
import com.example.btl.model.Book;
import com.example.btl.model.BookAll;
import com.example.btl.model.Cat;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatActivity extends AppCompatActivity {

    ImageView back;
    TextView cat;

    RecyclerView recyclerView;

    List<Book> list = new ArrayList<>();

    RecycleViewAdapterHome adapterHome;

    String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);
        back = findViewById(R.id.back);
        cat = findViewById(R.id.Cat);
        recyclerView = findViewById(R.id.recycleView);
        Intent intent = getIntent();
        check = intent.getStringExtra("cat");
        setOnClick();
        setCat();
        callApi();
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        adapterHome = new RecycleViewAdapterHome(list,this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapterHome);
    }

    private void callApi() {
        if(check.equals("fairy tales")){
            ApiService.apiService.getBookCat("fairy tales",10).enqueue(new Callback<BookAll>() {
                @Override
                public void onResponse(Call<BookAll> call, Response<BookAll> response) {
                    BookAll bookCt = response.body();
                    if(bookCt != null  && bookCt.getStatus() == 200){
                        list = bookCt.getList();
                        adapterHome.setList(list);
                    }
                }
                @Override
                public void onFailure(Call<BookAll> call, Throwable t) {
                    Toast.makeText(CatActivity.this,"Call api that bai",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            ApiService.apiService.getBookCat("comic",10).enqueue(new Callback<BookAll>() {
                @Override
                public void onResponse(Call<BookAll> call, Response<BookAll> response) {
                    BookAll bookComic = response.body();
                    if(bookComic != null && bookComic.getStatus() == 200){
                        list = bookComic.getList();
                        adapterHome.setList(list);
                    }
                }

                @Override
                public void onFailure(Call<BookAll> call, Throwable t) {
                    Toast.makeText(CatActivity.this,"Call api that bai",Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void setCat() {
        if(check.equals("fairy tales")){
            cat.setText("Truyện cổ tích");
        }else{
            cat.setText("Truyện tranh");
        }
    }

    private void setOnClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        callApi();
    }
}