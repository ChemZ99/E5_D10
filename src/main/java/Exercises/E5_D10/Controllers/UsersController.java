package Exercises.E5_D10.Controllers;

import Exercises.E5_D10.Entities.User;
import Exercises.E5_D10.Exceptions.BadRequestException;
import Exercises.E5_D10.Payloads.NewUserDTO;
import Exercises.E5_D10.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UsersService usersService;

    @GetMapping("")
    public Page<User> getUser(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy){
        return usersService.getUsers(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public User saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return usersService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping(value = "/{id}")
    public User findUserById(@PathVariable long id){
        return usersService.findById(id);
    }

    @PutMapping("/{id}")
    public User findUserByIdAndUpdate(@PathVariable long id, @RequestBody User body){
        return usersService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findUserByIdAndDelete(@PathVariable long id){
        usersService.findByIdAndDelete(id);
    }


    @PostMapping("/upload/{id}")
    public String uploadExample(@RequestParam("avatarUrl") MultipartFile body,@PathVariable long id) throws IOException {
        return usersService.uploadPicture(body,id);
    }
}
