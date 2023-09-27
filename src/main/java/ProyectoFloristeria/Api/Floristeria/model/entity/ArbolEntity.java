package ProyectoFloristeria.Api.Floristeria.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ArbolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArbol;
    @Column(name = "AturaCm")
    private double altura;
    @Column(name = "precioEuros")
    private double precio;
    @ManyToOne
    @JoinColumn(name = "idFloristeria")
    private FloristeriaEntity floristeria;
}
