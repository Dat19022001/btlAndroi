package com.example.btl.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.adapter.RecycleViewAdapterBill;
import com.example.btl.adapter.RecycleViewAdapterHome;
import com.example.btl.api.ApiService;
import com.example.btl.model.Bill;
import com.example.btl.model.Book;
import com.example.btl.model.Cat;
import com.example.btl.model.GetBill;
import com.example.btl.model.ProductCart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBill extends Fragment {

    EditText dFrom;
    Button search;

    RecyclerView recyclerView;

    List<Bill> list = new ArrayList<>();

    RecycleViewAdapterBill adapterBill;

    DatabaseReference reference;
    String UserId;
    FirebaseUser user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dFrom = view.findViewById(R.id.From);
        search = view.findViewById(R.id.Search);


        recyclerView = view.findViewById(R.id.recycleView);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);

        adapterBill =new RecycleViewAdapterBill(list,getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterBill);
        callApi();
        setOnclick();

    }

    private void callApi() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        UserId = user.getUid();

        ApiService.apiService.getOrder(UserId).enqueue(new Callback<GetBill>() {
            @Override
            public void onResponse(Call<GetBill> call, Response<GetBill> response) {
                GetBill bill = response.body();
                if(bill != null && bill.getStatus() == 200){
                    list = bill.getList();
                    adapterBill.setList(list);
                }
            }

            @Override
            public void onFailure(Call<GetBill> call, Throwable t) {
                Toast.makeText(getContext(),"call api that bai",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setOnclick() {
        dFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date="";
                        date= d+"-"+(m+1)+"-"+y;
                        dFrom.setText(date);
                    }
                },year,month,day);
                dialog.show();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = dFrom.getText().toString().trim();
                ApiService.apiService.getOrderbyDate(date).enqueue(new Callback<GetBill>() {
                    @Override
                    public void onResponse(Call<GetBill> call, Response<GetBill> response) {
                        GetBill bill = response.body();
                        if(bill != null && bill.getStatus() == 200){
                            list = bill.getList();
                            adapterBill.setList(list);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetBill> call, Throwable t) {
                        Toast.makeText(getContext(),"call api that bai",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        callApi();
    }
}
