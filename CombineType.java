package cs3500.music.model;

/**
 * Pieces of music can be combined in one of two ways, either sequentially or simultaneously.
 */
public enum CombineType {
  /**
   * If pieces of music are combined sequentially, one piece is played after the other ends.
   */
  CONSECUTIVE,
  /**
   * If pieces of music are played simultaneously, they are merged into one piece and played
   * at the same time.
   */
  SIMULTANEOUS,
}