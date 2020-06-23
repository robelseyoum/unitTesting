package com.robelseyoum3.robaunittesting.persistene;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.robelseyoum3.robaunittesting.models.Note;
import java.util.List;
import io.reactivex.Single;

@Dao
public interface NoteDao {
    @Insert
    Single<Long> insertNote(Note note) throws Exception;

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    @Update
    Single<Integer> updateNote(Note note) throws  Exception;

    @Delete
    Single<Integer> deleteNote(Note note) throws Exception;

}
