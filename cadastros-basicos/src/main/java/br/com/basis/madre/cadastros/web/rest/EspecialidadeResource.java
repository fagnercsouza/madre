package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.domain.Especialidade;
import br.com.basis.madre.cadastros.service.EspecialidadeService;
import br.com.basis.madre.cadastros.web.rest.errors.BadRequestAlertException;
import br.com.basis.madre.cadastros.web.rest.util.HeaderUtil;
import br.com.basis.madre.cadastros.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Especialidade.
 */
@RestController
@RequestMapping("/api")
public class EspecialidadeResource {

    private final Logger log = LoggerFactory.getLogger(EspecialidadeResource.class);

    private static final String ENTITY_NAME = "especialidade";

    private final EspecialidadeService especialidadeService;

    public EspecialidadeResource(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    /**
     * POST  /especialidades : Create a new especialidade.
     *
     * @param especialidade the especialidade to create
     * @return the ResponseEntity with status 201 (Created) and with body the new especialidade, or with status 400 (Bad Request) if the especialidade has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/especialidades")
    @Timed
    public ResponseEntity<Especialidade> createEspecialidade(@Valid @RequestBody Especialidade especialidade) throws URISyntaxException {
        log.debug("REST request to save Especialidade : {}", especialidade);
        if (especialidade.getId() != null) {
            throw new BadRequestAlertException("A new especialidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Especialidade result = especialidadeService.save(especialidade);
        return ResponseEntity.created(new URI("/api/especialidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /especialidades : Updates an existing especialidade.
     *
     * @param especialidade the especialidade to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated especialidade,
     * or with status 400 (Bad Request) if the especialidade is not valid,
     * or with status 500 (Internal Server Error) if the especialidade couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/especialidades")
    @Timed
    public ResponseEntity<Especialidade> updateEspecialidade(@Valid @RequestBody Especialidade especialidade) throws URISyntaxException {
        log.debug("REST request to update Especialidade : {}", especialidade);
        if (especialidade.getId() == null) {
            return createEspecialidade(especialidade);
        }
        Especialidade result = especialidadeService.save(especialidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, especialidade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /especialidades : get all the especialidades.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of especialidades in body
     */
    @GetMapping("/especialidades")
    @Timed
    public ResponseEntity<List<Especialidade>> getAllEspecialidades(Pageable pageable) {
        log.debug("REST request to get a page of Especialidades");
        Page<Especialidade> page = especialidadeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/especialidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /especialidades/:id : get the "id" especialidade.
     *
     * @param id the id of the especialidade to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the especialidade, or with status 404 (Not Found)
     */
    @GetMapping("/especialidades/{id}")
    @Timed
    public ResponseEntity<Especialidade> getEspecialidade(@PathVariable Long id) {
        log.debug("REST request to get Especialidade : {}", id);
        Especialidade especialidade = especialidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(especialidade));
    }

    /**
     * DELETE  /especialidades/:id : delete the "id" especialidade.
     *
     * @param id the id of the especialidade to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/especialidades/{id}")
    @Timed
    public ResponseEntity<Void> deleteEspecialidade(@PathVariable Long id) {
        log.debug("REST request to delete Especialidade : {}", id);
        especialidadeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/especialidades?query=:query : search for the especialidade corresponding
     * to the query.
     *
     * @param query the query of the especialidade search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/especialidades")
    @Timed
    public ResponseEntity<List<Especialidade>> searchEspecialidades(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Especialidades for query {}", query);
        Page<Especialidade> page = especialidadeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/especialidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}