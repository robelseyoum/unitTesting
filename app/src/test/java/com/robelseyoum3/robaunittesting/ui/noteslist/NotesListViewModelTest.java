package com.robelseyoum3.robaunittesting.ui.noteslist;

import androidx.lifecycle.MutableLiveData;

import com.robelseyoum3.robaunittesting.models.Note;
import com.robelseyoum3.robaunittesting.repository.NoteRepository;
import com.robelseyoum3.robaunittesting.ui.Resource;
import com.robelseyoum3.robaunittesting.ui.notelist.NotesListViewModel;
import com.robelseyoum3.robaunittesting.util.InstantExecutorExtension;
import com.robelseyoum3.robaunittesting.util.LiveDataTestUtil;
import com.robelseyoum3.robaunittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static com.robelseyoum3.robaunittesting.repository.NoteRepository.DELETE_FAILURE;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.DELETE_SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(InstantExecutorExtension.class)
public class NotesListViewModelTest {

    @Mock
    private NoteRepository noteRepository;

    private NotesListViewModel notesListViewModel;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        notesListViewModel = new NotesListViewModel(noteRepository);
    }

    /**
     * Retrieve list of notes
     * observe list
     * return list
     */
    @Test
    void retrieveNotes_returnNotesList() throws Exception {
        //Arrange
        List<Note> returnedData = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.getNotes()).thenReturn(returnedValue);

        //Act
        notesListViewModel.getNotes();
        List<Note> observedData = liveDataTestUtil.getValue(notesListViewModel.observerNotes());

        //Assert
        Assertions.assertEquals(returnedData, observedData);
    }

    /**
     * retrieve list of notes
     * observe the list
     * return empty list
     */
    @Test
    void retrieveNotes_returnEmptyList() throws Exception {
        //Arrange
        List<Note> returnedData = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.getNotes()).thenReturn(returnedValue);

        //Act
        notesListViewModel.getNotes();
        List<Note> observedData = liveDataTestUtil.getValue(notesListViewModel.observerNotes());

        //Assert
        Assertions.assertEquals(returnedData, observedData);
    }

    /**
     * delete note
     * observe Resource.success
     * return Resource.success
     */
    @Test
    void deleteNote_observeResourceSuccess() throws Exception {
        //Arrange
        Note deletedNote = new Note(TestUtil.TEST_NOTE_1);
        Resource<Integer> returnedData = Resource.success(1, DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnedData);
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);

        //Act
        Resource<Integer> observedValue = liveDataTestUtil.getValue(notesListViewModel.deleteNote(deletedNote));

        //Assert
        Assertions.assertEquals(observedValue, returnedData);
    }

    /**
     * delete note
     * observe Resource.error
     * return Resource.error
     */
    @Test
    void deleteNote_observeResourceError() throws Exception {
        //Arrange
        Note deleteNote = new Note(TestUtil.TEST_NOTE_1);
        Resource<Integer> returnData = Resource.error(null, DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<Resource<Integer>> returnedValue = new MutableLiveData<>();
        returnedValue.setValue(returnData);
        when(noteRepository.deleteNote(any(Note.class))).thenReturn(returnedValue);

        //Act
        Resource<Integer> observedData = liveDataTestUtil.getValue(notesListViewModel.deleteNote(deleteNote));

        //Assert
        Assertions.assertEquals(observedData, returnData);
    }
}
