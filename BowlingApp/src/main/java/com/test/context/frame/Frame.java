package com.test.context.frame;

/**
 * Created by svmen on 9/8/2021.
 * We all can be frames, why not..
 * Lets have a bit closer functions to share, then we update..
 */
public abstract class Frame {

  public static final int MAX_BONUS_COUNT = 2;
  public static final int MAX_FRAME_COUNT = 2;
  public static final int MAX_POSSIBLE = 10;
  public static final int FINAL_FRAME_MAX = 3;

  protected int firstTry;
  protected int secondTry;
  protected int frameTotal;

  public int getFirstTry() {
    return firstTry;
  }

  public int getSecondTry() {
    return secondTry;
  }

  public int getFrameTotal() {
    return frameTotal;
  }
}
