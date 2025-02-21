package bankService.EffectiveMobile.controller;

import bankService.EffectiveMobile.configuration.JwtTokenUtils;
import com.Bank_EffectiveMobile.Bank_service.model.DAL.JwtRequest;
import com.Bank_EffectiveMobile.Bank_service.model.DAL.JwtResponse;
import com.Bank_EffectiveMobile.Bank_service.service.SecurityUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final SecurityUserService securityService;
    private final JwtTokenUtils jwtTokenUtils;
    @Autowired
    public AuthRestController(SecurityUserService securityService,
                              JwtTokenUtils tokenUtils,
                              AuthenticationManager authenticationManager)
    {
        this.securityService = securityService;
        this.jwtTokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public JwtResponse createAuthToken(@RequestBody JwtRequest request){
        log.info("Создан токен для авторизации__: " + request.toString());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()));
        } catch (BadCredentialsException e){
            log.error(e.getMessage());
        }
        UserDetails userDetails = securityService.loadUserByUsername(request.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        return new JwtResponse(token);
    }



    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<String> BadCredentialsExceptionHandler(BadCredentialsException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неправильный логин или пароль");
    }
}
