package rakhinethargree.com.mynotelist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import data.DatabaseHandler;
import model.MyNote;

public class DisplayNotesActivity extends ActionBarActivity {

    private DatabaseHandler dba;
    private ArrayList<MyNote> dbNotes = new ArrayList<>();
    private NoteAdapter noteAdapter;
    private ListView listView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notes);

        listView = (ListView) findViewById(R.id.list);

        refreshData();


    }

    private void refreshData() {

        dbNotes.clear();
        dba = new DatabaseHandler(getApplicationContext());

        ArrayList<MyNote> notesFromDB = dba.getNotes();

        for (int i = 0; i < notesFromDB.size(); i++) {

            String title = notesFromDB.get(i).getTitle();
            String dateText = notesFromDB.get(i).getRecordDate();
            String content = notesFromDB.get(i).getContent();
            int mid = notesFromDB.get(i).getItemId();


            MyNote myNote = new MyNote();
            myNote.setTitle(title);
            myNote.setContent(content);
            myNote.setRecordDate(dateText);
            myNote.setItemId(mid);

            dbNotes.add(myNote);

        }
        dba.close();

        //setup adapter
        noteAdapter = new NoteAdapter(DisplayNotesActivity.this,R.layout.note_row,dbNotes);
        listView.setAdapter(noteAdapter);
        noteAdapter.notifyDataSetChanged();

    }


    public class NoteAdapter extends ArrayAdapter<MyNote> {

        Activity activity;
        int layoutResource;
        MyNote note;
        ArrayList<MyNote> mData = new ArrayList<>();

        public NoteAdapter(Activity act, int resource, ArrayList<MyNote> data) {
            super(act, resource, data);
            activity = act;
            layoutResource = resource;
            mData = data;

            notifyDataSetChanged();

        }


        // you can get these override methods by typing cmd_n
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public MyNote getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(MyNote item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            ViewHolder holder = null;

            if (row == null || (row.getTag()) == null ){

                LayoutInflater inflater = LayoutInflater.from(activity);
                row = inflater.inflate(layoutResource, null);
                holder = new ViewHolder();

                holder.mTitle = (TextView) row.findViewById(R.id.name);
                holder.mDate = (TextView) row.findViewById(R.id.dateText);

                row.setTag(holder);

            }else {

                holder = (ViewHolder) row.getTag();
            }// to get it from the list view faster

            holder.myNote = getItem(position);

            holder.mTitle.setText(holder.myNote.getTitle());
            holder.mDate.setText(holder.myNote.getRecordDate());

            final ViewHolder finalHolder = holder;
            holder.mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = finalHolder.myNote.getContent().toString();
                    String dateText = finalHolder.myNote.getRecordDate().toString();
                    String title = finalHolder.myNote.getTitle().toString();

                    int mId = finalHolder.myNote.getItemId();


                    Intent i = new Intent(DisplayNotesActivity.this,NoteDetailActivity.class);
                    // to take all the texts of the Note when you click
                    i.putExtra("content",text);
                    i.putExtra("date",dateText);
                    i.putExtra("title",title);
                    i.putExtra("id",mId);

                    startActivity(i);

                }
            });

            return row;// row holds the whole convert view
        }

        class ViewHolder{

            MyNote myNote;
            int mId;
            TextView mTitle,mContent,mDate;

        }


    }





}
