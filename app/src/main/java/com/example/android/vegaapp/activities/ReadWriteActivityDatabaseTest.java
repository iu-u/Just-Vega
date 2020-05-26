package com.example.android.vegaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.vegaapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadWriteActivityDatabaseTest extends AppCompatActivity {

    private static String TAG = ReadWriteActivityDatabaseTest.class.getName();

    TextView write;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_write_database_test);

        Log.d(TAG, "GO INTO DATABASE CLASS ");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myRef = database.getReference().child("test");


        Log.d(TAG, "Value in database +" + database.getReference());

        writeToDatabase();
        readFromDatabase();

        write = (TextView) findViewById(R.id.editText2);

    }

    public void writeToDatabase(){

        myRef.setValue("Helllo world");
    }

    public void readFromDatabase(){
// Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
                write.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}
