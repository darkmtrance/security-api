package com.matomaylla.security.mapper;

import com.matomaylla.security.model.event.Event;
import com.matomaylla.security.model.event.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
    @Mapping(source = "startDate", target = "start")
    @Mapping(source = "endDate", target = "end")
    EventResponse maptoEventResponse(Event event);

    @Mapping(source = "start", target = "startDate")
    @Mapping(source = "end", target = "endDate")
    Event maptoEvent(EventResponse eventResponse);

}
