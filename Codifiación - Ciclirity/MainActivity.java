package com.example.dbprueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button crear;
    Button iniciarSesion;

    EditText Email;
    EditText Pass;

    String email = "";
    String contra ="";



    DatabaseHelper db;

    @Override
    protected void  onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        Email = findViewById(R.id.editText);
        Pass = findViewById(R.id.editText2);
        iniciarSesion = findViewById(R.id.button);



        //Se toma iniciar sesion y se valida con firebase/sqlite
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = Email.getText().toString();
                contra = Pass.getText().toString();
                Boolean respueta = db.checkUser(email,contra);

                if(!email.isEmpty()&&!contra.isEmpty()&&respueta == true){
                    Toast.makeText(MainActivity.this, "Inicio de sesion exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, Principal.class));
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Usuario / contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Se retorna la pantalla de registrar usuario
        crear = findViewById(R.id.crearCuenta);
        crear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                startActivity(new Intent(MainActivity.this,formulario.class));
                finish();
            }


        });
    }

}
