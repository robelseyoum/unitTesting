package com.robelseyoum3.robaunittesting.ui.note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.robelseyoum3.robaunittesting.models.Note;
import com.robelseyoum3.robaunittesting.repository.NoteRepository;
import com.robelseyoum3.robaunittesting.ui.Resource;

import javax.inject.Inject;

import static com.robelseyoum3.robaunittesting.repository.NoteRepository.NOTE_TITLE_NULL;

public class NoteViewModel extends ViewModel {

    private static final String TAG = "NoteViewModel";

    //inject
    private final NoteRepository noteRepository;

    //vars
    private MutableLiveData<Note> note = new  MutableLiveData<>();

    @Inject
    public NoteViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public LiveData<Resource<Integer>> insertNote() throws Exception {
        return LiveDataReactiveStreams.fromPublisher( //this convert from Flowable to LiveDataReactiveStreams then it will be observable in UI
                noteRepository.insertNote(note.getValue())
        );
    }

    public LiveData<Note> observeNote(){
        return note;
    }

    public void setNote(Note note) throws Exception {
        if(note.getTitle() == null || note.getTitle().equals("")){
            throw  new Exception(NOTE_TITLE_NULL);
        }
        this.note.setValue(note);
    }
}
