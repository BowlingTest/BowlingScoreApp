package com.test.context;

import cern.colt.map.OpenIntObjectHashMap;
import com.test.context.frame.FinalFrame;
import com.test.context.frame.OpenFrame;
import com.test.exceptions.InValidRollException;
import java.util.LinkedList;

/**
 * Created by svmen on 9/8/2021.
 */
public class PlayerContext extends Context {

  public static final int FRAME_MAX_SIZE = 30;
  public static final String CURRENT_GAME_TOTAL = "Current Game Total ";
  private static final int MAX_SIZE = 10;
  public static final int FINAL_FRAME_COUNT = 9;

  //Keep a rolling list of bonus eligible frames
  private final LinkedList<OpenFrame> bonusFrames;

  //Open source map, keep the object cost low.with primitive keys.
  private final OpenIntObjectHashMap gameFrameIntMap;
  private int gameFrameCounter = 0;
  private int currentTotal = 0;

  public PlayerContext() {
    bonusFrames = new LinkedList<>();
    gameFrameIntMap = new OpenIntObjectHashMap();
  }

  /**
   * @Dssc: Core logic that will calculate and keep the track of the score card. Strike : Strike + (
   * next two frame total ) Spare : Spare + ( next half frame total ) Regular : Cumulative of each
   * frame. Final Frame is special : Best case 3 turns /worst case 2 turns. Metod also keeps a
   * Rolling total to inform the user on each roll.
   */
  public int updateOpenFrame(final int roll) throws InValidRollException {
    OpenFrame previousBonusEligibleFrame;

    if (roll < 0) {
      throw new InValidRollException("Rolled pins cannot be negative ");
    }

    if (gameFrameIntMap.size() == 0) {
      System.out.println("Starting a new Game ");
    }

    if (gameFrameCounter >= FINAL_FRAME_COUNT) {
      //Update calculation for final Roll and store in the cache.
      calculateFinalFrame(roll);

    } else {

      OpenFrame currentOpenFrame = getOpenFrameFromCacheOrNew(gameFrameIntMap, gameFrameCounter);
      updateMyFrame(roll, currentOpenFrame);
      gameFrameIntMap.put(gameFrameCounter, currentOpenFrame);
      boolean isFrameClosed = isFrameClosed(currentOpenFrame);

      if (isFrameClosed) {

        addBonusFrame(currentOpenFrame, bonusFrames);
        previousBonusEligibleFrame = updateAndCheckFrameIfEligibleTo(currentOpenFrame);
        OpenFrame bonusEligibleFrame = (OpenFrame) gameFrameIntMap.get(gameFrameCounter - 1);
        checkAndUpdateForStrike(bonusEligibleFrame, previousBonusEligibleFrame, currentOpenFrame);
        checkAndUpdateForSpare(bonusEligibleFrame, currentOpenFrame);

        currentTotal = currentTotal + currentOpenFrame.getFrameTotal();
        //System.out.println(CURRENT_GAME_TOTAL + currentTotal);
        gameFrameCounter++;
      }
    }
    return currentTotal;
  }

  private void calculateFinalFrame(final int roll) {
    FinalFrame finalFrame = getFinalFrameFromCache(gameFrameCounter, gameFrameIntMap);

    updateFinalFrameScores(finalFrame, roll);

    checkAndUpdateFinalFrameForStrikeBonus(finalFrame);

    gameFrameIntMap.put(gameFrameCounter, finalFrame);

    //finally add final frame to the total.
    if (finalFrame.isFrameClosed()) {

      if (finalFrame.isStrike() || finalFrame.isSpare()) {
        currentTotal = currentTotal + finalFrame.getFrameTotal();
      } else {
        currentTotal = currentTotal + finalFrame.getFirstTry() + finalFrame.getSecondTry();
      }
      gameFrameCounter++;
    }
  }


  /**
   * @Desc : Update Rolling score.
   */
  private OpenFrame updateBonusEligibleFrames(final FinalFrame finalFrame,
      final OpenFrame openFrame) {
    OpenFrame bonusEligible = bonusFrames.getFirst();
    int frameTotal;
    if (finalFrame != null) {
      frameTotal = finalFrame.getFrameTotal();
      currentTotal = currentTotal + frameTotal;
      bonusEligible.updateFrameTotal(frameTotal);
    } else {
      frameTotal = openFrame.getFrameTotal();
      currentTotal = currentTotal + frameTotal;
      bonusEligible.updateFrameTotal(frameTotal);
    }
    return bonusEligible;
  }

  private OpenFrame updateAndCheckFrameIfEligibleTo(final OpenFrame openFrame) {
    OpenFrame bonusEligible = null;
    if (bonusFrames.size() > 1) {
      OpenFrame bonusEligibleFrame = bonusFrames.getFirst();
      bonusEligible = updateBonusEligibleFrames(null, openFrame);
      if (bonusEligibleFrame.isBonusApplied()) {
        bonusFrames.remove(bonusEligibleFrame);
      }
    }
    return bonusEligible;
  }

  private void checkAndUpdateForStrike(final OpenFrame bonusEligibleFrame,
      final OpenFrame previousUpdatedFrame,
      final OpenFrame currentFrame) {
    if (bonusEligibleFrame != null && bonusEligibleFrame.isStrike()) {
      if (!bonusEligibleFrame.equals(previousUpdatedFrame)) {
        bonusEligibleFrame.updateFrameTotal(currentFrame.getFrameTotal());
        currentTotal = currentTotal + currentFrame.getFrameTotal();
      }
    }
  }

  private void checkAndUpdateFinalFrameForStrikeBonus(final FinalFrame finalFrame) {
    if (finalFrame.isStrike()) {
      if (bonusFrames.size() > 0) {
        OpenFrame bonusEligible = updateBonusEligibleFrames(finalFrame, null);
        if (bonusEligible.getFrameTotal() != FRAME_MAX_SIZE) {
          if (bonusEligible.isBonusApplied()) {
            bonusFrames.remove(bonusEligible);
          }
        } else {
          bonusFrames.remove(bonusEligible);
        }
      }
    }
  }

  private void checkAndUpdateForSpare(final OpenFrame bonusEligibleFrame,
      final OpenFrame currentFrame) {
    if (bonusEligibleFrame != null && bonusEligibleFrame.isSpareBonusEligible()) {
      bonusEligibleFrame.updateFrameTotal(currentFrame.getFirstTry());
      currentTotal = currentTotal + currentFrame.getFirstTry();
    }
  }

  //Test Method, in case if any one wants to debug.
  private int getDisplayToday(final OpenIntObjectHashMap gameFrameIntMap, boolean isFinal) {
    int gameSize = gameFrameIntMap.size();
    int total = 0;
    for (int i = 0; i < gameSize; i++) {
      if (i != FINAL_FRAME_COUNT) {
        OpenFrame openFrame = (OpenFrame) gameFrameIntMap.get(i);
        total = total + openFrame.getFrameTotal();
      }
    }
    if (isFinal) {
      FinalFrame finalFrame = (FinalFrame) gameFrameIntMap.get(FINAL_FRAME_COUNT);
      total = total + finalFrame.getFrameTotal();
    }
    System.out.println("Frame total is " + total);
    return total;
  }

  public int getGameSize() {
    return gameFrameCounter;
  }
}
