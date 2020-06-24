package com.robelseyoum3.robaunittesting.ui.notelist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.robelseyoum3.robaunittesting.R;
import com.robelseyoum3.robaunittesting.repository.NoteRepository;
import com.robelseyoum3.robaunittesting.ui.note.NoteActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class NotesListActivity extends DaggerAppCompatActivity {

    private static final String TAG = "NotesListActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);

    }
}
