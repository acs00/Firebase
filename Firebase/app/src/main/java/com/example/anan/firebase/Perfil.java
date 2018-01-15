package com.example.anan.firebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by anan on 14/01/18.
 */

public class Perfil extends AppCompatActivity{
    private TextView txtEmail, txtId;
    private Button btnLogout;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        initComponentes();
        eventoClick();
    }

    private void eventoClick() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conexao.logout();
                finish();
            }
        });
    }

    private void initComponentes() {
        txtEmail = findViewById(R.id.txtEmailPerfil);
        txtId = findViewById(R.id.txtIdPerfil);
        btnLogout = findViewById(R.id.btnLogout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = Conexao.getFirebaseAuth();
        firebaseUser = Conexao.getFirebaseUser();
        verificarUsuario();
    }

    private void verificarUsuario() {
        if(firebaseUser == null){
            finish();
        }else{
            txtEmail.setText("Email: "+firebaseUser.getEmail());
            txtId.setText("ID: "+firebaseUser.getUid());
        }
    }


}
