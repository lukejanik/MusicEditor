package cs3500.music.model;

import cs3500.music.MusicEditor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * PROBLEM WITH REMOVING A NOTE -> if two identical notes are added to the model, they can't
 * be removed?? possibly problem with overriding .equals and hashcode.
 */
public class MusicEditorModelTest {

  MusicEditorModel model = new MusicEditorModel();


  Note Fs4 = new Note(5, 2, Pitch.FSharp, 4);
  Note G3 = new Note(2, 1, Pitch.G, 3);
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

  /***************************** TESTS FOR REMOVE METHOD. ************************************/


  // Remove a single note at zero
  @Test
  public void testRemoveSingleNoteZero() {
    Note note12 = new Note(1, 0, Pitch.D, 0);
    model.write(note12);
    assertEquals(model.getBeats().get(0).containsNote(note12), true);
    model.remove(note12);
    assertEquals(model.getBeats().get(0).getNotes().size(), 0);
  }

  // Remove a long note at zero
  @Test
  public void testRemoveLongNoteZero() {
    Note note12 = new Note(3, 0, Pitch.D, 0);
    model.write(note12);
    assertEquals(model.getBeats().get(0).containsNote(note12), true);
    assertEquals(model.getBeats().get(1).containsNote(note12), true);
    assertEquals(model.getBeats().get(2).containsNote(note12), true);
    model.remove(note12);
    assertEquals(model.getBeats().get(0).getNotes().size(), 0);
    assertEquals(model.getBeats().get(1).getNotes().size(), 0);
    assertEquals(model.getBeats().get(2).getNotes().size(), 0);
  }

  // Remove a single note at non zero
  @Test
  public void testRemoveSingleNoteNonZero() {
    Note note12 = new Note(1, 1, Pitch.D, 0);
    model.write(note12);
    assertEquals(model.getBeats().get(1).containsNote(note12), true);
    model.remove(note12);
    assertEquals(model.getBeats().get(0).getNotes().size(), 0);
    assertEquals(model.getBeats().size(), 2);
  }

  // Remove a long note at non zero
  @Test
  public void testRemoveLongNoteNonZero() {
    Note note12 = new Note(3, 2, Pitch.D, 0);
    model.write(note12);
    assertEquals(model.getBeats().get(2).containsNote(note12), true);
    assertEquals(model.getBeats().get(3).containsNote(note12), true);
    assertEquals(model.getBeats().get(4).containsNote(note12), true);
    model.remove(note12);
    assertEquals(model.getBeats().size(), 5);
    assertNotEquals(model.getBeats().get(2).containsNote(note12), true);
    assertNotEquals(model.getBeats().get(3).containsNote(note12), true);
    assertNotEquals(model.getBeats().get(4).containsNote(note12), true);
  }

  // Remove early note, have one later
  @Test
  public void testRemoveNotes() {
    Note note1 = new Note(10, 0, Pitch.C, 0);
    Note note2 = new Note(3, 3, Pitch.A, 0);
    Note note3 = new Note(2, 4, Pitch.D, 1);
    model.write(note1);
    model.write(note2);
    model.write(note3);
    model.remove(note1);
    assertEquals(model.getBeats().size(), 10);
    assertEquals(model.getBeats().get(0).getNotes().contains(note1), false);
  }

  // remove duplicate
  @Test
  public void testRemoveDuplicateNote() {
    Note note1 = new Note(1, 0, Pitch.C, 0);
    model.write(note1);
    model.write(note1);
    model.write(note1);
    model.remove(note1);
    assertEquals(model.getBeats().get(0).getNotes().size(), 2);
  }

