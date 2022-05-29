package tech.devinhouse.devinhortifrutiapi.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.devinhouse.devinhortifrutiapi.configuration.TokenService;
import tech.devinhouse.devinhortifrutiapi.dto.LoginDTO;
import tech.devinhouse.devinhortifrutiapi.dto.TokenDTO;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public ResponseEntity<?> postLog(@RequestBody @Valid LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken login = loginDTO.converter();
        try{
            Authentication authentication = authenticationManager.authenticate(login);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        }
        catch (AuthenticationException | JsonProcessingException e){
            return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
        }
    }
}
