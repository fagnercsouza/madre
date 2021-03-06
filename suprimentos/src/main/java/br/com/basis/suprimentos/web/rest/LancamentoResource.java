package br.com.basis.suprimentos.web.rest;

import br.com.basis.suprimentos.service.LancamentoService;
import br.com.basis.suprimentos.service.dto.LancamentoDTO;
import br.gov.nuvem.comum.microsservico.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LancamentoResource {
    private static final String ENTITY_NAME = "madresuprimentosLancamento";
    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    private final LancamentoService lancamentoService;

    @PostMapping("/lancamentos")
    public ResponseEntity<LancamentoDTO> createLancamento(@Valid @RequestBody LancamentoDTO lancamentoDTO) throws URISyntaxException {
        log.debug("REST request to save Lancamento : {}", lancamentoDTO);
        if (lancamentoDTO.getId() != null) {
            throw new BadRequestAlertException("A new lancamento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LancamentoDTO result = lancamentoService.save(lancamentoDTO);
        return ResponseEntity.created(new URI("/api/lancamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/lancamentos")
    public ResponseEntity<LancamentoDTO> updateLancamento(@Valid @RequestBody LancamentoDTO lancamentoDTO) throws URISyntaxException {
        log.debug("REST request to update Lancamento : {}", lancamentoDTO);
        if (lancamentoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LancamentoDTO result = lancamentoService.save(lancamentoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, lancamentoDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/lancamentos")
    public ResponseEntity<List<LancamentoDTO>> getAllLancamentos(Pageable pageable) {
        log.debug("REST request to get a page of Lancamentos");
        Page<LancamentoDTO> page = lancamentoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/lancamentos/{id}")
    public ResponseEntity<LancamentoDTO> getLancamento(@PathVariable Long id) {
        log.debug("REST request to get Lancamento : {}", id);
        Optional<LancamentoDTO> lancamentoDTO = lancamentoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lancamentoDTO);
    }

    @DeleteMapping("/lancamentos/{id}")
    public ResponseEntity<Void> deleteLancamento(@PathVariable Long id) {
        log.debug("REST request to delete Lancamento : {}", id);
        lancamentoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/_search/lancamentos")
    public ResponseEntity<List<LancamentoDTO>> searchLancamentos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Lancamentos for query {}", query);
        Page<LancamentoDTO> page = lancamentoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
