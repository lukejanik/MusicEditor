package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;



/**
 Documented changes.
 * Changed field name from musicSymbols to notes.
 * Changed method names and parameters to use "note" instead of "musicSymbols"
 */

/**
 * To represent a single beat of a song.
 */
public class Beat {
  /**
   * A beat has a list of notes that is in no particular order.
   */
  private List<Note> notes;

  /**
   * Constructor for the beat class initializes it's list of notes.
   */
  public Beat() {
    this.notes = new ArrayList<>();
  }

  /**
   * Add a note to this beat's list of notes.
   *
   * @param note the note to be added to this beat.
   */
  public void addNoteToBeat(Note note) {
    notes.add(note);
  }

  /**
   * Remove a note from this beat's list of notes.
   *
   * @param oldNote symbol to be removed.
   */
  public void removeNoteFromBeat(Note oldNote) {
    notes.remove(oldNote);
  }

  /**
   * @param note note to be checked if part of this beat.
   * @return boolean if the given note is part of this beat's list of notes.
   */
  public boolean containsNote(Note note) {
    return notes.contains(note);
  }

  /**
   * Add the notes of the given beat to this beat.
   * @param beat Beat who's notes will be added to this beat.
   */
  public void addNotes(Beat beat) {
    for (Note note : beat.notes) {
      notes.add(note);
    }
  }

  /**
   * Returns the appropriate string to the getState. If the note's start beat is equal to the
   * given startbeat, an X should be returned. If the note is note equal to the given start beat,
   * a | symbol should be returned.
   * @param beatIndex index of this beat in the piece.
   * @param n         note that is to be checked if an X or | should be used to represent it.
   * @return String output of a note depending on whether its starting beat is this beat.
   */
  public String makeXandLines(int beatIndex, Note n) {
    String finalStr = "";
    boolean noteExists = false;
    Note note = null;
    for (Note sym : notes) {
      if (n.getOctave() == sym.getOctave()
              && n.getPitch() == sym.getPitch()) {
        noteExists = true;
        note = sym;
      }
    }
    if (noteExists) {
      if (beatIndex == note.getStartBeat()) {
        finalStr = finalStr + "  X  ";
      } else {
        finalStr = finalStr + "  |  ";
      }
    } else {
      finalStr = finalStr + "     ";
    }
    return finalStr;
  }

  /**
   * getter method that returns this beat's list of notes.
   * @return List<Note> </Note> this beat's list of notes.
   * */
  public List<Note> getNotes() {
    return this.notes;
  }

}