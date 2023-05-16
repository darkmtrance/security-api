package com.matomaylla.security.controller.event;

import com.matomaylla.security.controller.auth.AuthenticationController;
import com.matomaylla.security.model.event.Event;
import com.matomaylla.security.model.event.EventRequest;
import com.matomaylla.security.model.event.EventResponse;
import com.matomaylla.security.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService service;

    @PostMapping("/save")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<EventResponse> save(@RequestBody EventRequest request){
        logger.info(request.getTitle());
        logger.info(""+request.getStart());
        return ResponseEntity.ok(service.save(request));
    }

    @GetMapping("/listar")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<EventResponse>> listar(){
        return ResponseEntity.ok(service.listar());
    }
}
