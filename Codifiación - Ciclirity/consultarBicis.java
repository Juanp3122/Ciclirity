package com.example.dbprueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class consultarBicis extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FirebaseAuth mAuth;




    EditText correo;
    TextView vista;
    Button consultas;
    String email ="";

    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_bicis);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();

        databaseHelper = new DatabaseHelper(this);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        correo = findViewById(R.id.correoBusqueda);
        vista = findViewById(R.id.verSerial);

        consultas = findViewById(R.id.consultaDeBicis);




        consultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = correo.getText().toString().trim();


                Boolean respueta = checkUserInfos(email);

                if(respueta == true){

                    Cursor cursor = databaseHelper.alldata3();

                    if(cursor.getCount()==0){
                        Toast.makeText(consultarBicis.this, "No hay datos", Toast.LENGTH_SHORT).show();
                    }else{
                        while(cursor.moveToNext()){
                            startActivity(new Intent(consultarBicis.this, viewList.class));
                            finish();
                        }
                    }
                }

            }
        });




    }


    public boolean checkUserInfos(String username){
        String[] columns = { DatabaseHelper.COL_1 };
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String selection = DatabaseHelper.COL_2 + "=?" ;
        String[] selectionArgs = { username };
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();

        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_logout:
                mAuth.signOut();
                Toast.makeText(this, "Te has desconectado correctamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(consultarBicis.this, MainActivity.class));
                finish();
                break;
            case R.id.nav_home:
                startActivity(new Intent(consultarBicis.this, Principal.class));
                finish();
                break;
            case R.id.nav_profile:
                startActivity(new Intent(consultarBicis.this, perfilusuario.class));
                finish();
                break;

        }
        return true;
    }


}