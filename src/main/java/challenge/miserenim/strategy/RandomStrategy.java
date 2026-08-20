package challenge.miserenim.strategy;

import java.util.Random;

/**
 * Implements a random strategy for Misère Nim. The computer takes a random
 * number of matches between 1 and maxMove, where maxMove is the minimum of
 * 3 or the remaining matches.
 */
public class RandomStrategy implements ComputerStrategy {

   @Override
   public int calculateMove(int remainingMatches) {
      Random random = new Random();

      int maxMove = Math.min(3, remainingMatches);

      return random.nextInt(maxMove) + 1;
   }

}
