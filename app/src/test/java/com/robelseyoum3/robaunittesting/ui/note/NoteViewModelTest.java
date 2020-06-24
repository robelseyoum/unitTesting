package com.robelseyoum3.robaunittesting.ui.note;

import androidx.lifecycle.LiveData;

import com.robelseyoum3.robaunittesting.models.Note;
import com.robelseyoum3.robaunittesting.repository.NoteRepository;
import com.robelseyoum3.robaunittesting.ui.Resource;
import com.robelseyoum3.robaunittesting.util.InstantExecutorExtension;
import com.robelseyoum3.robaunittesting.util.LiveDataTestUtil;
import com.robelseyoum3.robaunittesting.util.TestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Flowable;
import io.reactivex.internal.operators.single.SingleToFlowable;

import static com.robelseyoum3.robaunittesting.repository.NoteRepository.INSERT_SUCCESS;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.UPDATE_SUCCESS;
import static com.robelseyoum3.robaunittesting.ui.note.NoteViewModel.NO_CONTENT_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(InstantExecutorExtension.class)
public class NoteViewModelTest {
    //system under test

    @Mock
    private NoteRepository noteRepository;

    private NoteViewModel noteViewModel;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        noteViewModel = new NoteViewModel(noteRepository);
    }


    /*
    can't observe a note that hasn't been set
     */
    @Test
    void observeEmptyNoteWhenNoteSet() throws Exception {
        //Arrange
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        //Act
        Note note = liveDataTestUtil.getValue(noteViewModel.observeNote());

        //Assert
        assertNull(note);
    }

    /*
    Observe a note has been set and onChanged will trigger in activity
     */
    @Test
    void observeNote_whenSet() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Note> liveDataTestUtil = new LiveDataTestUtil<>();

        //Act
        noteViewModel.setNote(note);
        Note observedNote = liveDataTestUtil.getValue(noteViewModel.observeNote());

        //Assert
        assertEquals(observedNote, note);
    }

    /*
    Insert a new note observe row returned
     */
    @Test
    void insertNote_returnRow() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int insertedRow = 1;
        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(insertedRow, INSERT_SUCCESS));
        when(noteRepository.insertNote(any(Note.class))).thenReturn(returnedData);

        //Act
        noteViewModel.setNote(note);
        noteViewModel.setIsNewNote(true);
        Resource<Integer> returnValue = liveDataTestUtil.getValue(noteViewModel.saveNote());
        //Assert
        assertEquals(Resource.success(insertedRow, INSERT_SUCCESS), returnValue);
    }
    /*
    insert: don't return a new row without observer
     */

    @Test
    void dontReturnInsertRowWithoutObserver() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);

        //Act
        noteViewModel.setNote(note);

        //Assert
        verify(noteRepository, never()).insertNote(any(Note.class));
    }
    /*
    set note, null title, throw exception
     */
    @Test
    void setNote_nullTitle_throwException() throws Exception {
        //Arrange
        final Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setTitle(null);

        //Assert
        assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                //Act
                noteViewModel.setNote(note);
            }
        });

    }

    /*
        update a note and observe row returned
     */

    @Test
    void updateNote_returnRow() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        final int updatedRow = 1;
        Flowable<Resource<Integer>> returnedData = SingleToFlowable.just(Resource.success(updatedRow, UPDATE_SUCCESS));
        when(noteRepository.updateNote(any(Note.class))).thenReturn(returnedData);

        //Act
        noteViewModel.setNote(note);
        noteViewModel.setIsNewNote(false);
        Resource<Integer> returnedValue = liveDataTestUtil.getValue(noteViewModel.saveNote());

        //Assert
        assertEquals(Resource.success(updatedRow, UPDATE_SUCCESS), returnedValue);
    }

    /*
        update: don't return a new row without observer
     */
    @Test
    void dontReturnUpdateRowNumWithoutObserver() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);

        //Act
        noteViewModel.setNote(note);

        //Assert
        verify(noteRepository, never()).updateNote(any(Note.class));
    }

    @Test
    void saveNote_shouldAllowSave_returnFalse() throws Exception {
        //Arrange
        Note note = new Note(TestUtil.TEST_NOTE_1);
        note.setContent(null);

        //Act
        noteViewModel.setNote(note);
        noteViewModel.setIsNewNote(true);

        //Assert
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                noteViewModel.saveNote();
            }
        });

        assertEquals(exception.getMessage(), NO_CONTENT_ERROR);
    }
}
