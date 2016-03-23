package rakhinethargree.com.mynotelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DatabaseHandler;
import model.MyNote;

public class MainActivity extends ActionBarActivity {

    private EditText title;
    private EditText content;
    private Button saveButton;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dba = new DatabaseHandler(MainActivity.this);

        title = (EditText) findViewById(R.id.titleEditText);
        content = (EditText) findViewById(R.id.noteEditText);
        saveButton = (Button) findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });

    }

    private void saveToDB() {

        MyNote note = new MyNote();
        note.setTitle(title.getText().toString().trim());
        note.setContent(content.getText().toString().trim());

        dba.addNotes(note);
        dba.close();

        //clear
        title.setText("");
        content.setText("");

        //display detail activity
        Intent i = new Intent(MainActivity.this,DisplayNotesActivity.class);
        startActivity(i);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
