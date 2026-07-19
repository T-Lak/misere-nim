package wakeline.challenge.miserenim.strategy;

import lombok.Getter;

@Getter
public enum StrategyType {
   OPTIMAL(new OptimalStrategy()),
   RANDOM(new RandomStrategy()),
   STUB(new StubStrategy());

   private final ComputerStrategy strategy;

   StrategyType(ComputerStrategy strategy) {
      this.strategy = strategy;
   }

}
