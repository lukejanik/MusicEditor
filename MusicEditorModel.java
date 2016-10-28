package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for MusicEditorModel.
 */

/**
 Changes made:
 * The addNote(Note note) no longer throws an exception. If an exception were thrown, if the given
 * note already existed in the beat, the note would not be able to be added to the beat. Not
 * throwing the exception allows the note to be added. If the given note is longer than the note
 * that already exists at that beat, the note will just be extended to accomodate for the length
 * of the given note.
 *
 * Abstracted the methods to find the highest and lowest notes
 */

public final class MusicEditorModel implements IMusicEditorModel {

  // The beats in this piece of music.
  private List<Beat> beats;

  MusicEditorModel() {
    this.beats = new ArrayList<>();
  }

  /**
   * Adds the note at its beat number to the given piece.
   * If the beat number does not exist in the piece, the note is still added to the piece.
   * If the beat number does exist in the piece, the note is added to that beat and all beats
   * within the duration of the note.
   *
   * @param note  The note to add to the piece
   */
  @Override
  public void write(Note note) {
    this.addNote(note);
  }

  /**
   * To replace an old note with a new note.
   *
   * @param oldNote the note that is to be replaced.
   * @param newNote the note that the oldNote will be replaced with.
   */
  @Override
  public void edit(Note oldNote, Note newNote) {
    this.editNote(oldNote, newNote);
  }

  /**
   * To remove a given note from the given piece.
   *
   * @param note   note to be removed.
   */
  @Override
  public void remove(Note note) {
    this.removeNote(note);
  }

