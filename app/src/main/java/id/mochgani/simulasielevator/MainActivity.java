package id.mochgani.simulasielevator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.mochgani.simulasielevator.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new DatabaseHelper(this);
        mDB.getWritableDatabase();
    }
}
