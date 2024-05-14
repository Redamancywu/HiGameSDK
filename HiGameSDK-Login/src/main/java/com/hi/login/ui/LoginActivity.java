package com.hi.login.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.hi.higamesdk_login.R;
import com.hi.higamesdk_login.databinding.ActivityLoginBinding;

public class LoginActivity extends Activity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    public void GoogleLogin(){
        binding.bthGoogle.setOnClickListener(view->{

        });
    }
}