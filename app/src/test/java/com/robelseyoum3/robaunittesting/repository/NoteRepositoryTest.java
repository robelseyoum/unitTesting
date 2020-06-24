package com.robelseyoum3.robaunittesting.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.robelseyoum3.robaunittesting.models.Note;
import com.robelseyoum3.robaunittesting.persistene.NoteDao;
import com.robelseyoum3.robaunittesting.ui.Resource;
import com.robelseyoum3.robaunittesting.util.InstantExecutorExtension;
import com.robelseyoum3.robaunittesting.util.LiveDataTestUtil;
import com.robelseyoum3.robaunittesting.util.TestUtil;

import static com.robelseyoum3.robaunittesting.repository.NoteRepository.DELETE_FAILURE;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.DELETE_SUCCESS;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.INSERT_FAILURE;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.INSERT_SUCCESS;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.INVALID_NOTE_ID;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.NOTE_TITLE_NULL;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.UPDATE_FAILURE;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.UPDATE_SUCCESS;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
@ExtendWith(InstantExecutorExtension.class)
public class NoteRepositoryTest {


    /***
     * IF WE USE @BEFOREALL on top of the class we have to use @TestInstance(TestInstance.Lifecycle.PER_CLASS)
     * @BEFOREALL
     */
    private final Note NOTE1 = new Note(TestUtil.TEST_NOTE_1);
    //system under test
    private NoteRepository noteRepository;

    @Mock
    private NoteDao noteDao;

    @BeforeEach
    public void initEach() {
       // MockitoAnnotations.initMocks(this);//this will mock all @Mock annotated variables
        noteDao = Mockito.mock(NoteDao.class);
        noteRepository = new NoteRepository(noteDao);
    }

    /*
    insert note
    verify the correct method is called
    confirm observer is triggered
    confirm new rows inserted
     */
    @Test
    void insertNote_returnRow() throws Exception {
        //Arrange
        final Long insertedRow = 1L;
        final Single<Long> returnedData = Single.just(insertedRow);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        //Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value: "+ returnedValue.data);
        assertEquals(Resource.success(1, INSERT_SUCCESS), returnedValue);
        /**
         *Another way of writing unit testing is using RxJava
         */
//        noteRepository.insertNote(NOTE1)
//                .test()
//                .wait()
//                .assertValue(Resource.success(1, INSERT_SUCCESS), returnedValue);
    }

    /*
    Insert note
    Failure (return -1)
     */
    @Test
    void insertNote_returnFailure() throws Exception {
        //Arrange
        final  Long failedInsert = -1L;
        final Single<Long> returnedData = Single.just(failedInsert);
        when(noteDao.insertNote(any(Note.class))).thenReturn(returnedData);

        //Act
        final Resource<Integer> returnedValue = noteRepository.insertNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).insertNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        System.out.println("Returned value: "+ returnedValue.data);
        assertEquals(Resource.error(null, INSERT_FAILURE), returnedValue);

    }

    /*
      insert note
      null title
      confirm throw exception
     */
    @Test
    void insertNote_nullTitle_throwException() throws Exception {

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.insertNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }

    /**
     * update note
     * verify correct method is called
     * confirm observer is trigger
     * confirm number of rows updated
     */
    @Test
    void updateNote_returnNumRowsUpdated() throws Exception {
        //Arrange
        final int updatedRow = 1;
        when(noteDao.updateNote(any(Note.class))).thenReturn(Single.just(updatedRow));

        //Act
        final Resource<Integer> returnedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.success(updatedRow, UPDATE_SUCCESS), returnedValue);
    }
    /**
     * update note
     * Failure (-1)
     *
     */
    @Test
    void updateNote_returnFailure() throws Exception {
        //Arrange
        final int failedInsert = -1;
        when(noteDao.updateNote(any(Note.class))).thenReturn(Single.just(failedInsert));

        //Act
        final Resource<Integer> returnFailedValue = noteRepository.updateNote(NOTE1).blockingFirst();

        //Assert
        verify(noteDao).updateNote(any(Note.class));
        verifyNoMoreInteractions(noteDao);

        assertEquals(Resource.error(null, UPDATE_FAILURE), returnFailedValue);
    }

    /**
     * update note
     * null title
     * throw exception
     * */
    @Test
    void updateNote_nulTitleThrowException() throws Exception {

        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                final Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setTitle(null);
                noteRepository.updateNote(note);
            }
        });

        assertEquals(NOTE_TITLE_NULL, exception.getMessage());
    }

    /**
     * delete note
     * null id
     * throw exception
     */
    @Test
    void deleteNote_nullId_throwException() throws Exception {
        Exception exception = assertThrows(Exception.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                Note note = new Note(TestUtil.TEST_NOTE_1);
                note.setId(-1);
                noteRepository.deleteNote(note);
            }
        });
        assertEquals(exception.getMessage(), INVALID_NOTE_ID);
    }

    /**
     * delete note
     * delete success
     * return Resource.success with deleted row
     */
    @Test
    void deleteNote_deleteSuccess_ReturnResourceSuccess() throws Exception {
        //Arrange
        final int deleteRow = 1;
        Resource<Integer> successResponse = Resource.success(deleteRow, DELETE_SUCCESS);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(deleteRow));

        //Act
        Resource<Integer> observedResponse = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));

        //Assert
        assertEquals(successResponse, observedResponse);
    }

    /**
     * delete note
     * delete failure
     * return Resource.error
     */
    @Test
    void deleteNote_deleteFailure_ReturnResourceError() throws Exception {
        //Arrange
        final int failedRow = -1;
        Resource<Integer> errorResponse = Resource.error(null, DELETE_FAILURE);
        LiveDataTestUtil<Resource<Integer>> liveDataTestUtil = new LiveDataTestUtil<>();
        when(noteDao.deleteNote(any(Note.class))).thenReturn(Single.just(failedRow));

        //Act
        Resource<Integer> observedResponse = liveDataTestUtil.getValue(noteRepository.deleteNote(NOTE1));

        //Assert
        assertEquals(observedResponse, errorResponse);
    }

    /**
     * retrieve note
     * return list of notes
     */
    @Test
    void getNotes_returnListWithNotes() throws Exception {
        //Arrange
        List<Note> notes = TestUtil.TEST_NOTES_LIST;
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);

        //Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());

        //Assert
        assertEquals(observedData, notes);
    }

    /**
     * retrieve note
     * return empty list
     */
    @Test
    void getNote_returnEmptyList() throws Exception {
        //Arrange
        List<Note> notes = new ArrayList<>();
        LiveDataTestUtil<List<Note>> liveDataTestUtil = new LiveDataTestUtil<>();
        MutableLiveData<List<Note>> returnedData = new MutableLiveData<>();
        returnedData.setValue(notes);
        when(noteDao.getNotes()).thenReturn(returnedData);

        //Act
        List<Note> observedData = liveDataTestUtil.getValue(noteRepository.getNotes());

        //Assert
        assertEquals(observedData, notes);
    }
}
