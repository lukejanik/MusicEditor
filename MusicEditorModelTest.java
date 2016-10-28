package cs3500.music.model;

import org.junit.Before;


/**
 * Created by ashnashah on 10/28/16.
 */
public class MusicEditorModelTest {
  MusicEditorModel model = new MusicEditorModel();
  Note ASharp2 = new Note(3, 0, Pitch.ASharp, 2);
  Note B2Beat2 = new Note(2, 2, Pitch.B, 2);
  Note B2Beat5 = new Note(1, 5, Pitch.B, 2);
  Note DSharp3Beat1 = new Note(3, 1, Pitch.DSharp, 3);
  Note DSharp3Beat6 = new Note(2, 6, Pitch.DSharp, 3);
  Note F3 = new Note(3, 6, Pitch.F, 3);
  Note GSharp3 = new Note(1, 10, Pitch.GSharp, 3);
  Note CSharp4 = new Note(2, 1, Pitch.CSharp, 4);
}
