package com.example.hethongthuenha.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hethongthuenha.R;
import com.example.hethongthuenha.Register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private EditText editEmail, editPassword;
    private Button btnLogin,btnRegister;
    private ProgressBar progressBarLogin;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        editEmail = findViewById(R.id.editTextEmail);
        editPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        loginPresenter = new LoginPresenter(this);

        getSupportActionBar().setElevation(0);

        btnLogin.setOnClickListener(v -> HandlingLogin());
        btnRegister.setOnClickListener(v->startActivity(new Intent(LoginActivity.this,RegisterActivity.class)));
    }

    private void HandlingLogin() {
        String email=editEmail.getText().toString();
        String password=editPassword.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            loginPresenter.LoginWithEmail(email, password);
        }
    }

    @Override
    public void loginSuccess() {
        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        progressBarLogin.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void loginFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        progressBarLogin.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void loginPeding() {
        progressBarLogin.setVisibility(android.view.View.VISIBLE);
    }

}