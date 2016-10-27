package cs3500.music.model;

import java.util.List;

/**
 * This is the interface for an MusicEditorModel.
 */
public interface IMusicEditorModel {

  /**
   * Write adds the given note to the MusicEditorModel one note at a time.
   *
   * @param note  The note to add to the MusicEditorModel.
   */
  void write(Note note);

  /**
   * Edit replaces an existing note with a new note at a given beat number.
   * If the noteToReplace does not exist at the given beatNumber, an IllegalArgumentException
   * is thrown. If the beat number doesn't exist in the MusicEditorModel, an
   * IllegalArgumentException is also thrown.
   *
   * @param noteToReplace The note at the beat number that is to be replaced.
   * @param noteToAdd The note that is to replace the old note.
   * @throws IllegalArgumentException An exception is thrown if the noteToReplace does not exist or
   * the beat number does not exist.
   */
  void edit(Note noteToReplace, Note noteToAdd) throws IllegalArgumentException;

  /**
   * Removes the given note from the given piece.
   * If the note leaves empty spaces in the piece, subsequent notes shift to fill the piece.
   * Otherwise the the piece stays the same and no notes are shifted.
   *
   * @param note  The note to be removed.
   */
  void remove(Note note);

  /**
   * Combine multiple pieces together either simultaneously or consecutively.
   * If pieces are combined simultaneously merged the two pieces together.
   * If pieces are combined consecutively, the pieces are combined in the order of the of
   * the indices of the given arraylist.
   *
   * @param piecesToCombine An arraylist of all of the pieces to combine.
   * @param type            Either simultaneous or sequential, how the pieces are combined.
   * @return Piece which is a combination of the given pieces
   */
  MusicEditorModel combine(List<MusicEditorModel> piecesToCombine, CombineType type);

  /**
   * Returns the string representation of the current editor.
   * @return String which is the state.
   */
  String getEditorState();

}