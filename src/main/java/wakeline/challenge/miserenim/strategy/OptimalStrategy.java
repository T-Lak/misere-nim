package wakeline.challenge.miserenim.strategy;

/**
 * Implements the optimal strategy for Misère Nim. It forces the opponent
 * to play with an amount of matches with a remainder of 1. If not in an
 * initial losing state, this is a winning strategy.
 */
public class OptimalStrategy implements ComputerStrategy {

   @Override
   public int calculateMove(int remainingMatches) {
      int target = (remainingMatches - 1) % 4;
      return (target == 0) ? 1 : target;
   }

}
