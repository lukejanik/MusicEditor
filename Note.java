package cs3500.music.model;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created by ashnashah on 10/17/16.
 */
// To represent a note.
public class Note implements Comparable<Note> {
  private int duration;
  private int startBeat;
  private final Pitch pitch;
  private final int octave;

  /**
   *
   * @param duration the duration of this note.
   * @param startBeat the starting beat of this note.
   * @param pitch the pitch of this note.
   * @param octave the octave of this note.
   */
  public Note(int duration, int startBeat, Pitch pitch, int octave) {
    if (duration <= 0) {
      throw new IllegalArgumentException("duration can't be negative.");
    }
    if (startBeat < 0) {
      throw new IllegalArgumentException("startBeat can't be negative");
    }
    this.duration = duration;
    this.startBeat = startBeat;
    this.pitch = pitch;
    this.octave = octave;
  }

  /**
   * To construct a note of only the pitch and octave.
   * @param pitch the pitch of this note.
   * @param octave the octave of this note.
   */
  public Note(Pitch pitch, int octave) {
    this.pitch = pitch;
    this.octave = octave;
  }

  // To return the string value of this note.
  @Override
  public String toString() {
    return this.pitch.toString() + this.octave;
  }

  // To return the octave of this note.
  public int getOctave() {
    return this.octave;
  }

  // To return this note's pitch.
  public Pitch getPitch() {
    return this.pitch;
  }

  // To return this note's startBeat.
  public int getStartBeat() {
    return this.startBeat;
  }

  // To return this note's duration.
  public int getDuration() {
    return this.duration;
  }

  // To check if this note is equal to a given object.
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else if (!(o instanceof Note)) {
      return false;
    } else {
      Note that = ((Note) o);
      return this.octave == that.octave &&
              this.pitch == that.pitch &&
              this.duration == that.duration &&
              this.startBeat == that.startBeat;
    }
  }

  // Overriding the hashCode() method because overriding the equals() method.
  @Override
  public int hashCode() {
    return Objects.hash(this.octave);
  }

  @Override
  public int compareTo(Note note) {
    int ans = -1;
    if (this.octave > note.octave) {
      if (this.pitch.getValue() > note.pitch.getValue()) {
        return 1;
      }
    }
    else {
      return -1;
    }
    return ans;
  }
}