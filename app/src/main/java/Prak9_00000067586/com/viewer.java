package Prak9_00000067586.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class viewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        Button edit = (Button) findViewById(R.id.btnEdit);
        Button del = (Button) findViewById(R.id.btnDel);
        Button upd = (Button)findViewById(R.id.btnUpdate);
        EditText judul = (EditText) findViewById(R.id.editJudul);
        EditText konten = (EditText) findViewById(R.id.editKonten);
        Intent I = getIntent();
        Catatan note = (Catatan) I.getSerializableExtra("note");
        MyDBHandler dbh = new MyDBHandler(this);

        try{
            dbh.open();
        }catch(SQLException e){
            e.printStackTrace();
        }

        judul.setText(note.getJudul());
        konten.setText(note.getNotes());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul.setEnabled(true);
                konten.setEnabled(true);
                upd.setEnabled(true);
            }
        });

        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note.setJudul(judul.getText().toString());
                note.setNotes(konten.getText().toString());
                dbh.updateNote(note);
                Toast.makeText(viewer.this, "Catatan berhasil diperbaharui", Toast.LENGTH_LONG).show();
                startActivity(new Intent(viewer.this, MainActivity.class));
                dbh.close();
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder conf = new AlertDialog.Builder(viewer.this);
                conf.setTitle("Hapus Catatan");
                conf.setMessage("Anda yakin ingin menghapus catatan ini?");
                conf.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbh.deleteNote(note.getID());
                        Toast.makeText(viewer.this, "Catatan berhasil dihapus", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(viewer.this, MainActivity.class));
                        dbh.close();
                    }
                });
                conf.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {}
                });
                conf.show();
            }
        });
    }
}