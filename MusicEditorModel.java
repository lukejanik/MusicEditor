package cs3500.music.model;


import java.util.ArrayList;

/**
 * This is the class for MusicEditorModel.
 */
public final class MusicEditorModel implements IMusicEditorModel {

  MusicEditorModel() {
    // No parameters.
  }

  /**
   * Adds the note at its beat number to the given piece.
   * If the beat number does not exist in the piece, the note is still added to the piece.
   * If the beat number does exist in the piece, the note is added to that beat and all beats
   * within the duration of the note.
   *
   * @param note  The note to add to the piece
   * @param piece The piece to add the note to
   */
  @Override
  public void write(Playable note, Piece piece) {
    piece.addNote(note);
  }

  @Override
  public void edit(Playable noteToReplace, Playable noteToAdd, Piece piece) {
    if (noteToAdd.getBeatNumber() != noteToReplace.getBeatNumber()) {
      throw new IllegalArgumentException("Invalid edit: beat numbers of both notes must match.");
    } else {
      piece.removeNote(noteToReplace);
      piece.addNote(noteToAdd);
    }
  }

  @Override
  public void remove(Playable note, Piece piece) {
    piece.removeNote(note);
  }

  @Override
  public Piece combine(ArrayList<Piece> piecesToCombine, CombineType type) {
    if (type.equals(CombineType.SEQUENTIAL)) {
      return combineSequential(piecesToCombine);
    }
    if (type.equals(CombineType.SIMULTANEOUS)) {
      return combineSimulteaneous(piecesToCombine);
    } else {
      throw new IllegalArgumentException("Invalid Combination Type.");
    }
  }

  /**
   * Add the beats one at a time from each piece, in order.
   *
   * @param piecesToCombine An arraylist of the pieces to combine.
   * @return Piece a new combined piece.
   */
  private Piece combineSequential(ArrayList<Piece> piecesToCombine) {
    Piece combo = new Piece();

    // Add the first
    for (Beat beat : piecesToCombine.get(0).beats) {
      for (Playable p : beat.notes) {
        combo.addNote(p);
      }
    }
    int size = piecesToCombine.get(0).beats.size();
    for (int i = 1; i < piecesToCombine.size(); i++) {
      for (Beat beat : piecesToCombine.get(i).beats) {
        for (Playable p : beat.notes) {
          Playable n = new Note(p.getPitch(), p.getOctave(), p.getDuration(), p.getBeatNumber() +
                  size);
          combo.addNote(n);
        }
      }
      size += piecesToCombine.get(i).beats.size();
    }

    return combo;
  }

  /**
   * Add the beats simultaneously.
   *
   * @param piecesToCombine An arraylist of the pieces to be combined.
   * @return Piece a new combined piece.
   */
  private Piece combineSimulteaneous(ArrayList<Piece> piecesToCombine) {
    int max = 0;
    for (Piece piece : piecesToCombine) {
      if (piece.beats.size() > max) {
        max = piece.beats.size();
      }
    }
    ArrayList<Beat> comboBeats = new ArrayList<>();
    for (int i = 0; i < max; i++) {
      comboBeats.add(new Beat());
    }
    Piece combo = new Piece();
    combo.beats = comboBeats;

    for (Piece piece : piecesToCombine) {
      for (int i = 0; i < piece.beats.size(); i++) {
        for (Playable note : piece.beats.get(i).notes) {
          combo.addNote(note);
        }
      }
    }
    return combo;
  }

  /**
   * Returns a string which is the current "state" of the music editor.
   *
   * @param piece one piece to display to the output.
   * @return String
   */
  public String getEditorState(Piece piece) {
    String state = "";
    state += piece.getState();
    return state;
  }

}
