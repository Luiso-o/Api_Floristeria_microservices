package ProyectoFloristeria.Api.Floristeria.Documents;

import ProyectoFloristeria.Api.Floristeria.enumeraciones.TipoProducto;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Productos")
public class ProductoDocument {
    @MongoId
    private String idProducto;
    @Field(name = "tipoProducto")
    private TipoProducto tipoProducto;
    @Field(name = "Caracteristica")
    private Object parametroGenerico;
    @Field(name = "precio")
    private double precio;
}
