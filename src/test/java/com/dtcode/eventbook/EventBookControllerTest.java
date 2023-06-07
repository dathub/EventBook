package com.dtcode.eventbook;

import com.dtcode.eventbook.service.EventBookService;
import com.dtcode.eventbook.web.controller.EventBookController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * LEARNING_NOTE:
 * The main difference between the @SpringBootTest and @WebMvcTest annotations lies in the application context that they create.
 * The @SpringBootTest annotation starts the full application context, which includes all the beans required for the application to function.
 * On the other hand, the @WebMvcTest annotation creates an application context with a limited number of beans, specifically those related to the Web Layer.
 *
 * The @SpringBootTest annotation is typically used for integration tests, where you need to test the whole application from a top-down perspective.
 * In contrast, the @WebMvcTest annotation is more focused and is used to test the Web MVC Layer, including controllers, filters,
 * and other components related to handling HTTP requests and responses. By limiting the number of beans in the application context,
 * @WebMvcTest can help you isolate the Web Layer and make tests faster and more focused.
 */


@SpringBootTest
public class EventBookControllerTest {

    @Autowired
    WebApplicationContext wac;
    private MockMvc mockMvc;

    public static Stream<Arguments> getStreamAdminUser() {
        return Stream.of(Arguments.of("TestAdmin" , "ad123"));
    }

    public static Stream<Arguments> getStreamNonAdminUser() {
        return Stream.of(Arguments.of("TestUser", "us123"));
    }
    public static Stream<Arguments> getStreamUnknownUser() {
        return Stream.of(Arguments.of("scott", "tiger"));
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @DisplayName("Find event book items tests")
    @Nested
    class findEventBookItems {
        @Test
        void findEventBookItems() throws Exception {
            mockMvc.perform(get("/api/v1/events"))
                    .andExpect(status().isUnauthorized());
        }

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.dtcode.eventbook.EventBookControllerTest#getStreamAdminUser")
        void findEventBookItemsWithAdminUser(String user, String pwd) throws Exception {
            mockMvc.perform(get("/api/v1/events").with(httpBasic(user, pwd)))
                    .andExpect(status().isOk());
        }

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.dtcode.eventbook.EventBookControllerTest#getStreamNonAdminUser")
        void findEventBookItemsWithNonAdminUser(String user, String pwd) throws Exception {
            mockMvc.perform(get("/api/v1/events").with(httpBasic(user, pwd)))
                    .andExpect(status().isOk());
        }

        @ParameterizedTest(name = "#{index} with [{arguments}]")
        @MethodSource("com.dtcode.eventbook.EventBookControllerTest#getStreamUnknownUser")
        void findEventBookItemsWithUnkonwnUser(String user, String pwd) throws Exception {
            mockMvc.perform(get("/api/v1/events").with(httpBasic(user, pwd)))
                    .andExpect(status().isUnauthorized());
        }
    }
}
