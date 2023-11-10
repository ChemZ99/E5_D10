package Exercises.E5_D10.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String type;
    @Column
    private String status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
