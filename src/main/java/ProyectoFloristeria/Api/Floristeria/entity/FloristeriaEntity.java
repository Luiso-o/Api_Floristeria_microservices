package ProyectoFloristeria.Api.Floristeria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Floristeria")
@Entity
public class FloristeriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFloristeria;
    @Column(name = "Nombre")
    private String nombre;
    @Column(name = "Inaguracion")
    private LocalDate fechaApertura;
    @Column(name = "Pais_Sucursal")
    private String pais;
}
