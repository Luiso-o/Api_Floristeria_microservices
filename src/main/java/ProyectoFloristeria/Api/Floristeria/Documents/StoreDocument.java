package ProyectoFloristeria.Api.Floristeria.Documents;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.BranchCountries;
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
public class StoreDocument {
    @MongoId
    private String id;
    @Field(name = "nombre")
    private String nombre;
    @Field(name = "fechaDeApertura")
    private LocalDate fechaApertura;
    @Field(name = "pais")
    private BranchCountries pais;
    @Field(name = "Productos")
    private List<ProductDocument> misProductos;
    @Field(name = "Tickets")
    private List<TicketDocument>tickets;
}
