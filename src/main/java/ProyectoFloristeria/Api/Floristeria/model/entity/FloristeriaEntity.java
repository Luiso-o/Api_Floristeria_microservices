package ProyectoFloristeria.Api.Floristeria.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class FloristeriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFloristeria;
    @Column(name = "Inaguracion")
    private LocalDate fechaApertura;
    @Column(name = "Pais_Sucursal")
    private String pais;
}
