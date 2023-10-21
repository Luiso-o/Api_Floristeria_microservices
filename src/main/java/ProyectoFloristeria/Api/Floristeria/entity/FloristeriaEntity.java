package ProyectoFloristeria.Api.Floristeria.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    @OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "idTienda")
    List<ProductoEntity> misProductos;
}
