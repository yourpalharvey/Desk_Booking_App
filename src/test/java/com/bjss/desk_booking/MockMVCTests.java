package com.bjss.desk_booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MockMVCTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestHomePagePath() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("home")));
    }

    // Won't work, need to do a security check first
    @Test
    public void getOfficeLocTest() throws Exception {
        this.mockMvc.perform(get("/user/quickbooking")
                        .with(SecurityMockMvcRequestPostProcessors.user("user").password("password").roles("USER")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Cardiff Office")));
    }


}
