package com.app.emapyc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mendez on 09/05/2017.
 */

public class BD extends SQLiteOpenHelper {



    public BD(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//si no existe lla base de datos la crea y ejecuta los siquientes comandos
        db.execSQL("create table usuario(codigo text ,estado text,nombre text,calle text,categoria text,medidor text)");
        db.execSQL("create table mensajes(id integer primary key autoincrement,mensaje text ,fecha text,hora text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
