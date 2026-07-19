package wakeline.challenge.miserenim.unit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import wakeline.challenge.miserenim.strategy.OptimalStrategy;
import wakeline.challenge.miserenim.strategy.RandomStrategy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StrategyTest {

   private final RandomStrategy randomStrategy = new RandomStrategy();
   private final OptimalStrategy optimalStrategy = new OptimalStrategy();


   @RepeatedTest(100)
   void testMoveIsAlwaysWithinLegalBounds() {
      int[] testPiles = {1, 2, 3, 10, 50};

      for (int matches : testPiles) {
         int move = randomStrategy.calculateMove(matches);

         assertTrue(move >= 1, "Move should be at least 1");
         assertTrue(move <= 3, "Move should not be greater than 3");
         assertTrue(move <= matches, "Move should not exceed remaining matches");
      }
   }

   @Test
   void testMoveWhenOnlyOneMatchLeft() {
      assertEquals(1, randomStrategy.calculateMove(1));
   }

   @ParameterizedTest(name = "With {0} matches, expected move is {1}")
   @CsvSource({
           "1, 1", // Only 1 left: Must take 1
           "2, 1", // (2-1)%4 = 1. Move: 1
           "3, 2", // (3-1)%4 = 2. Move: 2
           "4, 3", // (4-1)%4 = 3. Move: 3
           "5, 1", // (5-1)%4 = 0. Move: 1
           "6, 1", // (6-1)%4 = 1. Move: 1
           "7, 2", // (7-1)%4 = 2. Move: 2
           "8, 3"  // (8-1)%4 = 3. Move: 3
   })
   void testOptimalMoves(int remainingMatches, int expectedMove) {
      assertEquals(expectedMove, optimalStrategy.calculateMove(remainingMatches));
   }

   @Test
   void testMoveNeverExceedsThree() {
      for (int i = 1; i < 100; i++) {
         int move = optimalStrategy.calculateMove(i);
         assertTrue(move >= 1 && move <= 3, "Move must be 1, 2, or 3");
      }
   }

}
