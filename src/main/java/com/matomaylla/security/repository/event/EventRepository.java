package com.matomaylla.security.repository.event;

import com.matomaylla.security.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer>  {

    List<Event> findByIdSieweb(Integer Sieweb);
}
