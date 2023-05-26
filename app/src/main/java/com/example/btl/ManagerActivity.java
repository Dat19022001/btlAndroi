package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.btl.adapter.RecycleViewAdapterManager;
import com.example.btl.api.ApiService;
import com.example.btl.model.Book;
import com.example.btl.model.BookAll;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManagerActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    Spinner spinner;

    ImageView back;

    FloatingActionButton fab;

    List<Book> list = new ArrayList<>();

    RecycleViewAdapterManager adapterManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        recyclerView = findViewById(R.id.recycleView);
        spinner = findViewById(R.id.Spinner);
        back = findViewById(R.id.back);
        fab = findViewById(R.id.fab);


        String [] arr = getResources().getStringArray(R.array.category);
        String [] arr1 = new String[arr.length+1];
        arr1[0] = "All";
        for(int i = 0;i< arr.length;i++){
            arr1[i+1] = arr[i];
        }
        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,arr1));

        setOnCallApi();
        setOnclick();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        adapterManager = new RecycleViewAdapterManager(list,this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterManager);



    }

    private void setOnclick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this,AddBookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setOnCallApi() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String cate = spinner.getItemAtPosition(position).toString();
                if(!cate.equalsIgnoreCase("All")){
                    ApiService.apiService.getBookCat(cate,10).enqueue(new Callback<BookAll>() {
                        @Override
                        public void onResponse(Call<BookAll> call, Response<BookAll> response) {
                            BookAll bookCat = response.body();
                            if (bookCat != null && bookCat.getStatus() == 200){
                                list = bookCat.getList();
                                adapterManager.setList(list);
                            }
                            else {
                                Toast.makeText(ManagerActivity.this,"Thất bại",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BookAll> call, Throwable t) {
                            Toast.makeText(ManagerActivity.this,"Thất bại",Toast.LENGTH_LONG).show();
                        }
                    });
                }else {
                    ApiService.apiService.getAllBook().enqueue(new Callback<BookAll>() {
                        @Override
                        public void onResponse(Call<BookAll> call, Response<BookAll> response) {
                            BookAll bookAll = response.body();
                            if (bookAll != null && bookAll.getStatus() == 200){
                                list = bookAll.getList();
                                adapterManager.setList(list);
                            }
                            else {
                                Toast.makeText(ManagerActivity.this,"Thất bại",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<BookAll> call, Throwable t) {
                            Toast.makeText(ManagerActivity.this,"Thất bại",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void callApi(){
        ApiService.apiService.getAllBook().enqueue(new Callback<BookAll>() {
            @Override
            public void onResponse(Call<BookAll> call, Response<BookAll> response) {
                BookAll bookAll = response.body();
                if (bookAll != null && bookAll.getStatus() == 200){
                    list = bookAll.getList();
                    adapterManager.setList(list);
                }
                else {
                    Toast.makeText(ManagerActivity.this,"Thất bại",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BookAll> call, Throwable t) {
                Toast.makeText(ManagerActivity.this,"Thất bại",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        callApi();
    }
}