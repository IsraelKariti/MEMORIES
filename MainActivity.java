package com.example.izi.memories;

import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.izi.memories.Utility.TotalDay;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;
import java.util.Date;

import androidx.work.WorkManager;

import static com.example.izi.memories.MyContract.Memories.COLUMN_MEMORY;
import static com.example.izi.memories.MyContract.Memories.COLUMN_TOTAL_DAY;
import static com.example.izi.memories.MyContract.Memories.TABLE_NAME;

public class MainActivity extends AppCompatActivity implements MyDialogFragment.InsertMemoryInterface, MyDialogFragmentForDate.ChangeToSpecificDateInterface{

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;

    MySQLiteOpenHelper mSQL;
    SQLiteDatabase mDB;


    private GeofencingClient mGeofencingClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "0")
//                .setSmallIcon(R.drawable.ic_fitness_center_black_24dp)
//                .setContentTitle("MAIN ACTIVITY")
//                .setContentText("MAIN ACTIVITY")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
//        notificationManager.notify(-1, mBuilder.build());

        // prepare the database
        mSQL = new MySQLiteOpenHelper(this, "", null, 1);

        // get the date today + make it totalDay
        int totalDay = TotalDay.getTotalDay();
        String totalDayString = String.valueOf(totalDay);

        // prepare the adapter
        mMyAdapter = new MyAdapter(this);
        mMyAdapter.updateCursor(totalDay);

        // set the adapter
        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setAdapter(mMyAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Create the toolbar (Mandatory by the Android Framework)
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Intent intent = new Intent(this, AngelService.class);
        startService(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.change_date:
                MyDialogFragmentForDate myDialogFragmentForDate = new MyDialogFragmentForDate();
                myDialogFragmentForDate.show(getFragmentManager(), "date_change");
                break;
            case R.id.share:
                Log.i("XXXXX", "SHAREEEEE");
                // create string
                int count = 1;
                String shareString = "";
                Cursor cursorToShare = mMyAdapter.mCursor;
                cursorToShare.moveToFirst();
                while(!cursorToShare.isAfterLast()){
                    shareString += String.valueOf(count++)+". ";
                    shareString += cursorToShare.getString(2)+"\n";
                    cursorToShare.moveToNext();
                }

                // send on Whatsapp
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareString);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Log.i("XXXX","Whatsapp have not been installed.");
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addMemory(View view){
        MyDialogFragment dialogFragmentAddMemory = new MyDialogFragment();
        dialogFragmentAddMemory.show(getSupportFragmentManager(), "title");
    }

    @Override
    public void InsertMemory(String memory, int totalDay) {

        //insert memory to db
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TOTAL_DAY, totalDay);
        cv.put(COLUMN_MEMORY, memory);
        mDB = mSQL.getWritableDatabase();
        mDB.insert(TABLE_NAME, null, cv);

        // tell adapter to update itself
        mMyAdapter.updateCursor(totalDay);
    }


    @Override
    public void changeToSpecificDate(int totalDay) {
        mMyAdapter.updateCursor(totalDay);
    }
}