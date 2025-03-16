package dh.backend.music_store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate dateInit;
    private LocalDate dateEnd;
    private int status;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private Users users;

    @OneToOne
    @JoinColumn(name = "product_id", unique = true)
    private Product product;
}
