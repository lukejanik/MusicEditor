package cs3500.music.model;

import cs3500.music.MusicEditor;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

/**
 * PROBLEM WITH REMOVING A NOTE -> if two identical notes are added to the model, they can't
 * be removed?? possibly problem with overriding .equals and hashcode.
 */
public class MusicEditorModelTest {

  IMusicEditorModel model = new MusicEditorModel();
  ArrayList<MusicEditorModel> pieces = new ArrayList<>();

  /*********************************** TESTS FOR WRITE METHOD. *********************************/

  // Write one simple note to piece at 0
  @Test
  public void testWriteSimple() {
    Note note1 = new Note(1, 0, Pitch.A, 5);
    model.write(note1);
    assertEquals(model.getBeats().size(), 1);
    assertEquals(model.getBeats().get(0).getNotes().get(0), note1);
  }

  // Write one long note to piece at 0
  @Test
  public void testWriteLongNote() {
     Note note2 = new Note(3, 0, Pitch.A, 5);
    model.write(note2);
    assertEquals(model.getBeats().get(0).containsNote(note2), true);
    assertEquals(model.getBeats().get(1).containsNote(note2), true);
    assertEquals(model.getBeats().get(2).containsNote(note2), true);
  }

  // Write one simple note to piece at not 0
  @Test
  public void testWriteSimpleLaterBeat() {
    Note note3 = new Note(1, 3, Pitch.A, 5);
    model.write(note3);
    assertEquals(model.getBeats().get(0).containsNote(note3), false);
    assertEquals(model.getBeats().get(1).containsNote(note3), false);
    assertEquals(model.getBeats().get(2).containsNote(note3), false);
    assertEquals(model.getBeats().get(3).containsNote(note3), true);
  }

  // Write one longer note to piece at not 0
  @Test
  public void testWriteLongNoteLaterBeat() {
    Note note4 = new Note(2, 2, Pitch.A, 5);
    model.write(note4);
    assertEquals(model.getBeats().get(0).containsNote(note4), false);
    assertEquals(model.getBeats().get(1).containsNote(note4), false);
    assertEquals(model.getBeats().get(2).containsNote(note4), true);
    assertEquals(model.getBeats().get(3).containsNote(note4), true);
  }

  // Write two simple notes to the same beat
  @Test
  public void testWriteMultipleSimpleNotesSameBeat() {
    Note note5 = new Note(1, 1, Pitch.B, 4);
    Note note6 = new Note(1, 1, Pitch.CSharp, 4);
    model.write(note5);
    model.write(note6);
    assertEquals(model.getBeats().get(1).containsNote(note5), true);
    assertEquals(model.getBeats().get(1).containsNote(note6), true);
  }

  // Write two long notes on the same beat
  @Test
  public void testWriteLongOverlappingNotes() {
    Note note7 = new Note(3, 0, Pitch.E, 4);
    Note note8 = new Note(2, 2, Pitch.CSharp, 4);
    model.write(note7);
    model.write(note8);
    assertEquals(model.getBeats().get(0).containsNote(note7), true);
    assertEquals(model.getBeats().get(0).containsNote(note8), false);
    assertEquals(model.getBeats().get(1).containsNote(note7), true);
    assertEquals(model.getBeats().get(1).containsNote(note8), false);
    assertEquals(model.getBeats().get(2).containsNote(note7), true);
    assertEquals(model.getBeats().get(2).containsNote(note8), true);
    assertEquals(model.getBeats().get(3).containsNote(note7), false);
    assertEquals(model.getBeats().get(3).containsNote(note8), true);
  }

  @Test
  public void testWriteIdentical() {
    Note note1 = new Note(3, 0, Pitch.E, 4);
    Note note2 = new Note(3, 0, Pitch.E, 4);
    model.write(note1);
    model.write(note2);
    assertEquals(model.getBeats().get(0).containsNote(note1), true);
    assertEquals(model.getBeats().get(0).containsNote(note2), true);
  }

  // Write on top of another note
  @Test
  public void testWriteOverlap() {
    Note note1 = new Note(3, 0, Pitch.E, 4);
    Note note2 = new Note(3, 2, Pitch.E, 4);
    model.write(note1);
    model.write(note2);
    assertEquals(model.getBeats().get(0).containsNote(note1), true);
    assertEquals(model.getBeats().get(2).containsNote(note2), true);
  }

  /******************************** TESTS FOR EDIT METHOD. ************************************/

  // Simple edit: Replace single note from 0 with new note
  @Test
  public void testEditSingleWithSingle() {
    Note note10 = new Note(1, 0, Pitch.A, 5);
    Note note11 = new Note(1, 0, Pitch.A, 8);
    model.write(note10);
    model.edit(note10, note11);
    assertEquals(model.getBeats().get(0).containsNote(note10), false);
    assertEquals(model.getBeats().get(0).containsNote(note11), true);
  }

  // Long edit: Replace long note from 0 with new note
  @Test
  public void testEditLongWithSingle() {
    Note note10 = new Note(1, 0, Pitch.A, 5);
    Note note11 = new Note(1, 0, Pitch.A, 8);
    model.write(note10);
    model.edit(note10, note11);
    assertEquals(model.getBeats().get(0).containsNote(note11), true);
    assertEquals(model.getBeats().get(0).containsNote(note10), false);
  }

  // Long edit: Replace single note from non-zero with long note
  @Test
  public void testEditSingleWithLongNotZero() {
    Note note10 = new Note(3, 1, Pitch.A, 5);
    Note note11 = new Note(1, 1, Pitch.A, 8);
    model.write(note11);
    model.edit(note11, note10);
    assertEquals(model.getBeats().get(1).containsNote(note10), true);
    assertEquals(model.getBeats().size(), 4);
  }

  // Editing the same note
  @Test
  public void testEditSameNote() {
    Note note1 = new Note(1, 0, Pitch.A, 0);
    Note note2 = new Note(1, 0, Pitch.A, 0);
    model.write(note1);
    model.write(note2);
    model.edit(note1, note2);
    assertEquals(model.getBeats().get(0).containsNote(note2), true);
    assertEquals(model.getBeats().get(0).containsNote(note1), true);
    assertEquals(model.getBeats().size(), 1);
    assertEquals(model.getBeats().get(0).getNotes().size(), 1);
  }
}
