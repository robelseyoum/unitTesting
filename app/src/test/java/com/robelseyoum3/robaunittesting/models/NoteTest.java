package com.robelseyoum3.robaunittesting.models;

import com.robelseyoum3.robaunittesting.util.TestUtil;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class NoteTest {

    public static final String TIMESTAMP_1 = "05-2020";
    public static final String TIMESTAMP_2 = "06-2020";
    /*
    compare two equal Notes
     */
    @Test
    void isNotesEqual_identicalProperties_returnTrue() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1",TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1",TIMESTAMP_1);
        note2.setId(1);
        //Act
        //Assert
        Assert.assertEquals(note1, note2);
        System.out.println("The notes are equal");
    }

    /*
    compare notes with 2 different ids
     */

    @Test
    void isNotesEquals_differentIds_returnFalse() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1",TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1",TIMESTAMP_1);
        note2.setId(2);
        //Act
        //Assert
        Assert.assertNotEquals(note1, note2);
        System.out.println("The notes are not equal");
    }
    
    /*
    compare two notes with different timestamps
     */

    @Test
    void isNotesEquals_differentIds_returnsTrue() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1",TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #1",TIMESTAMP_2);
        note2.setId(1);
        //Act
        //Assert
        Assert.assertEquals(note1, note2);
        System.out.println("The notes are equal");
    }
    
    /*
    compare two notes with different titles
     */

    @Test
    void isNotesEquals_differentTitle_returnsFalse() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1",TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #2", "This is note #1",TIMESTAMP_2);
        note2.setId(1);
        //Act
        //Assert
        Assert.assertNotEquals(note1, note2);
        System.out.println("The notes are not equal they have different title");
    }


    /*
    compare two notes with different content
     */
    @Test
    void isNotesEquals_differentContent_returnsFalse() throws Exception {
        //Arrange
        Note note1 = new Note("Note #1", "This is note #1",TIMESTAMP_1);
        note1.setId(1);
        Note note2 = new Note("Note #1", "This is note #2",TIMESTAMP_2);
        note2.setId(1);
        //Act
        //Assert
        Assert.assertNotEquals(note1, note2);
        System.out.println("The notes are not equal they have different content");
    }
}
