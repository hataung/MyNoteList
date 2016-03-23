package rakhinethargree.com.mynotelist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;

public class NoteDetailActivity extends ActionBarActivity {

    private TextView title,date,content;
    private Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);


        title = (TextView) findViewById(R.id.detailsTitle);
        date = (TextView) findViewById(R.id.detailsDateText);
        content = (TextView) findViewById(R.id.detailsTextView);

        deleteButton = (Button) findViewById(R.id.deleteButton);

        Bundle extras = getIntent().getExtras();// take all the bundle

        if (extras !=null){

            title.setText(extras.getString("title"));
            date.setText("Created on " + extras.getString("date"));
            content.setText(" \" " + extras.getString("content") + " \" ");// content is surrounded by " "

            final int id = extras.getInt("id");

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseHandler dba = new DatabaseHandler(getApplicationContext());
                    dba.deleteNote(id);

                    Toast.makeText(getApplicationContext(), " Note deleted! ",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(NoteDetailActivity.this, DisplayNotesActivity.class));// going back to displayNotesActivity after deleting

                }
            });

        }



    }

}
