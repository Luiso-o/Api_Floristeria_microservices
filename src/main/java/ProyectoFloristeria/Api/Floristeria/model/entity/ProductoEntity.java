package ProyectoFloristeria.Api.Floristeria.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Productos")
@Entity
public class ProductoEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProducto;
    @Enumerated(EnumType.STRING)
    @Column(name = "TipoProducto")
    private TipoProducto tipo;
    @Column(name = "Caracter")
    private String caracteristica;
    @Column(name = "precioEuros")
    private double precio;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idFloristeria")
    private FloristeriaEntity floristeria;
}
