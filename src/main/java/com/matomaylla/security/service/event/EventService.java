package com.matomaylla.security.service.event;

import com.matomaylla.security.mapper.EventMapper;
import com.matomaylla.security.model.event.Event;
import com.matomaylla.security.model.event.EventRequest;
import com.matomaylla.security.model.event.EventResponse;
import com.matomaylla.security.repository.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    private final EventMapper eventMapper;

    public Boolean existeIdSieweb(Integer id){
        return !repository.findByIdSieweb(id).isEmpty();
    }

    public EventResponse save(EventRequest request){
        var event = Event.builder().title(request.getTitle()).startDate(request.getStart())
                .endDate(request.getEnd()).backgroundColor(request.getBackgroundColor()).textColor(request.getTextColor()).usuNomPublicacion(request.getUsuNomPublicacion()).idSieweb(request.getIdSieweb()).build();
        Event eventResultado = repository.save(event);
        return eventMapper.maptoEventResponse(eventResultado);
      }

    public EventResponse update(int id, EventRequest request){
        Event existingEvent = repository.findById(id).get();
        existingEvent.setStartDate(request.getStart());
        existingEvent.setEndDate(request.getEnd());
        existingEvent.setTitle(request.getTitle());
        existingEvent.setBackgroundColor(request.getBackgroundColor());
        existingEvent.setTextColor(request.getTextColor());
        Event eventResultado = repository.save(existingEvent);

        return eventMapper.maptoEventResponse(eventResultado);
    }

    public EventResponse updateByFields(int id, Map<String, Object> fields){
        Optional<Event> existingEvent = repository.findById(id);

        if (existingEvent.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Event.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, existingEvent.get(), value);
            });
            Event eventResultado = repository.save(existingEvent.get());

            return eventMapper.maptoEventResponse(eventResultado);
        }
        return null;

    }

    public List<EventResponse> listar(){
        List<EventResponse> eventResultado = repository.findAll().stream().map(p-> eventMapper.maptoEventResponse(p)).collect(Collectors.toList());
       return eventResultado;
    }

    public EventResponse obtenerEvento(Integer id){
        Optional<Event> existingEvent = repository.findById(id);
        if(existingEvent.isPresent()){
            return eventMapper.maptoEventResponse(existingEvent.get());
        }
        return null;

    }

    public void borrarEntidad(Integer id){
        repository.deleteById(id);
    }

    public Boolean existeEntidad(Integer id){
        return repository.existsById(id);
    }

    public List<EventResponse> paginacion(int offset, int limit){
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Event> page = repository.findAll(pageable);
        List<Event> listEvent = page.getContent();
        List<EventResponse> eventResultado = listEvent.stream().map(p-> eventMapper.maptoEventResponse(p)).collect(Collectors.toList());
        return eventResultado;
    }
}
