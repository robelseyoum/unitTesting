package com.robelseyoum3.robaunittesting.ui.notelist;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.robelseyoum3.robaunittesting.R;
import com.robelseyoum3.robaunittesting.repository.NoteRepository;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NotesListActivity extends DaggerAppCompatActivity {

    private static final String TAG = "NotesListActivity";

    @Inject
    NoteRepository noteRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        Log.d(TAG, "onCreate: "+noteRepository);
    }
}
