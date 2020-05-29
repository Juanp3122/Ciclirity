package com.example.dbprueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dbprueba.utilidades.utilidades;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Bicicletas extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    Button registar;
    EditText nombre,serial,correo;
    String tipo=" ",serialBici= " ",username = " ";

    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicicletas);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();


        registar = findViewById(R.id.registrarBicicleta);
        nombre =(EditText) findViewById(R.id.tipoBici);
        serial =(EditText) findViewById(R.id.idBici);
        correo = (EditText) findViewById(R.id.correoId);



        db =new  DatabaseHelper(this);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo = nombre.getText().toString().trim();
                serialBici = serial.getText().toString().trim();
                username = correo.getText().toString().trim();

                Boolean veriUsuario = checkUserInfos(username);

                if(veriUsuario == true){


                    db.addCicla(tipo,serialBici);



                    Cursor cursor = db.alldata();
                    if(cursor.getCount()==0){
                        Toast.makeText(Bicicletas.this, "No hay datos", Toast.LENGTH_SHORT).show();
                    }else{
                        while(cursor.moveToNext()){
                            db.addUser_Bici(username,serialBici);
                            Toast.makeText(Bicicletas.this, "Se ha registrado satisfactoriamente", Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{
                    Toast.makeText(Bicicletas.this, "Hubo un error al registrar los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public boolean checkUserInfos(String username){
        String[] columns = { DatabaseHelper.COL_1 };
        SQLiteDatabase dbs = db.getReadableDatabase();
        String selection = DatabaseHelper.COL_2 + "=?" ;
        String[] selectionArgs = { username };
        Cursor cursor = dbs.query(DatabaseHelper.TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();

        cursor.close();
        dbs.close();

        if(count>0)
            return  true;
        else
            return  false;
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                mAuth.signOut();
                Toast.makeText(this, "Te has desconectado correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Bicicletas.this, MainActivity.class));
                finish();
                break;
            case R.id.nav_home:
                startActivity(new Intent(Bicicletas.this, Principal.class));
                finish();
                break;
            case R.id.nav_profile:
                startActivity(new Intent(Bicicletas.this, perfilusuario.class));
                finish();
                break;
        }
        return true;
    }
}
