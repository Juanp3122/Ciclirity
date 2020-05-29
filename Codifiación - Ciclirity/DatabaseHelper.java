package com.example.dbprueba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="register.db";
    public static final String TABLE_NAME ="registerUser";
    public static final String TABLE_NAME2 ="registerUsers";
    public static final String TABLE_NAME3 ="bicicletas";
    public static final String TABLE_NAME4 ="usuarios_bicicletas";
    public static final String TABLE_NAME5 ="soloUsuario";
    public static final String COL_1 ="ID";
    public static final String COL_2 ="username";
    public static final String COL_3 ="password";
    public static final String COL_4 ="name";
    public static final String COL_5 ="tipo";
    public static final String COL_6 ="serial";
    public static final String COL_7 ="phone";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 12);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {



        String CREATE_USERS_TABLE = "CREATE TABLE "+TABLE_NAME2+"(KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,KEY_USER TEXT,KEY_PHONE TEXT);";
        sqLiteDatabase.execSQL("CREATE TABLE registerUser (ID INTEGER PRIMARY  KEY AUTOINCREMENT, username TEXT, password TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME5+" (username TEXT PRIMARY KEY)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME3 + " (ID INTEGER PRIMARY  KEY AUTOINCREMENT, " + COL_5 + " TEXT, " + COL_6 + " TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME4+"(KEY_SERIAL TEXT , KEY_CORREO TEXT, FOREIGN KEY(KEY_CORREO) REFERENCES soloUsuario(username), FOREIGN KEY(KEY_SERIAL) REFERENCES biciletas("+COL_6+"))");

        sqLiteDatabase.execSQL(CREATE_USERS_TABLE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        onCreate(db);
    }

    public long addUser(String user, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        contentValues.put("password",password);
        long res = db.insert("registerUser",null,contentValues);
        db.close();
        return  res;
    }

    public long addSoloUser(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",user);
        long res = db.insert("soloUsuario",null,contentValues);
        db.close();
        return  res;
    }

    public long addUser_Bici(String correo, String serial){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("KEY_CORREO",correo);
        contentValues.put("KEY_SERIAL",serial);
        long res = db.insert("usuarios_bicicletas",null,contentValues);
        db.close();
        return  res;
    }

    public long addUserInfo(String name, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("KEY_USER",phone);
        contentValues.put("KEY_PHONE",name);
        long res = db.insert("registerUsers",null,contentValues);
        db.close();
        return  res;
    }

    public long addCicla(String tipo, String serial){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tipo",tipo);
        contentValues.put("serial",serial);
        long res = db.insert("bicicletas",null,contentValues);
        db.close();
        return  res;
    }



    public boolean checkUser(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public boolean checkCicla(String tipo, String serial){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_5 + "=?" + " and " + COL_6 + "=?";
        String[] selectionArgs = { tipo, serial };
        Cursor cursor = db.query(TABLE_NAME3,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public boolean checkUserInfo(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public Cursor alldata (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from registerUser",null);
        return cursor;
    }
    public Cursor alldata2 (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from registerUsers",null);
        return cursor;
    }

    public Cursor alldata3 (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from usuarios_bicicletas",null);
        return cursor;
    }

    public Cursor alldata4 (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from bicicletas",null);
        return cursor;
    }
    public Cursor alldata6 (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from soloUsuario",null);
        return cursor;
    }


}
