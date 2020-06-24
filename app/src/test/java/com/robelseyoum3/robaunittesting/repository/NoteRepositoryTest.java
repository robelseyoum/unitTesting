package com.robelseyoum3.robaunittesting.repository;

import com.robelseyoum3.robaunittesting.models.Note;
import com.robelseyoum3.robaunittesting.persistene.NoteDao;
import com.robelseyoum3.robaunittesting.ui.Resource;
import com.robelseyoum3.robaunittesting.util.TestUtil;

import static com.robelseyoum3.robaunittesting.repository.NoteRepository.INSERT_FAILURE;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.INSERT_SUCCESS;
import static com.robelseyoum3.robaunittesting.repository.NoteRepository.NOTE_TITLE_NULL;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.inject.Singleton;

import io.reactivex.Single;

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
}
