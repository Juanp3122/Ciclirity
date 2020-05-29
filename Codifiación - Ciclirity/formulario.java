package com.example.dbprueba;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class formulario extends AppCompatActivity {



    DatabaseHelper db;
    EditText TextEmail;
    EditText TextPassword;
    EditText confirmarPassword;
    EditText TextNombre;
    EditText TextTelefono;
    Button btnRegistrar;

    String email = "";
    String password = "";
    String confirPassword = "";
    String name = "";
    String phone = "";
    Button yaRegistrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);



        db =new  DatabaseHelper(this);
        TextNombre = (EditText) findViewById(R.id.telefonoTexto);
        TextTelefono =(EditText) findViewById(R.id.nombreTexto);
        TextEmail = (EditText) findViewById(R.id.tCorreo);
        TextPassword =(EditText) findViewById(R.id.tContra);
        btnRegistrar = (Button) findViewById(R.id.registrar);
        confirmarPassword = (EditText) findViewById(R.id.confirmarContra);



        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = TextEmail.getText().toString().trim();
                password = TextPassword.getText().toString().trim();
                name = TextNombre.getText().toString();
                phone = TextTelefono.getText().toString();
                confirPassword = confirmarPassword.getText().toString().trim();

                if (password.equals(confirPassword)){

                    if (!email.isEmpty() && !password.isEmpty() && !name.isEmpty() && !phone.isEmpty()) {

                        long val = db.addUser(email, password);
                        long vale = db.addUserInfo(name, phone);
                        long vali = db.addSoloUser(email);

                        if (vale == -1 || vali == -1) {
                            Toast.makeText(formulario.this, "Hubo un error dentro de la base de datos", Toast.LENGTH_LONG).show();
                        }


                        if (password.length() >= 6) {


                            if (val > 0) {
                                Toast.makeText(formulario.this, "Felicidades te has registrado ", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(formulario.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(formulario.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();

                            }


                        } else {
                            Toast.makeText(formulario.this, "La contraseña esta muy corta", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(formulario.this, "Se debe llenar los campos", Toast.LENGTH_LONG).show();

                    }

            }else{
                    Toast.makeText(formulario.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                }
            }
        });
        yaRegistrado = findViewById(R.id.yaRegistrado);
        yaRegistrado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                startActivity(new Intent(formulario.this,MainActivity.class));
                finish();
            }


        });



    }
}
