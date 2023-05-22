package com.example.btl.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.btl.PaymentActivity;
import com.example.btl.R;
import com.example.btl.adapter.RecycleViewAdapterCart;
import com.example.btl.api.ApiService;
import com.example.btl.model.Cart;
import com.example.btl.model.GetCart;
import com.example.btl.model.ProductCart;
import com.example.btl.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCart extends Fragment {

    TextView Username;

    String UserId;

    Button Payment;
    FirebaseUser user;

    RecyclerView recyclerView;

    DatabaseReference reference;
    List<ProductCart> productCarts = new ArrayList<>();


    private RecycleViewAdapterCart adapterCart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cart,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Username = view.findViewById(R.id.UserName);
        recyclerView = view.findViewById(R.id.recycleView);
        Payment = view.findViewById(R.id.Payment);
        setUserName();
        callApi();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        adapterCart = new RecycleViewAdapterCart(productCarts,getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterCart);
        Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), PaymentActivity.class);
                intent.putExtra("listSp", (Serializable) productCarts);
                startActivity(intent);
            }
        });

    }

    private void callApi() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        UserId = user.getUid();

        ApiService.apiService.getCart(UserId).enqueue(new Callback<GetCart>() {
            @Override
            public void onResponse(Call<GetCart> call, Response<GetCart> response) {
                GetCart cart = response.body();
                if(cart !=null && cart.getStatus() == 200){
                    productCarts = cart.getCart().getProducts();
                    adapterCart.setList(productCarts);
                }
            }

            @Override
            public void onFailure(Call<GetCart> call, Throwable t) {
                Toast.makeText(getContext(),"call api that bai",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUserName() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        UserId = user.getUid();

        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if (userprofile != null) {
                    Username.setText(userprofile.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi" + error.getMessage() + "!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        callApi();
    }
}
