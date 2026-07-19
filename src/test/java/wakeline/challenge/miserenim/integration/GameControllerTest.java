package wakeline.challenge.miserenim.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;
import wakeline.challenge.miserenim.dto.GameCreationResponse;
import wakeline.challenge.miserenim.dto.GameResponse;
import wakeline.challenge.miserenim.game.GameStatus;
import wakeline.challenge.miserenim.game.Player;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

   @Autowired
   private MockMvc mockMvc;

   private static final ObjectMapper MAPPER = new ObjectMapper();

   @Test
   void testCreateGame() throws Exception {
      mockMvc.perform(post("/api/v1/misere-nim/create")
                      .param("matches", "5")
                      .param("player", "human")
                      .param("strategyType", "random")
              )
              .andExpect(status().isOk());
   }

   @Test
   void testCreateGameWithComputerToStart() throws Exception {
      GameCreationResponse gameCreationResponse = this.createGame("5", "computer", "stub");

      assertEquals(4, gameCreationResponse.initialMatches());
   }

   @Test
   void testExceptionOnInsufficientNumberOfMatches() throws Exception {
      mockMvc.perform(post("/api/v1/misere-nim/create")
                      .param("matches", "0")
                      .param("player", "human")
                      .param("strategyType", "random")
              )
              .andExpect(status().isBadRequest());
   }

   @Test
   void testExceptionOnIllegalPlayer() throws Exception {
      mockMvc.perform(post("/api/v1/misere-nim/create")
                      .param("matches", "0")
                      .param("player", "Any")
                      .param("strategyType", "random")
              )
              .andExpect(status().isBadRequest());
   }

   @Test
   void testCaseInsensitivePlayerString() throws Exception {
      mockMvc.perform(post("/api/v1/misere-nim/create")
                      .param("matches", "2")
                      .param("player", "Human")
                      .param("strategyType", "random")
              )
              .andExpect(status().isOk());
   }

   @Test
   void testGetGameState() throws Exception {
      GameCreationResponse creation = createGame("5", "human", "stub");

      MvcResult result = mockMvc.perform(get("/api/v1/misere-nim/" + creation.id()))
              .andExpect(status().isOk())
              .andReturn();

      String jsonResponse = result.getResponse().getContentAsString();
      GameResponse response = MAPPER.readValue(jsonResponse, GameResponse.class);

      assertEquals(creation.id(), response.id());
      assertEquals(5, response.matchesLeft());
   }

   @Test
   void testHumanMoveProcessing() throws Exception {
      GameCreationResponse gameCreationResponse = this.createGame("5", "human", "stub");

      MvcResult moveResult = mockMvc.perform(post("/api/v1/misere-nim/" + gameCreationResponse.id() + "/move")
                      .param("matches", "2")
              )
              .andExpect(status().isOk())
              .andReturn();

      String jsonResponseForMove = moveResult.getResponse().getContentAsString();
      GameResponse gameResponse = MAPPER.readValue(jsonResponseForMove, GameResponse.class);

      assertEquals(2, gameResponse.matchesLeft());
      assertEquals(2, gameResponse.moveHistory().size());
      assertEquals(Player.HUMAN, gameResponse.currentPlayer());
      assertEquals(GameStatus.IN_PROGRESS, gameResponse.status());
   }

   private GameCreationResponse createGame(String matches, String player, String strategy) throws Exception {
      MvcResult creationResult = mockMvc.perform(post("/api/v1/misere-nim/create")
                      .param("matches", matches)
                      .param("player", player)
                      .param("strategyType", strategy)
              )
              .andExpect(status().isOk())
              .andReturn();

      String jsonResponseForCreation = creationResult.getResponse().getContentAsString();

      return MAPPER.readValue(jsonResponseForCreation, GameCreationResponse.class);
   }

}
