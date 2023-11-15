package ProyectoFloristeria.Api.Floristeria.Documents;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.ProductType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Productos")
public class ProductDocument {
    @MongoId
    private String id;
    @Field(name = "tipoProducto")
    private ProductType tipoProducto;
    @Field(name = "Caracteristica")
    private Object parametroGenerico;
    @Field(name = "precio")
    private double precio;
    @DBRef
    private StoreDocument tienda;
}
