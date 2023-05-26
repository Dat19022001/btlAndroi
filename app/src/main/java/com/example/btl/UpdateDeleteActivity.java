package com.example.btl;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.btl.api.ApiService;
import com.example.btl.model.Book;
import com.example.btl.model.BookDetail;
import com.example.btl.model.Cat;
import com.google.android.gms.common.util.ArrayUtils;

import java.util.Arrays;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteActivity extends AppCompatActivity {

    ImageView image,back;
    EditText name,author,des,price,page,date;

    Spinner spinner;

    Button update,delete;

    Book book ;

    String [] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        image = findViewById(R.id.Img);
        name = findViewById(R.id.Name);
        author = findViewById(R.id.Author);
        des = findViewById(R.id.Des);
        price = findViewById(R.id.Price);
        page = findViewById(R.id.Page);
        date = findViewById(R.id.Date);
        spinner = findViewById(R.id.Spinner);
        update = findViewById(R.id.Update);
        delete = findViewById(R.id.Delete);
        back = findViewById(R.id.back);

        arr = getResources().getStringArray(R.array.category);

        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,arr));

        setOnClick();
        getData();
    }

    private void setOnClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteBook();
            }
        });
    }

    private void setData(){
        name.setText(book.getName());
        author.setText(book.getAuthor());
        des.setText(book.getDescription());
        price.setText(String.valueOf(book.getPrice()));
        page.setText(String.valueOf(book.getPageNumber()));
        date.setText(book.getDatePublish());
        spinner.setSelection(Arrays.asList(arr).indexOf(book.getCat().getName()));
        Glide.with(this).load(book.getImgUrl()).into(image);
    }

    private void getData(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("bookId");
        ApiService.apiService.getBookId(id).enqueue(new Callback<BookDetail>() {
            @Override
            public void onResponse(Call<BookDetail> call, Response<BookDetail> response) {
                BookDetail bookDetail = response.body();
                if(bookDetail != null && bookDetail.getStatus() == 200){
                    book = bookDetail.getBook();
                    if(book !=null) {
                        setData();
                    }else {
                        Toast.makeText(UpdateDeleteActivity.this, "NUll", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(UpdateDeleteActivity.this, "NUll", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookDetail> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Fail to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void update(){
        float bPrice = Float.parseFloat(price.getText().toString());
        int bPage = Integer.parseInt(page.getText().toString());
        String bName = name.getText().toString();
        String bAuthor = author.getText().toString();
        String bDate = date.getText().toString();
        String bDes = des.getText().toString();

        book.setName(bName);
        book.setAuthor(bAuthor);
        book.setDatePublish(bDate);
        book.setPrice(bPrice);
        book.setPageNumber(bPage);
        book.setDescription(bDes);
        book.setCat(new Cat("abc",spinner.getSelectedItem().toString()));

        ApiService.apiService.updateBook(book).enqueue(new Callback<BookDetail>() {
            @Override
            public void onResponse(Call<BookDetail> call, Response<BookDetail> response) {
                BookDetail bookDetail = response.body();
                if(bookDetail != null && bookDetail.getStatus() == 200){
                    Toast.makeText(UpdateDeleteActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(UpdateDeleteActivity.this, "Update fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookDetail> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this, "Fail to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteBook(){
        AlertDialog.Builder builder =  new AlertDialog.Builder(UpdateDeleteActivity.this);
        builder.setTitle("Thông báo xóa");
        builder.setMessage("Bạn có muốn xóa sản phẩm này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiService.apiService.deleteBook(book.getId()).enqueue(new Callback<BookDetail>() {
                    @Override
                    public void onResponse(Call<BookDetail> call, Response<BookDetail> response) {
                        BookDetail bookDetail = response.body();
                        if(bookDetail != null && bookDetail.getStatus() == 200){
                            Toast.makeText(UpdateDeleteActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(UpdateDeleteActivity.this, "Delete fail", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookDetail> call, Throwable t) {
                        Toast.makeText(UpdateDeleteActivity.this, "Delete fail", Toast.LENGTH_SHORT).show();
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
}