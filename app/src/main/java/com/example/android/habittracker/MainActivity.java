package com.example.android.habittracker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    HabitTrackerContract.HabitTrackerDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new HabitTrackerContract.HabitTrackerDbHelper(this);

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) findViewById(R.id.habit_1)).isSelected()) {
                    dbHelper.insertHabit(1, "Walking the dog");
                }
                if (((CheckBox) findViewById(R.id.habit_2)).isSelected()) {
                    dbHelper.insertHabit(2, "Practicing the saxophone");
                }
                if (((CheckBox) findViewById(R.id.habit_3)).isSelected()) {
                    dbHelper.insertHabit(3, "Taking my medications");
                }
                if (((CheckBox) findViewById(R.id.habit_4)).isSelected()) {
                    dbHelper.insertHabit(4, "Walking around the block");
                }
            }
        });

        Button show = (Button) findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> array_list = new ArrayList<String>();

                Cursor response = dbHelper.getHabits();
                response.moveToFirst();

                while(response.isAfterLast() == false){
                    array_list.add(response.getString(response.getColumnIndex(
                            HabitTrackerContract.HabitEntry.COLUMN_NAME_TITLE)));
                    response.moveToNext();
                }
                ((TextView) findViewById(R.id.results)).setText(array_list.toString());
            }
        });
    }
}
