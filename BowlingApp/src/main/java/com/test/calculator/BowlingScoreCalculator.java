package com.test.calculator;

import static com.test.context.PlayerContext.CURRENT_GAME_TOTAL;

import com.test.context.PlayerContext;
import com.test.exceptions.InValidRollException;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by svmen on 9/8/2021.
 */
public class BowlingScoreCalculator {

  private final Map<String, PlayerContext> playerContextMap;
  private final Scanner scanner;
  private static final int MAX_SCORE = 300;
  private static final int MAX_FRAMES = 10;
  private PlayerContext playerContext;

  public BowlingScoreCalculator() {
    playerContextMap = new ConcurrentHashMap();
    scanner = new Scanner(System.in);
  }

  public int roll(final int roll, final String gameName) {
    playerContext = playerContextMap.getOrDefault(gameName, new PlayerContext());
    playerContextMap.putIfAbsent(gameName, playerContext);
    if (roll < 0) {
      System.out.println("Bad Roll,Invalid Input");
      try {
        throw new InValidRollException("Bad Roll, Invalid Input");
      } catch (InValidRollException e) {
        e.printStackTrace();
      }
    }
    int currentScore = 0;
    try {
      currentScore = playerContext.updateOpenFrame(roll);
      System.out.println(CURRENT_GAME_TOTAL + currentScore);

    } catch (InValidRollException e) {
      e.printStackTrace();
    }
    return currentScore;
  }

  public static void main(String args[]) {
    BowlingScoreCalculator calculator = new BowlingScoreCalculator();

    try {
      System.out.println("Enter Player Name ");
      String name = calculator.scanner.next();
      System.out.println("Enter the pins rolled ");
      while (calculator.scanner.hasNext()) {
        int roll = calculator.scanner.nextInt();
        int score = calculator.roll(roll, name);
        if (score == MAX_SCORE || calculator.playerContext.getGameSize() == MAX_FRAMES) {
          break;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
