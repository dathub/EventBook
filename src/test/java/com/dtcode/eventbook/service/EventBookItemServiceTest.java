package com.dtcode.eventbook.service;

import com.dtcode.eventbook.domain.EventBookItem;
import com.dtcode.eventbook.repository.EventBookItemRepository;
import com.dtcode.eventbook.repository.security.AppUserRepository;
import com.dtcode.eventbook.service.impl.EventBookItemServiceImpl;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventBookItemServiceTest {

    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private EventBookItemRepository eventBookItemRepository;
    @Mock
    private EventBookItemMapper eventBookItemMapper;
    private EventBookItemService underTestEventBookItemService;

    @BeforeEach
    void setUp() {
        underTestEventBookItemService
                = new EventBookItemServiceImpl(appUserRepository, eventBookItemRepository, eventBookItemMapper);
    }


    void saveEventBookItemTest() {
    }

    void updateEventBookItemTest() {
    }

    @Test
    void deleteByIdTest() {
        underTestEventBookItemService.deleteById(1l);
        verify(eventBookItemRepository).deleteById(1l);
    }

    void findAllEventBookItemsTest(){

    }

    @Test
    void findEventBookItemByIdTest() {
        //given
        long itemId = 1l;
        EventBookItem eventBookItem = EventBookItem.builder().id(itemId).build();
        when(eventBookItemRepository.findById(itemId)).thenReturn(Optional.ofNullable(eventBookItem));
        when(eventBookItemMapper.eventBookItemToDto(eventBookItem)).thenReturn(EventBookItemDTO.builder().build());
        //when
        underTestEventBookItemService.findEventBookItemById(itemId);
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
        given(eventBookItemRepository.findById(itemId)).willReturn(Optional.ofNullable(null));
        //when
        //then
        assertThatThrownBy(() -> underTestEventBookItemService.findEventBookItemById(itemId))
                .isInstanceOf(ResponseStatusException.class).hasMessageContaining("Not Found. Event Id: " + itemId);
    }


}
