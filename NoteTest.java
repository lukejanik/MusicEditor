package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for Note class.
 */
public class NoteTest {

  Note middleC = new Note(1, 0, Pitch.C, 4);
  Note lowA = new Note(3, 3, Pitch.A, -10);
  Note highFs = new Note(5, 2, Pitch.FSharp, 999);

  @Test
  public void testToString() {
    assertEquals(middleC.toString(), "C4");
    assertEquals(lowA.toString(), "A-10");
    assertEquals(highFs.toString(), "F#999");
  }

  @Test
  public void testGetPitch() {
    assertEquals(middleC.getPitch(), Pitch.C);
    assertEquals(lowA.getPitch(), Pitch.A);
    assertEquals(highFs.getPitch(), Pitch.FSharp);
  }

  @Test
  public void testGetOctave() {
    assertEquals(middleC.getOctave(), 4);
    assertEquals(lowA.getOctave(), -10);
    assertEquals(highFs.getOctave(), 999);
  }

  @Test
  public void testGetBeatNumber() {
    assertEquals(middleC.getStartBeat(), 0);
    assertEquals(lowA.getStartBeat(), 3);
    assertEquals(highFs.getStartBeat(), 2);
  }

  /**
   * Beat number must be greater than or equal to zero.
   * Here it is -10.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBeatNumber() {
    Note badNote = new Note(4, -10, Pitch.A, 5);
  }

  @Test
  public void testGetDuration() {
    assertEquals(middleC.getDuration(), 1);
    assertEquals(lowA.getDuration(), 3);
    assertEquals(highFs.getDuration(), 5);
  }

  /**
   * Duration of -10, but duration must be a positive integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDuration() {
    Note badNote2 = new Note(0, 4, Pitch.C, 5);
  }

  /**
   * Duration of 0, but duration must be a positive integer.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDuration2() {
    Note badNote2 = new Note(0, 4, Pitch.C, 5);
  }


}