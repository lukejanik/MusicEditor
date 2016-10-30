package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for MusicEditorModel.
 */

/**
 Documented changes:
 * The addNote(Note note) no longer throws an exception. If an exception were thrown, if the given
 * note already existed in the beat, the note would not be able to be added to the beat. Not
 * throwing the exception allows the note to be added. If the given note is longer than the note
 * that already exists at that beat, the note will just be extended the original accomodate for the
 * length of the given note.
 *
 * Abstracted the methods to find the highest and lowest notes to new method called findExtreme
 * (int). If given -1, findExtreme returns the lowest note in the model, if 1 is given,
 * findExtreme will return the highest note.
 *
 * Removed the Piece class because now a model represents a single piece. The combine methods now
 * take in MusicEditorModels instead of Pieces (of music) and combine them appropriately.
 */

public final class MusicEditorModel implements IMusicEditorModel {

  /**
   * A model has a list of beats, starting at beat 0. Each beat in the list has a list of notes.
   */
  private List<Beat> beats;

  /**
   * Constructor for the  model which initializes the beats to an empty list.
   */
  public MusicEditorModel() {
    this.beats = new ArrayList<>();
  }

  /**
   * Adds the note at its beat number to the given piece.
   * If the beat number does exist in the piece, the note is added to that beat and all beats
   * within the duration of the note.
   *
   * @param note  The note to add to the piece
   */
  @Override
  public void write(Note note) {
    if (note.getStartBeat() + note.getDuration() >= beats.size()) {
      for (int i = beats.size(); i < note.getStartBeat() + note.getDuration(); i += 1) {
        beats.add(new Beat());
      }

    }
    for (int i = note.getStartBeat(); i < note.getStartBeat() + note.getDuration(); i += 1) {
      beats.get(i).addNoteToBeat(note);
    }
  }

  /**
   * To replace an old note with a new note. If the oldNote is not found in the model, an
   * exception is thrown. If the newNote and the oldNote do not have the same startBeat, an
   * exception is thrown. This method is essentially a convenience method for writing and
   * removing a note simultaneously.
   *
   * @param oldNote the note that is to be replaced.
   * @param newNote the note that the oldNote will be replaced with.
   */
  @Override
  public void edit(Note oldNote, Note newNote) {
    for (int i = oldNote.getStartBeat(); i < oldNote.getStartBeat() + oldNote.getDuration(); i += 1) {
      if (!(beats.get(i).containsNote(oldNote))) {
        throw new IllegalArgumentException("MusicEditorModel does not contain the given oldNote.");
      }
    }
    if (oldNote.getStartBeat() != newNote.getStartBeat()) {
      throw new IllegalArgumentException("The newNote must have the same startBeat " +
              "as the oldNote.");
    }
    this.remove(oldNote);
    this.write(newNote);
  }

  /**
   * To remove a given note from the given piece. If the note is not found in the model, an
   * exception is thrown.
   *
   * @param note   note to be removed.
   */
  @Override
  public void remove(Note note) {
    for (int i = note.getStartBeat(); i < note.getStartBeat() + note.getDuration(); i += 1) {
      if (!beats.get(i).containsNote(note)) {
        throw new IllegalArgumentException("The MusicEditorModel does not contain the given note.");
      }
      beats.get(i).removeNoteFromBeat(note);
    }
  }

  /**
   * To combine a list of pieces either simultaneously or consecutively.
   *
   * @param type   The type of combination you want to make (simultaneous or consecutive).
   * @param models The list of MusicEditorModels that are to be combined.
   * @return MusicEditorModel a new model with the combined models from the list of models.
   */
  @Override
  public MusicEditorModel combine(List<MusicEditorModel> models, CombineType type) {

    MusicEditorModel combo = new MusicEditorModel();

    switch (type) {

      case CONSECUTIVE:
        int size = this.beats.size();
        for (int i = 0; i < models.size(); i++) {
          for (Beat beat : models.get(i).beats) {
            for (Note p : beat.getNotes()) {
              Note n = new Note(p.getDuration(), p.getStartBeat() + size, p.getPitch(), p.getOctave());
              this.write(n);
            }
          }
          size += models.get(i).beats.size();
        }
        return combo;

      case SIMULTANEOUS:
        for (MusicEditorModel m : models) {
          for (Beat b: m.getBeats()) {
            for (Note n: b.getNotes()) {
              combo.write(n);
            }
          }
        }
        return combo;

      default:
        throw new IllegalArgumentException("Invalid combination type");
    }
  }

