package Exercises.E5_D10.Services;

import Exercises.E5_D10.Entities.User;
import Exercises.E5_D10.Exceptions.NotFoundException;
import Exercises.E5_D10.Payloads.NewUserDTO;
import com.cloudinary.Cloudinary;
import Exercises.E5_D10.Repositories.UsersRepository;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Cloudinary cloudinary;
    public void save(NewUserDTO body) throws IOException {

        User newUser = new User();
        newUser.setAvatarUrl("http://ui-avatars.com/api/?name="+body.name() + "+" + body.surname());
        newUser.setName(body.name());
        newUser.setSurname(body.surname());
        newUser.setEmail(body.email());
        usersRepository.save(newUser);
    }

    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return usersRepository.findAll(pageable);
    }

    public User findById(int id) throws NotFoundException {
        return usersRepository.findById(id).orElseThrow( ()  -> new NotFoundException(id));
    }

    public void findByIdAndDelete(int id) throws NotFoundException{
        User found = this.findById(id);
        usersRepository.delete(found);
    }

    public User findByIdAndUpdate(int id, User body) throws NotFoundException{
        User found = this.findById(id);
        found.setSurname(body.getSurname());
        found.setName(body.getName());
        return usersRepository.save(found);
    }

    public String uploadPicture(MultipartFile file) throws IOException {
        return (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    }

}
