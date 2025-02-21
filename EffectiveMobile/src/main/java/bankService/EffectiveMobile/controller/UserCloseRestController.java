package bankService.EffectiveMobile.controller;

import com.Bank_EffectiveMobile.Bank_service.model.entity.UserEntity;
import com.Bank_EffectiveMobile.Bank_service.exception.BadRequestParametersException;
import com.Bank_EffectiveMobile.Bank_service.model.DAL.FilterParameters;
import com.Bank_EffectiveMobile.Bank_service.service.UserService;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/close/search")
public class UserCloseRestController {
    private UserService userService;
    @Autowired
    public UserCloseRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param parameters {
     * parameter "date" must be valid  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")
     * parameter "number" must be valid @Pattern(regexp = "\\d{1}-\\d{3}-\\d{3}-\\d{2}-\\d{2}")
     * parameter "name" must be filled in together with other "ФИО" parameters.
     * parameter "lastName" must be filled in together with other "ФИО" parameters.
     * parameter "surname" must be filled in together with other "ФИО" parameters - this field means "отчество".
     * parameter "email" must be valid like @Email
     * }
     * @return
     */
    @PostMapping
    public List<UserEntity> findUsersWithFilter(@Valid @RequestBody FilterParameters parameters){
        return userService.getUserByParameters(parameters);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoResultException.class)
    private ResponseEntity<String> NoResultExceptionHandler(NoResultException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("no result");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestParametersException.class)
    private ResponseEntity<String> BadRequestParametersExceptionHandler(BadRequestParametersException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
