package ProyectoFloristeria.Api.Floristeria.Documents;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "tickets")
public class TicketDocument {
    @MongoId
    private String id;
    @Field(name = "fechaDeCreacion")
    private LocalDate fechaDeCreacion;
    @Field(name = "nombreDeLaTienda")
    private String nombreTienda;
    @Field(name = "productos")
    private List<ProductDocument> misProductos;
    @Field(name = "totalDeLaCompra")
    private Double total;
    @DBRef
    private StoreDocument tienda;
}
