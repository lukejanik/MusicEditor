package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashnashah on 10/17/16.
 */
// To represent a beat in a song.
public class Beat {
  private List<Note> musicSymbols;

  public Beat() {
    this.musicSymbols = new ArrayList<Note>();
  }

  /**
   * Add a symbol to this beat.
   *
   * @param sym the symbol to be added to this beat.
   */
  public void addMusicSym(Note sym) {
    musicSymbols.add(sym);
  }

  /**
   * Remove a symbol from this beat.
   *
   * @param oldSym symbol to be removed.
   */
  public void removeSym(Note oldSym) {
    musicSymbols.remove(oldSym);
  }

  /**
   * @param sym symbol to be checked if part of this beat.
   * @return boolean if the given symbol is part of this beat's list of symbols.
   */
  public boolean containsSym(Note sym) {
    return musicSymbols.contains(sym);
  }

  /**
   * Add the musicSymbols of the given beat to this beat.
   *
   * @param beat Bea who's symbols will be added to this beat.
   */
  public void addSymbols(Beat beat) {
    for (Note note : beat.musicSymbols) {
      if (musicSymbols.contains(note)) {
        // do nothing
      }
      musicSymbols.add(note);
    }
  }


  /**
   * @param beatIndex index of this beat in the piece.
   * @param n         note that is to be checked if an X or | should be used to represent it.
   * @return String output of a note depending on whether its starting beat is this beat.
   */
  public String makeXandLines(int beatIndex, Note n) {
    String finalStr = "";
    boolean noteExists = false;
    Note note = null;
    for (Note sym : musicSymbols) {
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
   * @param startBeat In order to only add the unique notes in the beats.
   * @return List<Note></Note> of all the unique notes in the beat.
   */
  public List<Note> allNotesInBeat(int startBeat) {
    List<Note> notes = new ArrayList<>();
    for (Note sym : musicSymbols) {
        if (sym.getStartBeat() == startBeat) {
          notes.add((Note) sym);
        }
    }
    return notes;
  }

  public List<Note> getNotes() {
    return this.musicSymbols;
  }

}
