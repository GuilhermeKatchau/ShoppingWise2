package com.example.shoppingwise2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.shoppingwise2.Produto;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "produtos   .db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_NAME = "nome";
    private static final String COLUMN_BARCODE = "barcode";
    private static final String COLUMN_STORE = "loja";
    private static final String COLUMN_PRICE = "preco";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_BARCODE + " TEXT, " +
                COLUMN_STORE + " TEXT, " +
                COLUMN_PRICE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void insertProduct(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, produto.getNome());
        values.put(COLUMN_BARCODE, produto.getBarcode());
        values.put(COLUMN_STORE, produto.getLoja());
        values.put(COLUMN_PRICE, produto.getPreco());
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }
}