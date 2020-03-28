package br.com.basis.madre.consulta.web.rest;

import br.com.basis.madre.consulta.service.FeriadoService;
import br.com.basis.madre.consulta.service.dto.FeriadoDTO;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/feriados")
@RequiredArgsConstructor
public class FeriadoResource {

    private final FeriadoService feriadoService;
    private static final String BASE_URL = "/api/v1/feriados";
    private static final String ENTITY_NAME = "feriados";
    private static final String APP_NAME = "Madre";

    @PostMapping("/filter")
    public ResponseEntity<Page<FeriadoDTO>> listar(@RequestBody FeriadoDTO feriadoDTO, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Feriados");
        Page<FeriadoDTO> feriado = feriadoService.findAll(feriadoDTO, pageable);
        return new ResponseEntity<>(feriado, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<FeriadoDTO> buscaPorId(@RequestBody Long id) {
        log.debug("REST request to get Feriado : {}", id);
        Optional<FeriadoDTO> feriado = feriadoService.findById(id);
        return ResponseUtil.wrapOrNotFound(feriado);
    }

    @PostMapping
    public ResponseEntity<FeriadoDTO> create(@RequestBody FeriadoDTO feriadoDTO) throws URISyntaxException {
        log.debug("REST request to save Feriado : {}", feriadoDTO);
        FeriadoDTO result = feriadoService.save(feriadoDTO);
        return ResponseEntity.created(new URI(BASE_URL + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(APP_NAME, false, result.getId().toString(), ENTITY_NAME))
            .body(result);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody Long id) {
        log.debug("REST request to delete Feriado : {}", id);
        feriadoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityCreationAlert(APP_NAME, false, ENTITY_NAME, id.toString())).build();
    }
}
