package com.dtcode.eventbook.service.impl;

import com.dtcode.eventbook.domain.security.AppUser;
import com.dtcode.eventbook.domain.EventBookItem;
import com.dtcode.eventbook.repository.security.AppUserRepository;
import com.dtcode.eventbook.utils.EventBookUtils;
import com.dtcode.eventbook.web.model.EventBookItemDTO;
import com.dtcode.eventbook.web.model.EventBookItemDtoPage;
import com.dtcode.eventbook.web.model.dtomapper.EventBookItemMapper;
import com.dtcode.eventbook.repository.EventBookItemRepository;
import com.dtcode.eventbook.service.EventBookItemService;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dtcode.eventbook.utils.EventBookUtils.isValidDate;
import static com.dtcode.eventbook.utils.EventBookUtils.isValidYear;

@Service
@AllArgsConstructor
public class EventBookItemServiceImpl implements EventBookItemService {

    private final AppUserRepository appUserRepository;
    private final EventBookItemRepository eventBookItemRepository;
    private final EventBookItemMapper eventBookItemMapper;

    @Override
    public EventBookItemDTO saveEventBookItem(EventBookItemDTO eventBookItemDTO) {
        EventBookItem eventBookItem = eventBookItemMapper.dtoToEventBookItem(eventBookItemDTO, getLoggedInUser());
        EventBookItem savedEventBookItem = eventBookItemRepository.save(eventBookItem);
        return eventBookItemMapper.eventBookItemToDto(savedEventBookItem);
    }

    @Override
    public void updateEventBookItem(Long eventBookItemId, EventBookItemDTO eventBookItemDTO) {
        Optional<EventBookItem> eventBookItem = eventBookItemRepository.findById(eventBookItemId);
        eventBookItem.ifPresentOrElse( item -> {
            item.setTitle(eventBookItemDTO.getTitle());
            item.setDescription(eventBookItemDTO.getDescription());
            item.setDate(eventBookItemDTO.getDate());
            eventBookItemRepository.save(item);
        }, () -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. Event Id: " + eventBookItemId);
        });
    }

    @Override
    public void deleteById(Long eventBookItemId) {
        eventBookItemRepository.deleteById(eventBookItemId);
    }

    @Override
    public EventBookItemDTO findEventBookItemById(Long eventBookItemId) {
        Optional<EventBookItem> eventBookItem = eventBookItemRepository.findById(eventBookItemId);
        if(eventBookItem.isPresent()) {
            return eventBookItemMapper.eventBookItemToDto(eventBookItem.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. Event Id: " + eventBookItemId);
        }
    }

    @Override
    public EventBookItemDtoPage findAllEventBookItems(PageRequest pageRequest, String date) throws ValidationException {

        Page<EventBookItem> eventBookItemPage = null;

        if (date == null) {
            eventBookItemPage = eventBookItemRepository.findAllByAppUser(getLoggedInUser(), pageRequest);
        } else if (isValidDate(date)) {
            eventBookItemPage = eventBookItemRepository.findAllByAppUserAndDate(getLoggedInUser(), EventBookUtils.getDate(date), pageRequest);
        } else if (isValidYear(date)) {
            LocalDate dateStart = EventBookUtils.getDate(date + "-01-01");
            LocalDate dateEnd = EventBookUtils.getDate(date + "-12-31");
            eventBookItemPage = eventBookItemRepository.findAllByAppUserAndDateBetween(getLoggedInUser(), dateStart, dateEnd, pageRequest);
        } else {
            throw new ValidationException("Incorrect date format: " + date + ". Expected date format is " + EventBookUtils.DefaultDateFormat + " or yyyy");
        }

        return getEventBookItemDtoPage(eventBookItemPage);
    }

    private EventBookItemDtoPage getEventBookItemDtoPage(Page<EventBookItem> eventBookItemPage) {
        return new EventBookItemDtoPage(
                eventBookItemPage
                        .getContent()
                        .stream()
                        .map(eventBookItemMapper::eventBookItemToDto)
                        .collect(Collectors.toList()),
                PageRequest.of(
                        eventBookItemPage.getPageable().getPageNumber(),
                        eventBookItemPage.getPageable().getPageSize()),
                eventBookItemPage.getTotalElements());
    }

    private AppUser getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)(authentication.getPrincipal());
        Optional<AppUser> appUser = appUserRepository.findByUserName(user.getUsername());
        if(appUser.isPresent()) {
            return appUser.get();
        }
        return null;
    }
}
