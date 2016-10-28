package cs3500.music.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by lukecjanik on 10/27/16.
 */
public class PitchTest {
  Pitch c = Pitch.C;
  Pitch ds = Pitch.DSharp;
  Pitch fs = Pitch.FSharp;
  Pitch b = Pitch.B;

  @Test
  public void testToString() {
    assertEquals(c.toString(), "C");
    assertEquals(ds.toString(), "D#");
    assertEquals(fs.toString(), "F#");
    assertEquals(b.toString(), "B");
  }

  @Test
  public void testValue() {
    assertEquals(c.getValue(), 1);
    assertEquals(ds.getValue(), 4);
    assertEquals(fs.getValue(), 7);
    assertEquals(b.getValue(), 12);
  }

  @Test
  public void testGetPitchBasedOnValue() {
    assertEquals(c.getPitchBasedOnValue(1), c);
    assertEquals(c.getPitchBasedOnValue(4), ds);
    assertEquals(c.getPitchBasedOnValue(8), Pitch.G);
    assertEquals(c.getPitchBasedOnValue(12), b);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetPitchBasedOnValueBad() {
    assertEquals(c.getPitchBasedOnValue(0), Pitch.C);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testGetPitchBasedOnValueBad2() {
    assertEquals(c.getPitchBasedOnValue(-1), Pitch.C);
  }
  @Test (expected = IllegalArgumentException.class)
  public void testGetPitchBasedOnValueBad3() {
    assertEquals(c.getPitchBasedOnValue(13), Pitch.C);
  }

}