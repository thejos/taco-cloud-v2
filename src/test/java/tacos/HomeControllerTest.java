package tacos;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Dejan Smiljić - dej4n.s@gmail.com
 *
 */
@WebMvcTest
class HomeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testHomepage() {

		try {
			mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home"))
					.andExpect(content().string(containsString("Welcome to TACO !!!")));
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}// testHomePage() END
}