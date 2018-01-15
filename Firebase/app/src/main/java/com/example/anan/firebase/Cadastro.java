package com.example.anan.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by anan on 14/01/18.
 */

public class Cadastro extends AppCompatActivity{
    private EditText edtEmail, edtSenha;
    private Button btnRegistrar, btnVoltar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        initComponentes();
        eventoClick();
    }

    private void eventoClick() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                String senha = edtEmail.getText().toString().trim();
                criarUsuario(email, senha);
            }
        });
    }

    private void criarUsuario(String email, String senha) {
        firebaseAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(Cadastro.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    alert("Usuário cadastrado com sucesso");
                                    Intent intent = new Intent(Cadastro.this,Perfil.class);
                                    startActivity(intent );
                                }else{
                                    alert("Erro ao cadastro");
                                }
                        }
        });
    }

    private void alert(String msg){
        Toast.makeText(Cadastro.this,msg,Toast.LENGTH_SHORT).show();
    }

    private void initComponentes() {
        edtEmail = findViewById(R.id.edtEmailCad);
        edtSenha = findViewById(R.id.edtSenhaCad);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVoltar = findViewById(R.id.btnVoltar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = Conexao.getFirebaseAuth();
    }
}