  // remove duplicates 2
  @Test
  public void testRemoveDuplicateNote2() {
    Note note1 = new Note(1, 0, Pitch.C, 0);
    Note note2 = new Note(1, 0, Pitch.C, 0);
    model.write(note1);
    model.write(note2);
    model.remove(note1);
    assertEquals(model.getBeats().get(0).getNotes().size(), 1);
    assertEquals(model.getBeats().get(0).containsNote(note1), true);
    assertEquals(model.getBeats().get(0).containsNote(note2), true);
    model.remove(note1);

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

  // Editing similar notes: Replace single note from note size 2
  @Test
  public void testEditSimilarNotes() {
    Note note10 = new Note(2, 1, Pitch.A, 8);
    Note note11 = new Note(1, 1, Pitch.A, 8);
    model.write(note11);
    model.edit(note11, note10);
    assertEquals(model.getBeats().get(1).containsNote(note10), true);
    assertEquals(model.getBeats().size(), 3);
  }

  // Editing the same note
  @Test
  public void testEditSameNote() {
    Note note1 = new Note(1, 0, Pitch.A, 0);
    Note note2 = new Note(1, 0, Pitch.A, 0);
    model.write(note1);
    model.edit(note1, note2);
    assertEquals(model.getBeats().get(0).containsNote(note2), true);
    /*
    If we override .equals, then any two notes with the same fields are exactly the same,
    could this cause issues in the future?
     */
//    assertEquals(model.getBeats().get(0).containsNote(note1), false);
    assertEquals(model.getBeats().size(), 1);
    assertEquals(model.getBeats().get(0).getNotes().size(), 1);
  }

  /**************************** TESTS FOR COMBINE SEQUENTIAL. *********************************/

  @Test
  public void testCombine2PiecesConsecEasy() {
    MusicEditorModel model1 = new MusicEditorModel();
    MusicEditorModel model2 = new MusicEditorModel();
    Note A4 = new Note(2, 3, Pitch.A, 4);
    Note C4 = new Note(1, 0, Pitch.C, 4);
    model1.write(A4);
    model2.write(C4);
    ArrayList<MusicEditorModel> list = new ArrayList<>();
    list.add(model2);
    MusicEditorModel combo = model1.combine(list, CombineType.CONSECUTIVE);
    assertEquals(combo.getBeats().size(), 6);
    assertEquals(combo.getBeats().get(5).getNotes().size(), 1);
    System.out.print(combo.getEditorState());
  }

  @Test
  public void testCombine2PiecesSimulEasy() {
    MusicEditorModel combo = new MusicEditorModel();
    MusicEditorModel model1 = new MusicEditorModel();
    MusicEditorModel model2 = new MusicEditorModel();
    Note A4 = new Note(2, 0, Pitch.A, 4);
    Note C4 = new Note(1, 1, Pitch.C, 4);
    model1.write(A4);
    model2.write(C4);
    ArrayList<MusicEditorModel> list = new ArrayList<>();
    list.add(model1);
    list.add(model2);
    combo = combo.combine(list, CombineType.SIMULTANEOUS);
    System.out.print(combo.getEditorState());
  }

  @Test
  public void testFindExtreme() {
    Note Fs4 = new Note(5, 2, Pitch.FSharp, 4);
    Note G3 = new Note(2, 1, Pitch.G, 3);
    model.write(G3);
    model.write(Fs4);
    assertEquals(model.findExtreme(-1), G3);
    assertEquals(model.findExtreme(1), Fs4);
  }

  @Test
  public void testGetAllPossibleNotesInRange() {
    Note Fs4 = new Note(5, 2, Pitch.FSharp, 4);
    Note G3 = new Note(2, 1, Pitch.G, 3);
    model.write(G3);
    model.write(Fs4);
    System.out.print(model.getAllPossibleNotesInRange());
  }

  @Test
  public void testGetAllPossibleNotesInRange2() {
    Note A2 = new Note(5, 2, Pitch.A, 2);
    Note Gs3 = new Note(2, 1, Pitch.GSharp, 3);
    Note G1 = new Note(2, 1, Pitch.G, 1);
    Note F3 = new Note(2, 1, Pitch.F, 3);
    Note B2 = new Note(2, 1, Pitch.B, 2);
    model.write(A2);
    model.write(Gs3);
    model.write(G1);
    model.write(F3);
    model.write(B2);
    assertEquals(model.getAllPossibleNotesInRange().toString(), "[G1, G#1, A1, A#1, B1, C2, C#2, " +
            "D2, D#2, E2, F2, F#2, G2, G#2, A2, A#2, B2, C3, C#3, D3, D#3, E3, F3, F#3, G3, G#3]");

    //  assertEquals(model.getAllPossibleNotesInRange().toString(), "");
  }

  @Test
  public void testCombineSeq() {
    MusicEditorModel model2 = new MusicEditorModel();
    MusicEditorModel model3 = new MusicEditorModel();
    MusicEditorModel model4 = new MusicEditorModel();
    MusicEditorModel combo = new MusicEditorModel();

    Note G2 = new Note(1, 1, Pitch.G, 2);
    Note B2 = new Note(2, 3, Pitch.B, 2);

    Note Gs = new Note(1, 1, Pitch.GSharp, 2);
    Note A2 = new Note(3, 2, Pitch.A, 3);

    Note Ds = new Note(3, 2, Pitch.DSharp, 3);
    Note A3 = new Note(2, 3, Pitch.A, 3);

    model2.write(G2);
    model2.write(B2);

    model3.write(Gs);
    model3.write(A2);

    model4.write(Ds);
    model4.write(A3);

    List<MusicEditorModel> models = new ArrayList<>();
    models.add(model2);
    models.add(model3);
    models.add(model4);
    combo = combo.combine(models, CombineType.CONSECUTIVE);
    System.out.print(combo.getEditorState());

  }

  @Test
  public void testCombine2PiecesSimulHARD() {
    MusicEditorModel combo = new MusicEditorModel();
    MusicEditorModel model1 = new MusicEditorModel();
    MusicEditorModel model2 = new MusicEditorModel();
    Note A4 = new Note(2, 0, Pitch.A, 4);
    Note A5 = new Note(6, 0, Pitch.A, 5);
    Note B5 = new Note(4, 2, Pitch.B, 5);
    Note Gs4 = new Note(1, 0, Pitch.GSharp, 4);
    Note C4 = new Note(1, 1, Pitch.C, 4);
    model1.write(A4);
    model1.write(A5);
    model1.write(B5);
    model1.write(Gs4);
    model2.write(C4);
    ArrayList<MusicEditorModel> list = new ArrayList<>();
    list.add(model1);
    list.add(model2);
    combo = combo.combine(list, CombineType.SIMULTANEOUS);
    System.out.print(combo.getEditorState());
  }
}
