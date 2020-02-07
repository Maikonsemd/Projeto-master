package com.example.projetosemd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetosemd.MODEL.Paroquia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastrar extends AppCompatActivity {
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    EditText nome, email, senha, confsenha, cpf;
    Button cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        nome = findViewById(R.id.idEditNome);
        email = findViewById(R.id.idEmail);
        senha = findViewById(R.id.idSenha);
        confsenha = findViewById(R.id.idComSenha);
        cpf = findViewById(R.id.idEditCPF);
        cadastrar = findViewById(R.id.idBtnCadastar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nome.getText().toString().equals("")
                        || email.getText().toString().equals("")
                        || senha.getText().toString().equals("")
                        || confsenha.getText().toString().equals("")
                        || cpf.getText().toString().equals("")
                ) {
                    Toast.makeText(getApplicationContext(), "preencha os campos", Toast.LENGTH_LONG).show();
                } else {
                    Paroquia paroquia = new Paroquia();
                    paroquia.setNome(nome.getText().toString());
                    paroquia.setCpf(cpf.getText().toString());
                    paroquia.setEmail(email.getText().toString());
                    paroquia.setSenha(senha.getText().toString());
                    salvar(paroquia);
                    createUser(email.getText().toString(), senha.getText().toString());

                }
            }
        });

    }

    private void createUser(String email,String senha) {

        firebaseAuth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(Cadastrar.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Cadastrado com Sucesso",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),"Erro cadastro",Toast.LENGTH_LONG).show();

                }

            }
        });

    }


    private boolean salvar(Paroquia paroquia) {
        try {

            DatabaseReference databaseReference = ConfiguracaoFirebase.getFirebase();
            databaseReference.child("usuario").push().setValue(paroquia);
            return true;


        } catch (DatabaseException e) {
            e.printStackTrace();
            return false;
        }

    }

    protected void onStart() {
        super.onStart();
        FirebaseAuth firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }

}




