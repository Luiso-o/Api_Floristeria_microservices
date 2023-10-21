package ProyectoFloristeria.Api.Floristeria.entity;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.TipoProducto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Productos")
@Entity
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;
    @Enumerated(EnumType.STRING)
    @Column(name = "TipoProducto")
    private TipoProducto tipoProducto;
    @Column(name = "Característica")
    private Object parametroGenerico;
    @Column(name = "Precio")
    private double precio;
    @ManyToOne
    @JoinColumn(name = "idTienda")
    private FloristeriaEntity tienda;
}
