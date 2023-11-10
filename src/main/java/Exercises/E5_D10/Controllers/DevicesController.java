package Exercises.E5_D10.Controllers;

import Exercises.E5_D10.Entities.Device;
import Exercises.E5_D10.Entities.User;
import Exercises.E5_D10.Exceptions.BadRequestException;
import Exercises.E5_D10.Payloads.NewDeviceDTO;
import Exercises.E5_D10.Payloads.NewUserDTO;
import Exercises.E5_D10.Services.DevicesService;
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
@RequestMapping("/devices")
public class DevicesController {
    @Autowired
    private DevicesService devicesService;

    @GetMapping("")
    public Page<Device> getDevices(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String orderBy){
        return devicesService.getDevices(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Device saveDevice(@RequestBody @Validated NewDeviceDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return devicesService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @GetMapping(value = "/{id}")
    public Device findDeviceById(@PathVariable long id){
        return devicesService.findById(id);
    }

    @PutMapping("/{id}")
    public Device findDeviceByIdAndUpdate(@PathVariable long id, @RequestBody Device body){
        return devicesService.findByIdAndUpdate(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findDeviceByIdAndDelete(@PathVariable long id){
        devicesService.findByIdAndDelete(id);
    }

    @PutMapping("/assign/device={deviceId}&user={userId}")
    public void assignDevice(@PathVariable long deviceId, @PathVariable long userId){
        devicesService.AssignDeviceToUser(userId,deviceId);
    }
}