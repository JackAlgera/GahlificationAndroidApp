package com.jack.algera.gahlificapption.reminders;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.jack.algera.gahlificapption.R;
import com.jack.algera.gahlificapption.web.responseHandlers.GetAllReminders;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        LinearLayout linearLayout = findViewById(R.id.reminderEntriesContainer);

        GetAllReminders getAllReminders = new GetAllReminders(linearLayout, getFragmentManager(), getApplicationContext());
        getAllReminders.start();
    }
}