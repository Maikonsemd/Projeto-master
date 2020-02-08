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
import com.example.projetosemd.admActivity.HomeAdm;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private Paroquia paroquia;
    private EditText email,senha;
    private Button cadastro;
    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.idEmail);
        senha=findViewById(R.id.idSenha);
        cadastro=findViewById(R.id.idBtnCadastar);

        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.equals("")||senha.equals("")){

                    Toast.makeText(getApplicationContext(),"preencha os campos vazio",Toast.LENGTH_LONG).show();

                }else {
                    paroquia.setEmail(email.getText().toString());
                    paroquia.setSenha(senha.getText().toString());
                    validarlogin();


                }
            }
        });
    }

    private void validarlogin() {
        firebaseAuth=ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseAuth.signInWithEmailAndPassword(paroquia.getEmail(),paroquia.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Abrirtela();

                }else{
                    Toast.makeText(getApplicationContext(),"Senha ou email errado",Toast.LENGTH_LONG).show();
                }

            }

            private void Abrirtela() {

                if(email.equals("semddesenvolvimentos@gmail.com")){
                    Intent intent = new Intent(MainActivity.this, HomeAdm.class);
                    startActivity( intent );

                }else{
                    Intent intent = new Intent(MainActivity.this, RecuperarSenha.class);
                    startActivity( intent );

                }
            }
        });

    }

    public void abrirCadastro( View view ) {

        Intent intent = new Intent(MainActivity.this, Cadastrar.class);
        startActivity( intent );
    }

    public void abrirEsqueceuSenha( View view ) {

        Intent intent = new Intent(MainActivity.this, RecuperarSenha.class);
        startActivity( intent );
    }
}
