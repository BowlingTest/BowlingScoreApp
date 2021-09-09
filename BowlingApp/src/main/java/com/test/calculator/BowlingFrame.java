package com.test.calculator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by svmen on 9/8/2021.
 */
public class BowlingFrame {

  private final Map<Integer,List<Integer>> bowlingFrames = new ConcurrentHashMap();
  private static LinkedList<Integer> rolls = new LinkedList<>();
  private static final Map<Integer,List<Integer>> possibleFrames = new HashMap<>();

  public static final int MAX_FRAMES_COUNT = 11;

  static{
    for(int i=0;i<MAX_FRAMES_COUNT;i++){
      rolls.add(i);
    }
    for(int count = 0; count < MAX_FRAMES_COUNT;count++){
      List<Integer> frame = getSecondFrame(count);
      possibleFrames.put(count,frame);
    }
  }

  private static List<Integer> getSecondFrame(final int roll){
    LinkedList<Integer> tempRolls = (LinkedList<Integer>)rolls.clone();
    for(int i =0;i<roll;i++){
      tempRolls.removeLast();
    }
    return (List<Integer>) tempRolls.clone();
  }

  public static List<Integer> getFrame(final int roll){
    return possibleFrames.get(roll);
  }

  public static void printFrame(){
    System.out.println(rolls.toString());
  }

  /**
   * In case there is aneed to just check..
   */
//  public static void main(String args[]){
//    BowlingFrame bowlingFrame = new BowlingFrame();
//  }

}
