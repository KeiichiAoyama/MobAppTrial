package Prak9_00000067586.com;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private MyDBHandler dbh;
    private ArrayList<Catatan> notes;
    private Button add;
    private ListView list;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add = (Button) findViewById(R.id.btnAdd);
        ListView list = (ListView) findViewById(android.R.id.list);
        dbh = new MyDBHandler(this);

        try{
            dbh.open();
        }catch (SQLException e){
            e.printStackTrace();
        }

        notes = dbh.getAllNote();
        ArrayAdapter<Catatan> adp = new ArrayAdapter<Catatan>(this, android.R.layout.simple_list_item_1, notes);
        setListAdapter(adp);
        list.setOnItemClickListener(this);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), newNote.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Catatan note = (Catatan) getListAdapter().getItem(i);
        long id = note.getID();
        Intent I = new Intent(getApplicationContext(), viewer.class);
        I.putExtra("note", note);
        startActivity(I);
    }
}