package challenge.miserenim.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;
import challenge.miserenim.dto.GameCreationRequest;
import challenge.miserenim.dto.GameCreationResponse;
import challenge.miserenim.dto.GameResponse;
import challenge.miserenim.dto.MoveRequest;
import challenge.miserenim.game.GameStatus;
import challenge.miserenim.game.Player;

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
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(MAPPER.writeValueAsString(new GameCreationRequest(5, "human", "random")))
              )
              .andExpect(status().isOk());
   }

   @Test
   void testCreateGameWithComputerToStart() throws Exception {
      GameCreationResponse gameCreationResponse = this.createGame(5, "computer", "stub");

      assertEquals(4, gameCreationResponse.initialMatches());
   }

   @Test
   void testExceptionOnInsufficientNumberOfMatches() throws Exception {
      mockMvc.perform(post("/api/v1/misere-nim/create")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(MAPPER.writeValueAsString(new GameCreationRequest(0, "human", "random")))
              )
              .andExpect(status().isBadRequest());
   }

   @Test
   void testExceptionOnIllegalPlayer() throws Exception {
      mockMvc.perform(post("/api/v1/misere-nim/create")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(MAPPER.writeValueAsString(new GameCreationRequest(0, "Any", "random")))
              )
              .andExpect(status().isBadRequest());
   }

   @Test
   void testCaseInsensitivePlayerString() throws Exception {
      mockMvc.perform(post("/api/v1/misere-nim/create")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(MAPPER.writeValueAsString(new GameCreationRequest(2, "Human", "random")))
              )
              .andExpect(status().isOk());
   }

   @Test
   void testGetGameState() throws Exception {
      GameCreationResponse creation = createGame(5, "human", "stub");

      MvcResult result = mockMvc.perform(get("/api/v1/misere-nim/" + creation.id()))
              .andExpect(status().isOk())
              .andReturn();

      GameResponse response = MAPPER.readValue(result.getResponse().getContentAsString(), GameResponse.class);

      assertEquals(creation.id(), response.id());
      assertEquals(5, response.matchesLeft());
   }

   @Test
   void testHumanMoveProcessing() throws Exception {
      GameCreationResponse creation = this.createGame(5, "human", "stub");

      GameResponse gameResponse = this.makeMove(creation.id(), 2);

      assertEquals(2, gameResponse.matchesLeft());
      assertEquals(2, gameResponse.moveHistory().size());
      assertEquals(Player.HUMAN, gameResponse.currentPlayer());
      assertEquals(GameStatus.IN_PROGRESS, gameResponse.status());
   }

   private GameCreationResponse createGame(int matches, String player, String strategy) throws Exception {
      MvcResult result = mockMvc.perform(post("/api/v1/misere-nim/create")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(MAPPER.writeValueAsString(new GameCreationRequest(matches, player, strategy)))
              )
              .andExpect(status().isOk())
              .andReturn();
      return MAPPER.readValue(result.getResponse().getContentAsString(), GameCreationResponse.class);
   }

   private GameResponse makeMove(String gameId, int matches) throws Exception {
      MvcResult result = mockMvc.perform(post("/api/v1/misere-nim/" + gameId + "/move")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(MAPPER.writeValueAsString(new MoveRequest(matches)))
              )
              .andExpect(status().isOk())
              .andReturn();
      return MAPPER.readValue(result.getResponse().getContentAsString(), GameResponse.class);
   }

}
