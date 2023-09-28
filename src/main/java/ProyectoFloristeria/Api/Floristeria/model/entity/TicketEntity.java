package ProyectoFloristeria.Api.Floristeria.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "Tickets")
@Entity
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTicket;
    @Column(name = "fecha")
    private LocalDate fecha;
    @ManyToOne
    @JoinColumn(name = "idProducto")
    private ProductoEntity producto;
    @ManyToOne
    @JoinColumn(name = "idFloristeria")
    private FloristeriaEntity floristeria;
}
