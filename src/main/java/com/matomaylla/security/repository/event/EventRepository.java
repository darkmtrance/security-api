package com.matomaylla.security.repository.event;

import com.matomaylla.security.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer>  {
}
