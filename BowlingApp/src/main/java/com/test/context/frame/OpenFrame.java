package com.test.context.frame;

import java.util.Objects;

/**
 * Created by svmen on 9/8/2021.
 *
 * @Desc : Open frame wil hold the current frame being played. Will hold details on the score/bonus
 * etc..
 */
public class OpenFrame extends Frame {

  private int bonusComplete;
  private int numberOfTries;
  private final int frameNumber;

  private boolean isSpareBonusEligible;
  private boolean isStrikeBonusEligible;


  public OpenFrame(final int frameNumber){
    this.frameNumber = frameNumber;
  }

  /**
   * Update the frame for first roll.
   * @param roll
   */
  public void updateFirstTry(int roll){
    this.firstTry = roll;
    if(markStrike()){
      numberOfTries = MAX_FRAME_COUNT;
    }else {
      numberOfTries++;
    }
    frameTotal = frameTotal + roll;
  }

  /**
   * Update the frame for second roll.
   * @param roll
   */
  public void updateSecondTry(int roll){
    this.secondTry = roll;
    frameTotal =  frameTotal + roll;
    numberOfTries++;
  }

  /**
   * update bound when applicable.
   * @param bonus
   */
  public void updateFrameTotal(int bonus){
    frameTotal =  frameTotal + bonus;
    bonusComplete++;
  }

  public boolean markSpare(){
    if(getFirstTry()+getSecondTry()==MAX_POSSIBLE && !isStrikeBonusEligible){
      isSpareBonusEligible=true;
    }
    return isSpareBonusEligible;
  }

  public boolean isStrike(){
    return getFirstTry()== MAX_POSSIBLE;
  }

  public boolean isSpare(){
    return (getFirstTry()+getSecondTry()==MAX_POSSIBLE) && (!isStrikeBonusEligible);
  }

  public boolean markStrike(){
    if(getFirstTry()== MAX_POSSIBLE){
      isStrikeBonusEligible=true;
    }
    return isStrikeBonusEligible;
  }

  public boolean isFrameClosed(){
    return numberOfTries== MAX_FRAME_COUNT;
  }

  public int getFrameNumber(){
    return frameNumber;
  }

  public int getCurrenctFrameCount(){
    return numberOfTries;
  }

  public int getFrameTotal(){
    return frameTotal;
  }


  public boolean isBonusApplied(){
    return bonusComplete== MAX_BONUS_COUNT;
  }

  public int getBonusComplete() {
    return bonusComplete;
  }

  public int getNumberOfTries() {
    return numberOfTries;
  }

  public boolean isSpareBonusEligible() {
    return isSpareBonusEligible;
  }

  public boolean isStrikeBonusEligible() {
    return isStrikeBonusEligible;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OpenFrame openFrame = (OpenFrame) o;
    return firstTry == openFrame.firstTry &&
        secondTry == openFrame.secondTry &&
        numberOfTries == openFrame.numberOfTries &&
        getFrameNumber() == openFrame.getFrameNumber() &&
        getFrameTotal() == openFrame.getFrameTotal() &&
        isSpareBonusEligible == openFrame.isSpareBonusEligible &&
        isStrikeBonusEligible == openFrame.isStrikeBonusEligible;
  }

  @Override
  public int hashCode() {
    return Objects.hash(firstTry, secondTry, numberOfTries, getFrameNumber(), getFrameTotal(), isSpareBonusEligible, isStrikeBonusEligible);
  }

  @Override
  public String toString() {
    return "OpenFrame{" +
        "firstTry=" + firstTry +
        ", secondTry=" + secondTry +
        ", numberOfTries=" + numberOfTries +
        ", frameNumber=" + frameNumber +
        ", frameTotal=" + frameTotal +
        ", isSpareBonusEligible=" + isSpareBonusEligible +
        ", isStrikeBonusEligible=" + isStrikeBonusEligible +
        '}';
  }
}
