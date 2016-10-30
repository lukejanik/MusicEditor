package cs3500.music.model;

import java.util.Objects;

/**
 * Documented changes.
 * - overrode compareTo in order to compare two notes. Used to find the highest and lowest notes
 * in the model.
 * - removed the Symbol abstract Class and Rest Class. We did this because we could not find a
 * solid reason to represents rests in a song, so we are left with a single Class to represent
 * notes. Because of this, we now have four paramters for a Note instead of just 2.
 */

/**
 * To represent a single note in a song.
 */
public class Note implements Comparable<Note> {
  /**
   * duration of a note measured in beats. A duration must be an integer greater than 0.
   */
  private int duration;
  /**
   * startBeat represents at which beat the note starts at. startBeat must be greater than or
   * equal to 0.
   */
  private int startBeat;
  /**
   * represents the pitch of a note, which must be one of 12 distinct pitches in the pitch
   * enumeration.
   */
  private final Pitch pitch;
  /**
   * represents the octave of this note, which can be any integer, positive or negative.
   */
  private final int octave;

  /**
   * Constructor for the Note class.
   * @param duration  the duration of this note.
   * @param startBeat the starting beat of this note.
   * @param pitch     the pitch of this note.
   * @param octave    the octave of this note.
   * @throws IllegalArgumentException if the duration is <= 0 or startBeat is < 0.
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
   * Returns the string representation of this Note. A Note is represented as a string as it's
   * Pitch followed by it's octave.
   */
  @Override
  public String toString() {
    return this.pitch.toString() + this.octave;
  }

  /**
   * Getter to return the octave of this Note.
   * @return the octave.
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Getter to return the Pitch of this Note.
   * @return the pitch.
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Getter to return the startBeat of this Note.
   * @return the startBeat.
   */
  public int getStartBeat() {
    return this.startBeat;
  }

  /**
   * Getter to return the duration of thie Note.
   * @return the duration.
   */
  public int getDuration() {
    return this.duration;
  }

  /**
   * Overriding equality -- two notes are equal if all of their fields are the same. It doesn't
   * matter that two notes of the same paramters exist, and one can be interchanged for the other.
   * @param o any object.
   * @return boolean true if the notes are the same, false otherwise.
   */
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

  /**
   * Overriding hashCode because equals is overridden.
   * @return a unique integer.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.octave);
  }

  /**
   * Overriding compareTo to compare two Notes.
   * CompareTo will return -1 if the given note is lower than this one.
   * CompareTo will return 1 if the given note is higher than this one.
   * @param note The note to be compared to.
   * @return an int representing if the given note is lower (-1) or not (1).
   */
  @Override
  public int compareTo(Note note) {
    if (this.octave > note.octave) {
      return -1;
    }
    if (this.octave == note.octave) {
      if (this.pitch.getValue() > note.pitch.getValue()) {
        return -1;
      }
      else {
        return 1;
      }
    }
    if (this.octave < note.octave) {
      return 1;
    }
    else {
      return -1;
    }
  }

}