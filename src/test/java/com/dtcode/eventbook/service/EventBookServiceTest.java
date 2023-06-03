package com.dtcode.eventbook.service;

import com.dtcode.eventbook.domain.EventBookItem;
import com.dtcode.eventbook.repository.EventBookItemRepository;
import com.dtcode.eventbook.repository.security.AppUserRepository;
import com.dtcode.eventbook.service.impl.EventBookServiceImpl;
import com.dtcode.eventbook.web.model.EventBookItemDTO;
import com.dtcode.eventbook.web.model.dtomapper.EventBookItemMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventBookServiceTest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private EventBookItemRepository eventBookItemRepository;
    @Mock
    private EventBookItemMapper eventBookItemMapper;
    private EventBookService underTestEventBookService;

    @BeforeEach
    void setUp() {
        underTestEventBookService
                = new EventBookServiceImpl(appUserRepository, eventBookItemRepository, eventBookItemMapper);
    }


    void saveEventBookItemTest() {
    }

    void updateEventBookItemTest() {
    }

    @Test
    void deleteByIdTest() {
        underTestEventBookService.deleteEventBookItemByIdForCurrentUser(1l);
        verify(eventBookItemRepository).deleteById(1l);
    }

    void findAllEventBookItemsTest(){

    }

    @Test
    void findEventBookItemByIdTest() {
        //given
        long itemId = 1l;
        EventBookItem eventBookItem = EventBookItem.builder().id(itemId).build();
        when(eventBookItemRepository.findById(anyLong())).thenReturn(Optional.ofNullable(eventBookItem));
        when(eventBookItemMapper.eventBookItemToDto(eventBookItem)).thenReturn(EventBookItemDTO.builder().build());
        //when
        underTestEventBookService.findEventBookItemByIdForCurrentUser(itemId);
        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(eventBookItemRepository).findById(argumentCaptor.capture());
        Long capturedValue = argumentCaptor.getValue();
        assertThat(capturedValue).isEqualTo(itemId);
    }


    @Test
    void findEventBookItemByIdWhenItemIdNotexistsTest() {
        //given
        long itemId = 1l;
        given(eventBookItemRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        //when
        //then
        assertThatThrownBy(() -> underTestEventBookService.findEventBookItemByIdForCurrentUser(itemId))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("Not Found. Event Id: " + itemId);

//        verify(eventBookItemRepository, never()).findById(any());
    }


}
