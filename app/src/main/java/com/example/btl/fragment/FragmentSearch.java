package com.example.btl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
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

public class FragmentSearch extends Fragment {

    private RecyclerView recyclerView;
    List<Book> list = new ArrayList<>();



    private RecycleViewAdapterHome adapterHome;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        SearchView searchView = view.findViewById(R.id.search);



        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);

        adapterHome =new RecycleViewAdapterHome(list,getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterHome);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                ApiService.apiService.getBookByName(s).enqueue(new Callback<BookAll>() {
                    @Override
                    public void onResponse(Call<BookAll> call, Response<BookAll> response) {
                        BookAll bookByName = response.body();
                        if(bookByName != null && bookByName.getStatus() == 200){
                            list = bookByName.getList();
                            adapterHome.setList(list);
                        }
                        else {
                            list.clear();
                            adapterHome.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(Call<BookAll> call, Throwable t) {
                        Toast.makeText(getContext(),"Không có sản phẩm nào",Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }
        });
    }
}
