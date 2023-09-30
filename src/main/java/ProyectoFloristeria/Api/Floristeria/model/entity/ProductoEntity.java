package ProyectoFloristeria.Api.Floristeria.model.entity;

import ProyectoFloristeria.Api.Floristeria.model.entity.enumeraciones.TipoProducto;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "productos")
@Entity
public class ProductoEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProducto;
    @Enumerated(EnumType.STRING)
    @Column(name = "TipoProducto")
    private TipoProducto tipo;
    @Column(name = "Caracter",columnDefinition = "TEXT")
    private Object caracteristica;
    @Column(name = "precioEuros")
    private double precio;
    @ManyToOne
    @JoinColumn(name = "idFloristeria")
    private FloristeriaEntity floristeria;
}