  /**
   * To return a string of the state of this MusicEditorModel.
   * @return a string of the current state of this MusicEditorModel.
   */
  @Override
  public String getEditorState() {
    //StringBuilder builder = new StringBuilder();
    String finalStr = "";
    List<Note> allNotesInRange = this.getAllPossibleNotesInRange();
    int size = beats.size() - 1;
    String stringSize = Integer.toString(size);
    for (char c : stringSize.toCharArray()) {
      finalStr += " ";
    }
    for (int i = 0; i < allNotesInRange.size(); i += 1) {
      finalStr += String.format("%1$" + 4 + "s", allNotesInRange.get(i).toString()) + " ";
    }

    for (int j = 0; j < beats.size(); j += 1) {
      if (j == 0) {
        finalStr += '\n';
      }
      finalStr += String.format("%1$" + stringSize.length() + "s", j);
      for (int i = 0; i < allNotesInRange.size(); i += 1) {
        finalStr += beats.get(j).makeXandLines(j, allNotesInRange.get(i));
      }
      if (j != beats.size() - 1) {
        finalStr += '\n';
      }

    }
    return finalStr;
  }

  /**
   * FindExtreme returns the lowest note or highest note in the model given a comparison value.
   * If compValue = 1, the highest note is returned.
   * If compValue = -1, the lowest note is returned.
   * If compValue is any other integer, an exception is thrown.
   * @param compValue An int indicator to find the low or high.
   * @return The lowest or highest note in the model.
   * @throw IllegalArgumentException if compValue is an invalid integer.
   */
  public Note findExtreme(int compValue) {
    List<Note> allNotesInPiece = new ArrayList<>();

    if (compValue != 1 || compValue != -1) {
      throw new IllegalArgumentException("CompValue must be 1 or -1.");
    }

    for (int i = 0; i < beats.size(); i += 1) {
      for (Note n: beats.get(i).getNotes()) {
        if (n.getStartBeat() == i) {
          allNotesInPiece.add(n);
        }
      }
    }
    Note extremeNote = allNotesInPiece.get(0);
    for (int i = 1; i < allNotesInPiece.size(); i++) {
      if (extremeNote.compareTo(allNotesInPiece.get(i)) == compValue) {
        extremeNote = allNotesInPiece.get(i);
      }
    }

    return extremeNote;
  }

  /**
   * To generate a list of all the notes that are within the range of the highest and lowest notes
   * in this MusicEditorModel.
   * @return List<Note> </Note> of all the notes within the range of the highest and lowest notes
   * that could be in this MusicEditorModel.
   */
  public List<Note> getAllPossibleNotesInRange() {

    List<Note> allPossibleNotesInRange = new ArrayList<>();
    Note lowestNote = this.findExtreme(-1);
    Note highestNote = this.findExtreme(1);

    ArrayList<Pitch> pitches = new ArrayList<>();
    for (Pitch t : Pitch.values()) {
      pitches.add(t);
    }

    int loPitch = lowestNote.getPitch().getValue() - 1;
    int loOctave = lowestNote.getOctave();
    int hiPitch = highestNote.getPitch().getValue() - 1;
    int hiOctave = highestNote.getOctave();

    // Stay within the octave of the lowest note first
    if (loOctave == hiOctave) {
      for (int i = loPitch; i <= hiPitch; i++) {
        allPossibleNotesInRange.add(new Note(1, 0, pitches.get(i), loOctave));
      }
    }
    if (loOctave < hiOctave) {
      // FIRST OCTAVE
      for (int i = loPitch; i < pitches.size(); i++) {
        allPossibleNotesInRange.add(new Note(1, 0, pitches.get(i), loOctave));
      }
      // MIDDLE OCTAVES
      for (int i = loOctave + 1; i < hiOctave; i++) {
        for (Pitch p : pitches) {
          allPossibleNotesInRange.add(new Note(1, 0, p, i));
        }
      }
      // LAST OCTAVE
      for (int i = 0; i <= hiPitch; i++) {
        allPossibleNotesInRange.add(new Note(1, 0, pitches.get(i), hiOctave));
      }
    }
    return allPossibleNotesInRange;
  }

  /**
   * getter method that will be used to get necessary information for the View.
   * @return This model's list of beats.
   */
  public List<Beat> getBeats() {
    return new ArrayList<>(this.beats);
  }

  /**
   * getter method that will be used to get necessary information for the View.
   * @return This model's note range.
   */
  public List<Note> getNoteRange() {
    return new ArrayList<>(this.getAllPossibleNotesInRange());
  }


}