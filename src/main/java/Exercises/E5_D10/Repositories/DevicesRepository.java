package Exercises.E5_D10.Repositories;

import Exercises.E5_D10.Entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface DevicesRepository extends JpaRepository<Device, Integer> {
}