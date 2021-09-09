package com.test.context;

import cern.colt.map.OpenIntObjectHashMap;
import com.test.context.frame.FinalFrame;
import com.test.context.frame.OpenFrame;
import java.util.LinkedList;

/**
 * Created by svmen on 9/8/2021.
 */
public abstract class Context {
  public OpenFrame getOpenFrameFromCacheOrNew(final OpenIntObjectHashMap gameFrameIntMap, final int gameFrameCounter) {
    OpenFrame openFrame = (OpenFrame) gameFrameIntMap.get(gameFrameCounter);

    if (openFrame == null) {
      openFrame = new OpenFrame(gameFrameCounter);
    }
    return openFrame;
  }

  public void updateMyFrame(final int roll, final OpenFrame openFrame) {
    if (openFrame.getCurrenctFrameCount() == 0) {
      openFrame.updateFirstTry(roll);
    } else {
      openFrame.updateSecondTry(roll);
    }
  }

  public boolean isFrameClosed(final OpenFrame openFrame) {
    return openFrame.isFrameClosed();
  }

  public boolean addBonusFrame(final OpenFrame openFrame, final LinkedList<OpenFrame> bonusFrames) {
    boolean wasAdded = false;
    if (openFrame.isStrike()) {
      bonusFrames.add(openFrame);
      wasAdded = true;
    }
    return wasAdded;
  }

  public FinalFrame getFinalFrameFromCache(final int gameFrameCounter, final OpenIntObjectHashMap gameFrameIntMap) {
    FinalFrame finalFrame = (FinalFrame) gameFrameIntMap.get(gameFrameCounter);

    if (finalFrame == null) {
      finalFrame = new FinalFrame(gameFrameCounter);
    }
    return finalFrame;
  }

  public void updateFinalFrameScores(final FinalFrame finalFrame,final int roll){
    if (finalFrame.getNumberOfTries() == 0) {
      finalFrame.updateFirstTry(roll);
    } else if (finalFrame.getNumberOfTries() == 1) {
      finalFrame.updateSecondTry(roll);
    } else {
      finalFrame.updateThirdTry(roll);
    }
  }
}

