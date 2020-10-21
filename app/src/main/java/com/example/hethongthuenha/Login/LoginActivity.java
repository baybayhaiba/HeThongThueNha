package com.example.hethongthuenha.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hethongthuenha.R;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {
    private EditText editEmail, editPassword;
    private Button btnLogin;
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
        progressBarLogin = findViewById(R.id.progressBarLogin);
        loginPresenter = new LoginPresenter(this);

        getSupportActionBar().setElevation(0);

        btnLogin.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                HandlingLogin();
            }
        });
    }

    private void HandlingLogin() {
        loginPresenter.LoginWithEmail(editEmail.getText().toString(), editPassword.getText().toString());
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        loginPresenter.HaveAccount();
    }
}