package com.dtcode.eventbook.web.controller;


import com.dtcode.eventbook.domain.security.permission.*;
import com.dtcode.eventbook.service.EventBookService;
import com.dtcode.eventbook.web.model.EventBookItemDTO;
import com.dtcode.eventbook.web.model.EventBookItemDtoPage;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class EventBookController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    private final EventBookService eventBookService;

    //Requires ADMIN or USER roles
    @EventBookItemReadPermission
    @GetMapping(path = {"events"}, produces = { "application/json" })
    public ResponseEntity<EventBookItemDtoPage> listEventBookItems(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                         @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                         @RequestParam(value = "date", required = false) String date) {

        EventBookItemDtoPage eventBookItemDtoPage = null;

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        eventBookItemDtoPage = eventBookService.findEventBookItemsForCurrentUser(PageRequest.of(pageNumber, pageSize), date);
        return new ResponseEntity<>(eventBookItemDtoPage, HttpStatus.OK);
    }


    //Requires ADMIN or USER roles
    @EventBookItemReadPermission
    @GetMapping(path = {"events/{eventId}"}, produces = { "application/json" })
    public ResponseEntity<EventBookItemDTO> getEventBookItemById(@PathVariable("eventId") Long eventId){

        log.debug("Get Request for EventId: " + eventId);
        EventBookItemDTO eventBookItemDto = eventBookService.findEventBookItemByIdForCurrentUser(eventId);
        return new ResponseEntity<>(eventBookItemDto, HttpStatus.OK);
    }

    //Requires ADMIN or USER roles
    //Add event
    @EventBookItemCreatePermission
    @PostMapping(path = "events")
    public ResponseEntity saveNewEventBookItem(@Valid @RequestBody EventBookItemDTO eventBookItemDTO){

        EventBookItemDTO savedDto = eventBookService.saveEventBookItemForCurrentUser(eventBookItemDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        //todo hostname for uri
        httpHeaders.add("Location", "localhost:8080/api/v1/event/" + savedDto.getId().toString());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    //Update event
    //Requires ADMIN or USER roles
    @EventBookItemUpdatePermission
    @PutMapping(path = {"events/{eventId}"}, produces = { "application/json" })
    public ResponseEntity updateEventBookItem(@PathVariable("eventId") Long eventId, @Valid @RequestBody EventBookItemDTO eventBookItemDTO){

        eventBookService.updateEventBookItemForCurrentUser(eventId, eventBookItemDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //delete event
    //Requires ADMIN or USER roles
    @EventBookItemDeletePermission
    @DeleteMapping({"events/{eventId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventBookItem(@PathVariable("eventId") Long eventId){
        eventBookService.deleteEventBookItemByIdForCurrentUser(eventId);
    }

    //Requires ADMIN role
    @EventBookItemReadAllPermission
    @GetMapping(path = {"events/all"}, produces = { "application/json" })
    public ResponseEntity<EventBookItemDtoPage> listAllEventBookItems(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                                      @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                                      @RequestParam(value = "userName", required = false) String userName,
                                                                      @RequestParam(value = "date", required = false) String date) {

        EventBookItemDtoPage eventBookItemDtoPage = null;

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        eventBookItemDtoPage = eventBookService.findEventBookItemsForAllUsers(PageRequest.of(pageNumber, pageSize), date);
        return new ResponseEntity<>(eventBookItemDtoPage, HttpStatus.OK);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> badReqeustHandler(ValidationException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
