package com.test.context.frame;

import java.util.Objects;

/**
 * Created by svmen on 9/8/2021.
 *
 * @Desc : Final Frames, are specail. So we will just make object to hold details. this will be
 * useful for debugging and make the code more simple to understand.
 *
 * Final Frames: let you do three tries.
 */
public class FinalFrame extends Frame {

  private final int frameNumber;
  private int bonusComplete;
  private int thirdTry;
  private int numberOftries;

  public FinalFrame(final int frameNumber) {
    this.frameNumber = frameNumber;
  }

  public boolean isBonusApplied() {
    return bonusComplete == MAX_BONUS_COUNT;
  }

  public void updateFrameTotal(final int bonus) {
    frameTotal = frameTotal + bonus;
    bonusComplete++;
  }

  public void updateFirstTry(final int firstTry) {
    this.firstTry = firstTry;
    frameTotal = firstTry;
    numberOftries++;
  }

  public void updateSecondTry(final int secondTry) {
    this.secondTry = secondTry;
    frameTotal = frameTotal + secondTry;
    numberOftries++;
  }

  public void updateThirdTry(final int thirdTry) {
    this.thirdTry = thirdTry;
    frameTotal = frameTotal + thirdTry;
    numberOftries++;
  }

  public boolean isFrameClosed() {
    return numberOftries == FINAL_FRAME_MAX;
  }

  public boolean isSpare() {
    int total = firstTry + secondTry;
    return total == MAX_POSSIBLE;
  }

  public boolean isStrike() {
    return firstTry == MAX_POSSIBLE;
  }

  public int getNumberOfTries() {
    return numberOftries;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof FinalFrame)) {
      return false;
    }
    FinalFrame that = (FinalFrame) o;
    return frameNumber == that.frameNumber &&
        bonusComplete == that.bonusComplete &&
        thirdTry == that.thirdTry &&
        numberOftries == that.numberOftries;
  }

  @Override
  public int hashCode() {
    return Objects.hash(frameNumber, bonusComplete, thirdTry, numberOftries);
  }

  @Override
  public String toString() {
    return "FinalFrame{" +
        "frameNumber=" + frameNumber +
        ", bonusComplete=" + bonusComplete +
        ", thirdTry=" + thirdTry +
        ", numberOftries=" + numberOftries +
        '}';
  }
}
