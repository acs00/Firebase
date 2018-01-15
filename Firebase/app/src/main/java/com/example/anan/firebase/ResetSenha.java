package com.example.anan.firebase;

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
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by anan on 14/01/18.
 */

public class ResetSenha extends AppCompatActivity {
    private EditText edtEmail;
    private Button btnResetarSenha;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);
        initComponentes();
        eventoClick();
    }

    private void eventoClick() {
        btnResetarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString().trim();
                resetSenha(email);
            }
        });
    }

    private void resetSenha(String email) {
        firebaseAuth.sendPasswordResetEmail(email).
                addOnCompleteListener(ResetSenha.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            alert("E-mail encaminhado");
                            finish();
                        }else{
                            alert("E-mail não encontrado");
                        }
                    }
                });
    }

    private void alert(String s) {
        Toast.makeText(ResetSenha.this,s,Toast.LENGTH_SHORT).show();
    }

    private void initComponentes(){
        edtEmail = findViewById(R.id.edtEmailReset);
        btnResetarSenha = findViewById(R.id.btnReset);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = Conexao.getFirebaseAuth();
    }
}
