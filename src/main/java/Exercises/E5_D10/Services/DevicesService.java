package Exercises.E5_D10.Services;

import Exercises.E5_D10.Entities.Device;
import Exercises.E5_D10.Entities.User;
import Exercises.E5_D10.Exceptions.NotFoundException;
import Exercises.E5_D10.Payloads.NewDeviceDTO;

import Exercises.E5_D10.Repositories.DevicesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DevicesService {
    @Autowired
    private DevicesRepository devicesRepository;

    public void save(NewDeviceDTO body) throws IOException {

        Device newDevice = new Device();
        newDevice.setType(body.type());
        newDevice.setStatus(body.status());
        devicesRepository.save(newDevice);
    }

    public Page<Device> getDevices(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return devicesRepository.findAll(pageable);
    }

    public Device findById(int id) throws NotFoundException {
        return devicesRepository.findById(id).orElseThrow( ()  -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) throws NotFoundException{
        Device found = this.findById(id);
        devicesRepository.delete(found);
    }

    public Device findByIdAndUpdate(int id, Device body) throws NotFoundException{
        Device found = this.findById(id);
        found.setType(body.getType());
        found.setStatus(body.getStatus());
        found.setUser(body.getUser());
        return devicesRepository.save(found);
    }
}
