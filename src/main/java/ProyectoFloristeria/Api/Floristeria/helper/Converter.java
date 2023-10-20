package ProyectoFloristeria.Api.Floristeria.helper;

import ProyectoFloristeria.Api.Floristeria.Dto.FloristeriaDto;
import ProyectoFloristeria.Api.Floristeria.entity.FloristeriaEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;


import java.util.regex.Pattern;

@Component
public class Converter {
    private static final Pattern LONG_PATTERN = Pattern.compile("^-?\\d+$");
    private static final Logger log = LoggerFactory.getLogger(Converter.class);

    public Flux<FloristeriaDto>fromFloristeriaEntityToDto(Flux<FloristeriaEntity> store){
        return store.map(this::floristeriaDto);
    }

    public FloristeriaDto floristeriaDto(FloristeriaEntity store){
        return FloristeriaDto.builder()
                .idFloristeria(store.getIdFloristeria())
                .nombre(store.getNombre())
                .pais(store.getPais())
                .build();
    }






    public void verificaId(Long id) {
        String idCadena = String.valueOf(id);
        if (!LONG_PATTERN.matcher(idCadena).matches()) {
            log.info("El formato del ID no es válido: " + id);
            throw new IllegalArgumentException("El formato del ID no es válido: " + id);
        }
    }

}
