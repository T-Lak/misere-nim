package wakeline.challenge.miserenim.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

   @Autowired
   private MockMvc mockMvc;

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

}
