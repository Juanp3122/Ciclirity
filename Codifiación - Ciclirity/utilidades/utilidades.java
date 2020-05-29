package com.example.dbprueba.utilidades;

public class utilidades {

    public static final String TABLA_CICLAS = "bicicletas";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_SERIAL = "serial";
    public static final String CAMPO_NOMBRE = "nombre";

    public static final String CREAR_TABLA_BICICLETA = "CREATE TABLE"+TABLA_CICLAS+" ("+CAMPO_ID+" " +
            "INTEGER, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_SERIAL+" TEXT)";


}
