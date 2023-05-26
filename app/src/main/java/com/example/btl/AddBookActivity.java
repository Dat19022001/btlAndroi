package com.example.btl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.btl.api.ApiService;
import com.example.btl.model.Book;
import com.example.btl.model.BookDetail;
import com.example.btl.model.Cat;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookActivity extends AppCompatActivity {

    ImageView image,back;
    EditText name,author,des,price,page,date;

    Spinner spinner;

    Button add;

    private FirebaseStorage storage;

    private Book book = new Book();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        image = findViewById(R.id.Img);
        name = findViewById(R.id.Name);
        author = findViewById(R.id.Author);
        des = findViewById(R.id.Des);
        price = findViewById(R.id.Price);
        page = findViewById(R.id.Page);
        date = findViewById(R.id.Date);
        spinner = findViewById(R.id.Spinner);
        add = findViewById(R.id.Add);
        back = findViewById(R.id.back);

        spinner.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));

        setOnClick();

    }

    private void setOnClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddBookActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date1="";
                        date1= d+"/"+(m+1)+"/"+y;
                        date.setText(date1);
                    }
                },year,month,day);
                dialog.show();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,1);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float bPrice = Float.parseFloat(price.getText().toString());
                int bPage = Integer.parseInt(page.getText().toString());
                String bName = name.getText().toString();
                String bAuthor = author.getText().toString();
                String bDate = date.getText().toString();
                String bDes = des.getText().toString();
                Random r = new Random();
                float rate = Float.parseFloat(fmt(0f + r.nextFloat() * (5f - 0f)));

                book.setName(bName);
                book.setAuthor(bAuthor);
                book.setBuyNumber(1);
                book.setRate(rate);
                book.setDatePublish(bDate);
                book.setPrice(bPrice);
                book.setPageNumber(bPage);
                book.setDescription(bDes);

                ApiService.apiService.addBook(book).enqueue(new Callback<BookDetail>() {
                    @Override
                    public void onResponse(Call<BookDetail> call, Response<BookDetail> response) {
                        BookDetail bookDetail = response.body();
                        if(bookDetail != null && bookDetail.getStatus() == 200){
                            Toast.makeText(AddBookActivity.this,"Thêm sách thành công",Toast.LENGTH_LONG).show();
                            finish();
                        }else {
                            Toast.makeText(AddBookActivity.this,"Thêm sách thất bại",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BookDetail> call, Throwable t) {
                        Toast.makeText(AddBookActivity.this,"Thêm sách thất bại1",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String cate = spinner.getItemAtPosition(position).toString();
                book.setCat(new Cat("abc",cate));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        storage = FirebaseStorage.getInstance();
        if(requestCode ==1){
            if (data.getData() != null) {
                Uri img = data.getData();
                image.setImageURI(img);
                final String randomKey = UUID.randomUUID().toString();
                final StorageReference reference = storage.getReference("Book_Cover").child(randomKey);

                reference.putFile(img).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                book.setImgUrl(uri.toString());
                                book.setId(randomKey);

                                Toast.makeText(AddBookActivity.this, "Tải thành công"
                                        ,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }
    }
    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%.2f",d);
    }
}