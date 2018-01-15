package com.example.anan.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.anan.firebase.model.Pessoa;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private EditText edtNome, edtEmail;
    private ListView listV_dados;

    private List<Pessoa> listPessoa = new ArrayList<Pessoa>();
    private ArrayAdapter<Pessoa> arrayAdapter;

    Pessoa pessoaSelecionada;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNome = findViewById(R.id.editNome);
        edtEmail = findViewById(R.id.editEmail);
        listV_dados = findViewById(R.id.listV_dados);

        initFirebase();
        eventoDataBase();

        listV_dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pessoaSelecionada = (Pessoa)parent.getItemAtPosition(position);
                edtEmail.setText(pessoaSelecionada.getEmail());
                edtNome.setText(pessoaSelecionada.getNome());
            }
        });
    }

    private void eventoDataBase() {
        databaseReference.child("Pessoa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPessoa.clear();
                for (DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Pessoa pessoa = objSnapshot.getValue(Pessoa.class);
                    listPessoa.add(pessoa);
                }
                arrayAdapter = new ArrayAdapter<Pessoa>(MainActivity.this,
                        android.R.layout.simple_list_item_1,listPessoa);
                listV_dados.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Pessoa pessoa = new Pessoa();
        if(id == R.id.menu_novo){
            pessoa = new Pessoa(UUID.randomUUID().toString(),edtNome.getText().toString(),
                    edtEmail.getText().toString());
            databaseReference.child("Pessoa").child(pessoa.getUid()).setValue(pessoa);
        }else if(id == R.id.menu_atualiza){
            pessoa.setUid(pessoaSelecionada.getUid());
            pessoa.setNome(edtNome.getText().toString().trim());
            pessoa.setEmail(edtEmail.getText().toString().trim());
            databaseReference.child("Pessoa").child(pessoa.getUid()).setValue(pessoa);
        }else if(id == R.id.menu_deleta){
            pessoa.setUid(pessoaSelecionada.getUid());
            databaseReference.child("Pessoa").child(pessoa.getUid()).removeValue();
        }
        limparCampos();
        return true;
    }

    private void limparCampos() {
        edtEmail.setText("");
        edtNome.setText("");
    }
}
