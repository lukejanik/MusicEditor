/**
 * Created by ashnashah on 10/28/16.
 */
package cs3500.music.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * STILL NEED TO TEST FOR: allNotesInBeat() makeXandLines()
 */
public class BeatTest {
  Beat b1 = new Beat();
  Note middleC = new Note(1, 0, Pitch.C, 4);
  Note lowA = new Note(3, 3, Pitch.A, -10);
  Note highFs = new Note(5, 2, Pitch.FSharp, 999);
  Beat b2 = new Beat();
  Note A1 = new Note(2, 3, Pitch.A, 1);
  Note B3 = new Note(4, 2, Pitch.B, 3);
  Note D2 = new Note(3, 1, Pitch.D, 2);

  @Test
  public void testAddNote() {
    b1.addNoteToBeat(middleC);
    assertEquals(b1.getNotes().get(0).toString(), "C4");
    b1.addNoteToBeat(lowA);
    assertEquals(b1.getNotes().get(0).toString() + " " + b1.getNotes().get(1).toString(), "C4 " +
            "A-10");
    b1.addNoteToBeat(highFs);
    assertEquals(b1.getNotes().get(0).toString() + " " + b1.getNotes().get(1).toString() + " " +
            b1.getNotes().get(2).toString(), "C4 A-10 F#999");

    b2.addNoteToBeat(A1);
    assertEquals(b2.getNotes().get(0).toString(), "A1");
    b2.addNoteToBeat(B3);
    assertEquals(b2.getNotes().get(0).toString() + " " + b2.getNotes().get(1).toString(), "A1 " +
            "B3");
    b2.addNoteToBeat(D2);
    assertEquals(b2.getNotes().get(0).toString() + " " + b2.getNotes().get(1).toString() + " " +
    b2.getNotes().get(2).toString(), "A1 B3 D2");
  }

  @Test
  public void testGetNotes() {
    b1.addNoteToBeat(middleC);
    b1.addNoteToBeat(lowA);
    b1.addNoteToBeat(highFs);
    List<Note> notes = new ArrayList<>();
    notes.add(middleC);
    notes.add(lowA);
    notes.add(highFs);
    assertEquals(notes, b1.getNotes());
  }

  @Test
  public void testRemoveNotes() {
    this.testAddNotes();
    b1.removeNoteFromBeat(middleC);
    assertEquals(b1.getNotes().get(0).toString() + " " + b1.getNotes().get(1).toString(),
            "A-10 F#999");
    b1.removeNoteFromBeat(lowA);
    assertEquals(b1.getNotes().get(0).toString(), "F#999");
    b1.removeNoteFromBeat(middleC);
    assertEquals(b1.getNotes().get(0).toString(), "F#999");
    b1.removeNoteFromBeat(highFs);
    assertEquals(b1.getNotes().size(), 0);
  }

  @Test
  public void testContains() {
    this.testAddNotes();
    assertEquals(b1.containsNote(middleC), true);
    b1.removeNoteFromBeat(middleC);
    assertEquals(b1.containsNote(middleC), false);
    assertEquals(b1.containsNote(highFs), true);

  }

  @Test
  public void testAddNotes() {
    this.testAddNote();
    b1.addNotes(b2);
    assertEquals(b1.getNotes().size(), 6);
    assertEquals(b1.getNotes().get(1), lowA);
    assertEquals(b1.getNotes().get(3), A1);
    assertEquals(b1.getNotes().get(5), D2);
  }

}