package Prak9_00000067586.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class newNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        Button add = (Button) findViewById(R.id.btnRelease);
        EditText judul = (EditText) findViewById(R.id.editTitle);
        EditText konten = (EditText) findViewById(R.id.editContent);
        MyDBHandler dbh = new MyDBHandler(this);

        try{
            dbh.open();
        }catch (SQLException e){
            e.printStackTrace();
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbh.createNote(judul.getText().toString(), konten.getText().toString());
                Toast.makeText(newNote.this, "Catatan berhasil dibuat.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(newNote.this, MainActivity.class);
                startActivity(i);
                dbh.close();
            }
        });
    }
}