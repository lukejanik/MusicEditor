package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * STILL NEED TO TEST FOR:
 *  getNotes()
 *  allNotesInBeat()
 *  makeXandLines()
 *  addNotes()
 */
public class BeatTest {
  Beat b1 = new Beat();
  Note middleC = new Note(1, 0, Pitch.C, 4);
  Note lowA = new Note(3, 3, Pitch.A, -10);
  Note highFs = new Note(5, 2, Pitch.FSharp, 999);

  @Test
  public void testAddNotes() {
    b1.addNoteToBeat(middleC);
    assertEquals(b1.getNotes().get(0).toString(), "C4");
    b1.addNoteToBeat(lowA);
    assertEquals(b1.getNotes().get(0).toString() + " " + b1.getNotes().get(1).toString(), "C4 " +
            "A-10");
    b1.addNoteToBeat(highFs);
    assertEquals(b1.getNotes().get(0).toString() + " " + b1.getNotes().get(1).toString() + " " +
            b1.getNotes().get(2).toString(), "C4 A-10 F#999");
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



}