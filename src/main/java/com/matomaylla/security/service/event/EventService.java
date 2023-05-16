package com.matomaylla.security.service.event;

import com.matomaylla.security.model.event.Event;
import com.matomaylla.security.model.event.EventRequest;
import com.matomaylla.security.model.event.EventResponse;
import com.matomaylla.security.repository.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    public EventResponse save(EventRequest request){
        var event = Event.builder().title(request.getTitle()).startDate(request.getStart())
                .endDate(request.getEnd()).build();
        Event eventResultado = repository.save(event);
        return EventResponse.builder().id(eventResultado.getId()).title(eventResultado.getTitle())
                .start(eventResultado.getStartDate()).end(eventResultado.getEndDate()).build();
    }

    public List<EventResponse> listar(){
        List<EventResponse> eventResultado = repository.findAll().stream().map(p-> EventResponse.builder().id(p.getId()).title(p.getTitle()).start(p.getStartDate()).end(p.getEndDate()).build()).collect(Collectors.toList());
       return eventResultado;
    }
}