  /**
   * To combine a list of pieces either simultaneously or consecutively.
   *
   * @param type   The type of combination you want to make (simultaneous or consecutive).
   * @param models The list of MusicEditorModels that are to be combined.
   */
  @Override
  public MusicEditorModel combine(List<MusicEditorModel> models, CombineType type) {
    MusicEditorModel combo = new MusicEditorModel();
    switch (type) {
      case CONSECUTIVE:
        combo.appendModels(models);
        return combo;

      case SIMULTANEOUS:
        int maxLength = 0;
        for (MusicEditorModel p : models) {
          if (p.size() > maxLength) {
            maxLength = p.size();
          }
        }
        combo.addBeats(maxLength);
        for (MusicEditorModel m : models) {
          combo.addNotesToModel(m);
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
    return this.setUpState();
  }

  /**
   * Adds the given note to this MusicEditorModel. If the note that is being added already exists
   * in this MusicEditorModel, not throwing an exception would basically ignore it and add only
   * if it's longer than the existing note. Throwing the exception would prevent you from
   * combining music if two notes appear in the same place.
   * @param note
   */
  private void addNote(Note note) {
    if (note.getStartBeat() + note.getDuration() >= beats.size()) {
      for (int i = beats.size(); i < note.getStartBeat() + note.getDuration(); i += 1) {
        beats.add(new Beat());
      }

    }
    for (int i = note.getStartBeat(); i < note.getStartBeat() + note.getDuration(); i += 1) {
      beats.get(i).addNoteToBeat(note);
    }
  }

  // Returns the size of this piece's list of beats.
  private int size() {
    return this.beats.size();
  }

  /**
   * Edits this MusicEditorModel by replacing the given oldNote with the given newNote.
   * Throws an exception if this MusicEditorModel does not contain the oldNote at the beat
   * or the startBeat of the oldNote and newNote are not the same.
   * @param oldNote The oldNote that is to be replaced.
   * @param newNote The newNote that replaces the oldNote.
   * @throws IllegalArgumentException Throws an exception if the oldNote does not exist at the
   * beat or if the oldNote and newNote do not have the same startBeat.
   */
  private void editNote(Note oldNote, Note newNote) throws IllegalArgumentException {
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
    this.addNote(newNote);
  }

  /**
   * To remove the given note from this MusicEditorModel.
   * @param note The given note that is to be removed from this MusicEditorModel.
   * @throws IllegalArgumentException Throws an exception if the given note does not exist at the
   * beat that is it's startBeat in this MusicEditorModel.
   */
  private void removeNote(Note note) throws IllegalArgumentException {
    for (int i = note.getStartBeat(); i < note.getStartBeat() + note.getDuration(); i += 1) {
      if (!beats.get(i).containsNote(note)) {
        throw new IllegalArgumentException("The MusicEditorModel does not contain the given note.");
      }
      beats.get(i).removeNoteFromBeat(note);
    }
  }

  /**
   * To add beats to this piece's list of beats.
   *
   * @param maxLength The length of the list of beats that will be created for this piece.
   */
  private void addBeats(int maxLength) {
    for (int i = 0; i < maxLength; i += 1) {
      this.beats.add(new Beat());
    }
  }

  /**
   * To add all the notes from the given MusicEditorModel to this MusicEditorModel.
   *
   * @param  model The MusicEditorModel whose notes will be added to this MusicEditorModel.
   */
  private void addNotesToModel(MusicEditorModel model) {
    for (int i = 0; i < model.beats.size(); i += 1) {
      this.beats.get(i).addNotes(model.beats.get(i));
    }
  }


  /**
   * To add the beats from all the given pieces to this piece.
   * @param models The models to be appended.
   */
  private void appendModels(List<MusicEditorModel> models) {
    // Add the first
    for (Beat beat : models.get(0).beats) {
      for (Note note : beat.getNotes()) {
        this.addNote(note);
      }
    }
    int size = models.get(0).beats.size();
    for (int i = 1; i < models.size(); i++) {
      for (Beat beat : models.get(i).beats) {
        for(Note n : beat.getNotes()) {
          Note newNote = new Note(n.getDuration(), n.getStartBeat() + size,
                  n.getPitch(), n.getOctave());
          this.addNote(newNote);
        }
      }
      size += models.get(i).beats.size();
    }
  }

  // To find the lowest or highest note in this MusicEditorModel.
  private Note findExtreme(int compValue) {
    List<Note> allNotesInPiece = new ArrayList<>();
    for (int i = 0; i < beats.size(); i += 1) {
      allNotesInPiece.addAll(beats.get(i).allNotesInBeat(i));
    }
    Note extremeNote = allNotesInPiece.get(0);
    for (int i = 0; i < allNotesInPiece.size(); i += 1) {
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
  private List<Note> getAllPossibleNotesInRange() {
    List<Note> allPossibleNotesInRange = new ArrayList<>();
    Note lowestNote = this.findExtreme(1);
    Note highestNote = this.findExtreme(-1);
    // Stay within the octave of the lowest note first
    for (int i = lowestNote.getPitch().getValue(); i <= highestNote.getPitch().getValue(); i += 1) {
      Note n1 = new Note(lowestNote.getPitch().getPitchBasedOnValue(i), lowestNote.getOctave());
      allPossibleNotesInRange.add(n1);
    }
    for (int j = lowestNote.getOctave() + 1; j < highestNote.getOctave(); j += 1) {
      int nextOctave = j;
      if (nextOctave < highestNote.getOctave()) {
        for (Pitch p : Pitch.values()) {
          Note n2 = new Note(p, nextOctave);
          allPossibleNotesInRange.add(n2);
        }
      }
    }
    if (lowestNote != highestNote) {
      for (int i = lowestNote.getPitch().getValue(); i <= highestNote.getPitch().getValue(); i += 1) {
        Note n3 = new Note(highestNote.getPitch().getPitchBasedOnValue(i), highestNote.getOctave());
        allPossibleNotesInRange.add(n3);
      }
    }
    return allPossibleNotesInRange;
  }

  /**
   * @return String of the current state of this piece.
   */
  private String setUpState() {
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

  public List<Beat> getBeats() {
    return new ArrayList<>(this.beats);
  }

  public List<Note> getNoteRange() {
    return new ArrayList<>(this.getAllPossibleNotesInRange());
  }



}