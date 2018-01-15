package com.example.anan.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anan.firebase.Cadastro;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by anan on 14/01/18.
 */

public class Login extends AppCompatActivity{
    private EditText edtEmail, edtSenha;
    private Button btnLogar,btnNovo;
    private TextView txtResetSenha;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponentes();
        eventoClick();
    }

    private void eventoClick() {
        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, com.example.anan.firebase.Cadastro.class);
                startActivity(intent);
            }
        });
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();
                login(email, senha);
            }
        });
        txtResetSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ResetSenha.class);
                startActivity(intent);
            }
        });
    }

    private void login(String email, String senha) {
        firebaseAuth.signInWithEmailAndPassword(email, senha)
        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(Login.this,Perfil.class);
                    startActivity(intent);
                    finish();
                }else{
                    alert("e-mail ou senha inválidos");
                }
            }
        });
    }

    private void alert(String s) {
        Toast.makeText(Login.this,s,Toast.LENGTH_SHORT).show();
    }

    private void initComponentes() {
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtSenha = findViewById(R.id.edtSenhaLogin);
        btnLogar = findViewById(R.id.btnLogar);
        btnNovo = findViewById(R.id.btnNovo);
        txtResetSenha = findViewById(R.id.txtLembrar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = Conexao.getFirebaseAuth();
    }
}
