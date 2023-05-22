package com.example.btl.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.CatActivity;
import com.example.btl.R;
import com.example.btl.adapter.RecycleViewAdapterHome;
import com.example.btl.api.ApiService;
import com.example.btl.model.Book;
import com.example.btl.model.BookAll;
import com.example.btl.model.Cat;
import com.example.btl.model.Comment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {


    private RecyclerView recyclerViewCt,recyclerViewTt,recyclerViewHot;

    private ImageView next,next1;


    List<Book> list = new ArrayList<>();

    List<Book> listHot = new ArrayList<>();

    List<Book> listComic = new ArrayList<>();

    private RecycleViewAdapterHome adapterCt,adapterHomeHot,adapterHomeComic;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewHot = view.findViewById(R.id.recycleViewHot);
        recyclerViewCt = view.findViewById(R.id.recycleViewCt);
        recyclerViewTt = view.findViewById(R.id.recycleViewTt);
        next = view.findViewById(R.id.next);
        next1 = view.findViewById(R.id.next1);


        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager manager1 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager manager2 = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);

        adapterCt =new RecycleViewAdapterHome(list,getContext());
        adapterHomeHot = new RecycleViewAdapterHome(listHot,getContext());
        adapterHomeComic = new RecycleViewAdapterHome(listComic,getContext());

        // fairy tales(truyen co tich)
        recyclerViewCt.setLayoutManager(manager);
        recyclerViewCt.setAdapter(adapterCt);
        // comic(truyen tranh)
        recyclerViewTt.setLayoutManager(manager1);
        recyclerViewTt.setAdapter(adapterHomeComic);
        // hot
        recyclerViewHot.setLayoutManager(manager2);
        recyclerViewHot.setAdapter(adapterHomeHot);
        callApi();
        setOnClick();
    }

    private void callApi() {
        ApiService.apiService.getBookCat("fairy tales",3).enqueue(new Callback<BookAll>() {
            @Override
            public void onResponse(Call<BookAll> call, Response<BookAll> response) {
                BookAll bookCt = response.body();
                if(bookCt != null  && bookCt.getStatus() == 200){
                    list = bookCt.getList();
                    adapterCt.setList(list);
                }
            }
            @Override
            public void onFailure(Call<BookAll> call, Throwable t) {
                Toast.makeText(getContext(),"Call api that bai",Toast.LENGTH_LONG).show();
            }
        });

        ApiService.apiService.getBookRate().enqueue(new Callback<BookAll>() {
            @Override
            public void onResponse(Call<BookAll> call, Response<BookAll> response) {
                BookAll bookRate = response.body();
                if (bookRate != null && bookRate.getStatus() == 200){
                    listHot = bookRate.getList();
                    adapterHomeHot.setList(listHot);
                }
            }

            @Override
            public void onFailure(Call<BookAll> call, Throwable t) {
                Toast.makeText(getContext(),"Call api that bai",Toast.LENGTH_LONG).show();
            }
        });

        ApiService.apiService.getBookCat("comic",3).enqueue(new Callback<BookAll>() {
            @Override
            public void onResponse(Call<BookAll> call, Response<BookAll> response) {
                BookAll bookComic = response.body();
                if(bookComic != null && bookComic.getStatus() == 200){
                    listComic = bookComic.getList();
                    adapterHomeComic.setList(listComic);
                }
            }

            @Override
            public void onFailure(Call<BookAll> call, Throwable t) {
                Toast.makeText(getContext(),"Call api that bai",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setOnClick() {
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CatActivity.class);
                intent.putExtra("cat","comic");
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CatActivity.class);
                intent.putExtra("cat","fairy tales");
                startActivity(intent);
            }
        });
    }

    // activity đang hoạt đọng thì gọi liên tục
    @Override
    public void onResume() {
        super.onResume();
        callApi();
    }
}
