package com.jack.algera.gahlificapption.reminders;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jack.algera.gahlificapption.R;
import com.jack.algera.gahlificapption.reminders.models.Reminder;

import java.time.Instant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReminderEntryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderEntryFragment extends Fragment {
    private static final String REMINDER_NAME                       = "REMINDER_NAME";
    private static final String REMINDER_DESCRIPTION                = "REMINDER_DESCRIPTION";
    private static final String REMINDER_DUE_DATE                   = "REMINDER_DUE_DATE";
    private static final String REMINDER_START_NOTIFYING_DATE       = "REMINDER_START_NOTIFYING_DATE";
    private static final String REMINDER_PING_FREQUENCY_INTERVAL    = "REMINDER_PING_FREQUENCY_INTERVAL";

    private Reminder reminder;

    public ReminderEntryFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Fragment newInstance(Reminder reminder) {
        ReminderEntryFragment fragment = new ReminderEntryFragment();
        Bundle args = new Bundle();
        args.putString(REMINDER_NAME, reminder.getName());
        args.putString(REMINDER_DESCRIPTION, reminder.getDescription());
        args.putLong(REMINDER_DUE_DATE, reminder.getDueDate().getEpochSecond());
        args.putLong(REMINDER_START_NOTIFYING_DATE, reminder.getStartNotifyingDate().getEpochSecond());
        args.putLong(REMINDER_PING_FREQUENCY_INTERVAL, reminder.getPingFrequencyInterval());
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            reminder = new Reminder(
                    null,
                    getArguments().getString(REMINDER_NAME),
                    getArguments().getString(REMINDER_DESCRIPTION),
                    Instant.ofEpochSecond(getArguments().getLong(REMINDER_DUE_DATE)),
                    Instant.ofEpochSecond(getArguments().getLong(REMINDER_START_NOTIFYING_DATE)),
                    getArguments().getLong(REMINDER_PING_FREQUENCY_INTERVAL),
                    null,
                    null
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_reminder_entry, container, false);
        ((TextView) v.findViewById(R.id.reminderName)).setText(reminder.getName());
        ((TextView) v.findViewById(R.id.reminderDescription)).setText(reminder.getDescription());
        ((TextView) v.findViewById(R.id.reminderDate)).setText(reminder.getDueDate().toString());
        return v;
    }
}