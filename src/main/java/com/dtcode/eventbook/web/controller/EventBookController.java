package com.dtcode.eventbook.web.controller;


import com.dtcode.eventbook.service.EventBookItemService;
import com.dtcode.eventbook.web.model.EventBookItemDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class EventBookController {

    private final EventBookItemService eventBookItemService;

    @GetMapping(path = {"event/{eventId}"}, produces = { "application/json" })
    public ResponseEntity<EventBookItemDTO> getEventBookItemById(@PathVariable("eventId") Long eventId){

        log.debug("Get Request for EventId: " + eventId);

        EventBookItemDTO eventBookItemDto = eventBookItemService.findEventBookItemById(eventId);
        return new ResponseEntity<>(eventBookItemDto, HttpStatus.OK);
    }

    //Add event
    @PostMapping(path = "event")
    public ResponseEntity saveNewEventBookItem(@Valid @RequestBody EventBookItemDTO eventBookItemDTO){

        EventBookItemDTO savedDto = eventBookItemService.saveEventBookItem(eventBookItemDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        //todo hostname for uri
        httpHeaders.add("Location", "localhost:8080/api/v1/event/" + savedDto.getId().toString());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    //Update event

    //delete event
}
