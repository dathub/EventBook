package com.dtcode.eventbook.web.controller;


import com.dtcode.eventbook.service.EventBookItemService;
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
    private final EventBookItemService eventBookItemService;

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

        eventBookItemDtoPage = eventBookItemService.findAllEventBookItems(PageRequest.of(pageNumber, pageSize), date);

        return new ResponseEntity<>(eventBookItemDtoPage, HttpStatus.OK);
    }


    @GetMapping(path = {"events/{eventId}"}, produces = { "application/json" })
    public ResponseEntity<EventBookItemDTO> getEventBookItemById(@PathVariable("eventId") Long eventId){

        log.debug("Get Request for EventId: " + eventId);

        EventBookItemDTO eventBookItemDto = eventBookItemService.findEventBookItemById(eventId);
        return new ResponseEntity<>(eventBookItemDto, HttpStatus.OK);
    }

    //Add event
    @PostMapping(path = "events")
    public ResponseEntity saveNewEventBookItem(@Valid @RequestBody EventBookItemDTO eventBookItemDTO){

        EventBookItemDTO savedDto = eventBookItemService.saveEventBookItem(eventBookItemDTO);

        HttpHeaders httpHeaders = new HttpHeaders();

        //todo hostname for uri
        httpHeaders.add("Location", "localhost:8080/api/v1/event/" + savedDto.getId().toString());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    //Update event
    @PutMapping(path = {"events/{eventId}"}, produces = { "application/json" })
    public ResponseEntity updateEventBookItem(@PathVariable("eventId") Long eventId, @Valid @RequestBody EventBookItemDTO eventBookItemDTO){

        eventBookItemService.updateEventBookItem(eventId, eventBookItemDTO);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //delete event
    @DeleteMapping({"events/{eventId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventBookItem(@PathVariable("eventId") Long eventId){
        eventBookItemService.deleteById(eventId);
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> badReqeustHandler(ValidationException e){
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
