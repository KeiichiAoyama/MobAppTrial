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
    private static final String COLUMN_NOTES = "note";

    public MyDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_NOTES = "CREATE TABLE "+TABLE_NAME+"("+
                COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_JUDUL+" VARCHAR(50) NOT NULL, "+
                COLUMN_NOTES+" TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CREATE_TABLE_NOTES);
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
            {COLUMN_ID, COLUMN_JUDUL, COLUMN_NOTES};

    private Catatan cursorToNote(Cursor cursor){
        Catatan note = new Catatan();
        note.setID(cursor.getLong(0));
        note.setJudul(cursor.getString(1));
        note.setNotes(cursor.getString(2));
        return note;
    }

    public void createNote(String nama, String note){
        ContentValues values = new ContentValues();
        values.put(COLUMN_JUDUL, nama);
        values.put(COLUMN_NOTES, note);
        db.insert(TABLE_NAME, null, values);
    }

    public Catatan getNote(long id){
        Catatan note = new Catatan();
        Cursor cursor = db.query(TABLE_NAME, allColumns, "id="+id, null, null, null, null);
        cursor.moveToFirst();
        note = cursorToNote(cursor);
        cursor.close();
        return note;
    }

    public ArrayList<Catatan> getAllNote(){
        ArrayList<Catatan> notes = new ArrayList<Catatan>();
        Cursor cursor = db.query(TABLE_NAME, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Catatan note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return notes;
    }

    public void updateNote(Catatan note){
        String filter = "id="+note.getID();
        ContentValues args = new ContentValues();
        args.put(COLUMN_JUDUL, note.getJudul());
        args.put(COLUMN_NOTES, note.getNotes());
        db.update(TABLE_NAME, args, filter, null);
    }

    public void deleteNote(long id){
        String filter = "id="+id;
        db.delete(TABLE_NAME, filter, null);
    }
}

