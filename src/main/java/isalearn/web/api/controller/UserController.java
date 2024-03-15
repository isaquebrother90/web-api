package isalearn.web.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import isalearn.web.api.model.User;
import isalearn.web.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<User> list(){
        return repository.findAll();
    }

    @PostMapping
    public void save(@RequestBody User user){
        repository.save(user);
    }
    @PutMapping
    public void update(@RequestBody User user){
        repository.save(user);
    }

    @Operation(summary = "Get a user by its username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid username",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content) })
    @GetMapping("/{username}")
    public User find(@PathVariable("username") String username){
        return repository.findByUsername(username);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id){
        repository.deleteById(id);
    }
}
