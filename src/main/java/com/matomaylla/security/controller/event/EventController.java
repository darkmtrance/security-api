package com.matomaylla.security.controller.event;

import com.matomaylla.security.model.event.EventRequest;
import com.matomaylla.security.model.event.EventResponse;
import com.matomaylla.security.service.event.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
@Tag(name = "Event", description = "Event management APIs")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    private final EventService service;

    @PostMapping("/save")
    @CrossOrigin
    @Operation(
            summary = "Retrieve a saved Event",
            description = "Save a Event object.",
            tags = { "Event" })
    public ResponseEntity<EventResponse> saveEvent(@RequestBody EventRequest request){
        return ResponseEntity.ok(service.save(request));
    }

    @PutMapping("/update/{id}")
    @CrossOrigin
    @Operation(
            summary = "Retrieve a updated Event",
            description = "Update a Event object.",
            tags = { "Event" })
    public  ResponseEntity<EventResponse> updateEvent(@PathVariable int id, @RequestBody EventRequest request){
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/update/{id}")
    @CrossOrigin
    @Operation(
            summary = "Retrieve a updated Event",
            description = "Update fields of a Event object.",
            tags = { "Event" })
    public  ResponseEntity<EventResponse> updateEventFields(@PathVariable int id, @RequestBody Map<String, Object> fields){
        EventResponse response = service.updateByFields(id, fields);
        if(response !=null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/listar")
    @CrossOrigin
    @Operation(
            summary = "Retrieve a Event List",
            description = "Get a Event List.",
            tags = { "Event" })
    public ResponseEntity<List<EventResponse>> listarEventos(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    @CrossOrigin
    @Operation(
            summary = "Retrieve a Event",
            description = "Get a single Event object.",
            tags = { "Event" })
    public  ResponseEntity<EventResponse> getEvent(@PathVariable int id){
        EventResponse response = service.obtenerEvento(id);
        if(response !=null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    @CrossOrigin
    @Operation(
            summary = "Retrieve a message of delete",
            description = "Delete a Event object by specifying its id.",
            tags = { "Event" })
    public ResponseEntity<String> delete(@PathVariable("id") Integer id){
        if(service.existeEntidad(id)) {
            service.borrarEntidad(id);
            return ResponseEntity.ok().body("Entidad eliminada correctamente");
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    @CrossOrigin
    @Operation(
            summary = "Retrieve a Event Pagination List",
            description = "Get a Event Pagination List.",
            tags = { "Event" })
    public ResponseEntity<List<EventResponse>> paginarEventos(@RequestParam(defaultValue = "0") int offset,
                                                              @RequestParam(defaultValue = "10") int limit){

        if(!service.paginacion(offset, limit).isEmpty()){
            return ResponseEntity.ok(service.paginacion(offset, limit));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
