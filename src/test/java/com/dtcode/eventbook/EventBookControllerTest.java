package com.dtcode.eventbook;

import com.dtcode.eventbook.service.EventBookService;
import com.dtcode.eventbook.web.controller.EventBookController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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


@WebMvcTest(EventBookController.class)
public class EventBookControllerTest {

//    private MockMvc mockMvc;
//
//    @Autowired
//    private EventBookService eventBookService;
//
//    @InjectMocks
//    private EventBookController eventBookController;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(eventBookController)
//                .apply(SecurityMockMvcConfigurers.springSecurity())
////                .setControllerAdvice(new ExceptionHandlerControllerAdvice()) // If you have a custom exception handler
//                .build();
//    }
//
//    @Test
//    void findEventBookItems() throws Exception {
//        mockMvc.perform(get("/api/v1/events/"))
//                .andExpect(status().isUnauthorized());
//    }

//    @Test
//    public void testListEventBookItems() throws Exception {
//        // Mocking the service
//        EventBookItemDtoPage mockPage = new EventBookItemDtoPage(Collections.emptyList());
//        when(eventBookService.findAllEventBookItems(any(PageRequest.class), anyString())).thenReturn(mockPage);
//
//        // Performing GET request
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/events")
//                        .param("pageNumber", "0")
//                        .param("pageSize", "25")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").isArray());
//    }
//
//    @Test
//    public void testGetEventBookItemById() throws Exception {
//        long eventId = 1L;
//        EventBookItemDTO mockDto = new EventBookItemDTO();
//        when(eventBookService.findEventBookItemById(eventId)).thenReturn(mockDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/events/{eventId}", eventId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(eventId));
//    }
//
//    @Test
//    public void testSaveNewEventBookItem() throws Exception {
//        EventBookItemDTO eventBookItemDTO = new EventBookItemDTO();
//        EventBookItemDTO savedDto = new EventBookItemDTO();
//        savedDto.setId(1L);
//
//        when(eventBookService.saveEventBookItem(eventBookItemDTO)).thenReturn(savedDto);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/events")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(eventBookItemDTO)))
//                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", "localhost:8080/api/v1/events/1"));
//    }
//
//    @Test
//    public void testUpdateEventBookItem() throws Exception {
//        long eventId = 1L;
//        EventBookItemDTO eventBookItemDTO = new EventBookItemDTO();
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/events/{eventId}", eventId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(eventBookItemDTO)))
//                .andExpect(status().isNoContent());
//
//        verify(eventBookService).updateEventBookItem(eq(eventId), eq(eventBookItemDTO));
//    }
//
//    @Test
//    public void testDeleteEventBookItem() throws Exception {
//        long eventId = 1L;
//
//        mockMvc.perform(delete("/api/v1/events/{eventId}", eventId))
//                .andExpect(status().isNoContent());
//
//        verify(eventBookService).deleteById(eventId);
//    }

    // You can add more tests for exception handling if required
}
