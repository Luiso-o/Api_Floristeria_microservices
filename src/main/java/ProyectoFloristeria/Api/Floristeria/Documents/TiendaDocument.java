package ProyectoFloristeria.Api.Floristeria.Documents;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.PaisesSucursales;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Tiendas")
public class TiendaDocument {
    @MongoId
    private String idTienda;
    @Field(name = "nombre")
    private String nombre;
    @Field(name = "fechaDeApertura")
    private LocalDate fechaApertura;
    @Field(name = "pais")
    private PaisesSucursales pais;
    @Field(name = "productos")
    private List<ProductoDocument> misProductos;
    @Field(name = "tickets")
    private List<TicketDocument>tickets;
}
