package br.com.amz.hostfillit.web.controller;

import br.com.amz.hostfillit.usecases.service.PropertyService;
import br.com.amz.hostfillit.web.dto.ResponseCreated;
import br.com.amz.hostfillit.web.dto.ResponseMessage;
import br.com.amz.hostfillit.web.dto.property.CreatePropertyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService service;

    public PropertyController(final PropertyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createProperty(@RequestBody final CreatePropertyDTO dto) {
        try {
            dto.validate();
            final var createdProperty = service.createProperty(dto.toModel());

            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseCreated(createdProperty.id()));
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
            }

            final var message = "Error while trying to create a property";
            return ResponseEntity.internalServerError().body(new ResponseMessage(message));
        }
    }
}
