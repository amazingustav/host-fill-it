package br.com.amz.hostfillit.web.controller;

import br.com.amz.hostfillit.usecases.service.AuthenticationService;
import br.com.amz.hostfillit.web.dto.ResponseMessage;
import br.com.amz.hostfillit.web.dto.authentication.SignInRequestDTO;
import br.com.amz.hostfillit.web.dto.authentication.SignInResponseDTO;
import br.com.amz.hostfillit.web.dto.authentication.SignUpRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService service;

    public AuthenticationController(final AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody final SignInRequestDTO signIn) {
        try {
            signIn.validate();
            final var token = service.signIn(signIn.username(), signIn.password());

            return ResponseEntity.ok(new SignInResponseDTO(token));
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
            }

            final var message = "Error while trying to sign in";

            return ResponseEntity.internalServerError().body(new ResponseMessage(message));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody final SignUpRequestDTO signUp) {
        try {
            signUp.validate();
            final var token = service.signUp(signUp.toModel());

            return ResponseEntity.ok(new SignInResponseDTO(token));
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
            }

            final var message = "Error while trying to sign up";

            return ResponseEntity.internalServerError().body(new ResponseMessage(message));
        }
    }
}