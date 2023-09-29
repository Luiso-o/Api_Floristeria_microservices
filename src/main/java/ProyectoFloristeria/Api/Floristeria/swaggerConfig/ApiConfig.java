package ProyectoFloristeria.Api.Floristeria.swaggerConfig;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.info.*;
import io.swagger.v3.oas.annotations.servers.*;

/**
 * @author Luis
 */
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Luis",
                        email = "cheportillo@gmail.com",
                        url = "https://github.com/Luiso-o"
                ),
                description = "API para gestionar diferentes tiendas y sus stocks",
                title = "Floristerías ApiRestFull",
                version = "1.0",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        description = "local ENV",
                        url = "http://localhost:8080"
                )
        }
)
public class ApiConfig {
}
