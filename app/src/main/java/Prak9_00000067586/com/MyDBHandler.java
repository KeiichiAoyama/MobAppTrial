package Prak9_00000067586.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notes.db";
    private static final String TABLE_NAME = "notes";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_JUDUL = "judul";
    private static final String COLUMN_NOTES = "notes";

    public MyDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_BARANG = "CREATE TABLE "+TABLE_NAME+"("+
                COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_JUDUL+" VARCHAR(50) NOT NULL, "+
                COLUMN_NOTES+" VARCHAR(50) NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_BARANG);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    private SQLiteDatabase db;

    public void open() throws SQLException{
        db = this.getWritableDatabase();
    }

    private String[] allColumns =
            {COLUMN_ID, COLUMN_NAMABARANG, COLUMN_KATEGORIBARANG, COLUMN_HARGABARANG};

    private Barang cursorToBarang(Cursor cursor){
        Barang barang = new Barang();
        barang.setID(cursor.getLong(0));
        barang.setNamaBarang(cursor.getString(1));
        barang.setKategoriBarang(cursor.getString(2));
        barang.setHargaBarang(cursor.getLong(3));
        return barang;
    }

    public void createBarang(String nama, String kategori, long harga){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAMABARANG, nama);
        values.put(COLUMN_KATEGORIBARANG, kategori);
        values.put(COLUMN_HARGABARANG, harga);
        db.insert(TABLE_NAME, null, values);
    }

    public Barang getBarang(long id){
        Barang barang = new Barang();
        Cursor cursor = db.query(TABLE_NAME, allColumns, "_id="+id, null, null, null, null);
        cursor.moveToFirst();
        barang = cursorToBarang(cursor);
        cursor.close();
        return barang;
    }

    public ArrayList<Barang> getAllBarang(){
        ArrayList<Barang> daftarBarang = new ArrayList<Barang>();
        Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Barang barang = cursorToBarang(cursor);
            daftarBarang.add(barang);
            cursor.moveToNext();
        }
        cursor.close();
        return daftarBarang;
    }

    public void updateBarang(Barang barang){
        String filter = "_id="+barang.getID();
        ContentValues args = new ContentValues();
        args.put(COLUMN_NAMABARANG, barang.getNamaBarang());
        args.put(COLUMN_HARGABARANG, barang.getHargaBarang());
        args.put(COLUMN_KATEGORIBARANG, barang.getKategoriBarang());
        db.update(TABLE_NAME, args, filter, null);
    }

    public void deleteBarang(long id){
        String filter = "_id="+id;
        db.delete(TABLE_NAME, filter, null);
    }
}

