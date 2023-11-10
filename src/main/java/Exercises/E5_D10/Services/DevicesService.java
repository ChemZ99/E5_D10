package Exercises.E5_D10.Services;

import Exercises.E5_D10.Entities.Device;
import Exercises.E5_D10.Entities.User;
import Exercises.E5_D10.Exceptions.BadRequestException;
import Exercises.E5_D10.Exceptions.NotFoundException;
import Exercises.E5_D10.Payloads.NewDeviceDTO;

import Exercises.E5_D10.Repositories.DevicesRepository;

import Exercises.E5_D10.Repositories.UsersRepository;
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
    @Autowired
    private UsersRepository usersRepository;

    public Device save(NewDeviceDTO body) throws IOException {

        Device newDevice = new Device();
        newDevice.setType(body.type());
        newDevice.setStatus(body.status());
        devicesRepository.save(newDevice);
        return newDevice;
    }

    public Page<Device> getDevices(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return devicesRepository.findAll(pageable);
    }

    public Device findById(long id) throws NotFoundException {
        return devicesRepository.findById((int) id).orElseThrow( ()  -> new NotFoundException((int) id));
    }

    public void findByIdAndDelete(long id) throws NotFoundException{
        Device found = this.findById(id);
        devicesRepository.delete(found);
    }

    public Device findByIdAndUpdate(long id, Device body) throws NotFoundException{
        Device found = this.findById(id);
        found.setType(body.getType());
        found.setStatus(body.getStatus());
        found.setUser(body.getUser());
        return devicesRepository.save(found);
    }

    public void AssignDeviceToUser(long userId, long deviceId) throws NotFoundException{
        Device target = devicesRepository.findById((int) deviceId).orElseThrow( ()  -> new NotFoundException((int) deviceId));
        if (target.getStatus().equals("assegnato")) {
            throw new BadRequestException("il dispositivo e' gia assegnato");
        } else if (target.getStatus().equals("manutenzione")) {
            throw new BadRequestException("il dispositivo e' in manutenzione");
        } else if (target.getStatus().equals("dismesso")) {
            throw new BadRequestException("il dispositivo e' stato dismesso");
        } else if (target.getStatus().equals("disponibile")) {
            User owner = usersRepository.findById((int) userId).orElseThrow( ()  -> new NotFoundException((int) userId));
            target.setUser(owner);
            target.setStatus("assegnato");
            devicesRepository.save(target);
        }


    }
}
