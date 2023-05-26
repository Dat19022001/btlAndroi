package com.example.btl.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.btl.LoginActivity;
import com.example.btl.ManagerActivity;
import com.example.btl.R;
import com.example.btl.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class FragmentUser extends Fragment {
    private ImageView avatar;
    private EditText Username,Email,Address,Phone;

    private Button btUpdate,btLogout,btManager;

    DatabaseReference reference;

    FirebaseStorage storage;
    String UserId;
    FirebaseUser user;

    FirebaseDatabase firebaseDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        avatar = view.findViewById(R.id.avatar);
        Username = view.findViewById(R.id.UserName);
        Email = view.findViewById(R.id.Email);
        btLogout = view.findViewById(R.id.Logout);
        btUpdate = view.findViewById(R.id.btUpdate);
        Address = view.findViewById(R.id.Address);
        Phone = view.findViewById(R.id.Phone);
        btManager = view.findViewById(R.id.manager);
        setOnclick();
        showUser();

    }

    private void setOnclick() {
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String currentUserId =user.getUid();
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference myref =firebaseDatabase.getReference("User").child(currentUserId);

                String name = Username.getText().toString().trim();
                String address = Address.getText().toString().trim();
                String phone = Phone.getText().toString().trim();
                String email = Email.getText().toString().trim();

                Map<String,Object> infor = new HashMap<>();
                infor.put("username",name);
                infor.put("address",address);
                infor.put("phone",phone);
                infor.put("email",email);
                myref.updateChildren(infor, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    }
                });
                if(user != null){
                    if(!user.getEmail().equals(email)){
                        user.updateEmail(email)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Cập nhật email thành công!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);

            }
        });
        btManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManagerActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        storage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        if(requestCode == 1){
            if (data.getData() != null){
                Uri uri = data.getData();
                avatar.setImageURI(uri);
                final StorageReference referencetest= storage.getReference().child("profile_picture")
                        .child(FirebaseAuth.getInstance().getUid());

                referencetest.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Đang upload", Toast.LENGTH_SHORT).show();

                        referencetest.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                firebaseDatabase.getReference().child("User").child(FirebaseAuth.getInstance().getUid())
                                        .child("profileImg").setValue(uri.toString());

                                Toast.makeText(getContext(), "Upload thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        }
    }

    // ham lay thong tin user
    private void showUser(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User");
        UserId = user.getUid();

        reference.child(UserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if(userprofile != null){
                    Username.setText(userprofile.getUsername());
                    Email.setText(userprofile.getEmail());
                    Address.setText(userprofile.getAddress());
                    Phone.setText(userprofile.getPhone());
                    Glide.with(getContext()).load(userprofile.getProfileImg()).error(R.drawable.avatar).into(avatar);
                    if(userprofile.isAdmin() == true){
                        btManager.setVisibility(View.VISIBLE);
                    }else {
                        btManager.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Lỗi"+error.getMessage()+"!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
