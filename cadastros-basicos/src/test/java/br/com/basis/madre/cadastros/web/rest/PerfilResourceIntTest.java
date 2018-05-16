package br.com.basis.madre.cadastros.web.rest;

import br.com.basis.madre.cadastros.CadastrosbasicosApp;
import br.com.basis.madre.cadastros.domain.Perfil;
import br.com.basis.madre.cadastros.repository.PerfilRepository;
import br.com.basis.madre.cadastros.repository.search.PerfilSearchRepository;
import br.com.basis.madre.cadastros.service.PerfilService;
import br.com.basis.madre.cadastros.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.basis.madre.cadastros.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the PerfilResource REST controller.
 *
 * @see PerfilResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CadastrosbasicosApp.class)
public class PerfilResourceIntTest {

    private static final Integer DEFAULT_NM_PERFIL = 1;
    private static final Integer UPDATED_NM_PERFIL = 2;

    private static final String DEFAULT_DS_PERFIL = "AAAAAAAAAA";
    private static final String UPDATED_DS_PERFIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ST_EXCLUIDO = false;
    private static final Boolean UPDATED_ST_EXCLUIDO = true;

    private static final Boolean DEFAULT_ST_ATIVO = false;
    private static final Boolean UPDATED_ST_ATIVO = true;

    private static final Integer DEFAULT_ID_FUNCIONALIDADE = 1;
    private static final Integer UPDATED_ID_FUNCIONALIDADE = 2;

    @Autowired
    private PerfilRepository perfilRepository;


    @Autowired
    private PerfilService perfilService;

    @Autowired
    private PerfilSearchRepository perfilSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPerfilMockMvc;

    private Perfil perfil;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PerfilResource perfilResource = new PerfilResource(perfilService);
        this.restPerfilMockMvc = MockMvcBuilders.standaloneSetup(perfilResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Perfil createEntity(EntityManager em) {
        Perfil perfil = new Perfil()
            .nmPerfil(DEFAULT_NM_PERFIL)
            .dsPerfil(DEFAULT_DS_PERFIL)
            .stExcluido(DEFAULT_ST_EXCLUIDO)
            .stAtivo(DEFAULT_ST_ATIVO)
            .idFuncionalidade(DEFAULT_ID_FUNCIONALIDADE);
        return perfil;
    }

    @Before
    public void initTest() {
        perfilSearchRepository.deleteAll();
        perfil = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfil() throws Exception {
        int databaseSizeBeforeCreate = perfilRepository.findAll().size();

        // Create the Perfil
        Perfil perfil = new Perfil();
        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfil)))
            .andExpect(status().isCreated());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeCreate + 1);
        Perfil testPerfil = perfilList.get(perfilList.size() - 1);
        assertThat(testPerfil.getNmPerfil()).isEqualTo(DEFAULT_NM_PERFIL);
        assertThat(testPerfil.getDsPerfil()).isEqualTo(DEFAULT_DS_PERFIL);
        assertThat(testPerfil.isStExcluido()).isEqualTo(DEFAULT_ST_EXCLUIDO);
        assertThat(testPerfil.isStAtivo()).isEqualTo(DEFAULT_ST_ATIVO);
        assertThat(testPerfil.getIdFuncionalidade()).isEqualTo(DEFAULT_ID_FUNCIONALIDADE);

        // Validate the Perfil in Elasticsearch
        Perfil perfilEs = perfilSearchRepository.findOne(testPerfil.getId());
        assertThat(perfilEs).isEqualToIgnoringGivenFields(testPerfil);
    }

    @Test
    @Transactional
    public void createPerfilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfilRepository.findAll().size();

        // Create the Perfil with an existing ID
        perfil.setId(1L);
        Perfil perfil = new Perfil();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfil)))
            .andExpect(status().isBadRequest());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkIdFuncionalidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = perfilRepository.findAll().size();
        // set the field null
        perfil.setIdFuncionalidade(null);

        // Create the Perfil, which fails.
        Perfil perfil = new Perfil();

        restPerfilMockMvc.perform(post("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfil)))
            .andExpect(status().isBadRequest());

        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPerfils() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get all the perfilList
        restPerfilMockMvc.perform(get("/api/perfils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmPerfil").value(hasItem(DEFAULT_NM_PERFIL)))
            .andExpect(jsonPath("$.[*].dsPerfil").value(hasItem(DEFAULT_DS_PERFIL.toString())))
            .andExpect(jsonPath("$.[*].stExcluido").value(hasItem(DEFAULT_ST_EXCLUIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].stAtivo").value(hasItem(DEFAULT_ST_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].idFuncionalidade").value(hasItem(DEFAULT_ID_FUNCIONALIDADE)));
    }

    @Test
    @Transactional
    public void getPerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);

        // Get the perfil
        restPerfilMockMvc.perform(get("/api/perfils/{id}", perfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(perfil.getId().intValue()))
            .andExpect(jsonPath("$.nmPerfil").value(DEFAULT_NM_PERFIL))
            .andExpect(jsonPath("$.dsPerfil").value(DEFAULT_DS_PERFIL.toString()))
            .andExpect(jsonPath("$.stExcluido").value(DEFAULT_ST_EXCLUIDO.booleanValue()))
            .andExpect(jsonPath("$.stAtivo").value(DEFAULT_ST_ATIVO.booleanValue()))
            .andExpect(jsonPath("$.idFuncionalidade").value(DEFAULT_ID_FUNCIONALIDADE));
    }

    @Test
    @Transactional
    public void getNonExistingPerfil() throws Exception {
        // Get the perfil
        restPerfilMockMvc.perform(get("/api/perfils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);
        perfilSearchRepository.save(perfil);
        int databaseSizeBeforeUpdate = perfilRepository.findAll().size();

        // Update the perfil
        Perfil updatedPerfil = perfilRepository.findOne(perfil.getId());
        // Disconnect from session so that the updates on updatedPerfil are not directly saved in db
        em.detach(updatedPerfil);
        updatedPerfil
            .nmPerfil(UPDATED_NM_PERFIL)
            .dsPerfil(UPDATED_DS_PERFIL)
            .stExcluido(UPDATED_ST_EXCLUIDO)
            .stAtivo(UPDATED_ST_ATIVO)
            .idFuncionalidade(UPDATED_ID_FUNCIONALIDADE);
        Perfil perfil = updatedPerfil;

        restPerfilMockMvc.perform(put("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfil)))
            .andExpect(status().isOk());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeUpdate);
        Perfil testPerfil = perfilList.get(perfilList.size() - 1);
        assertThat(testPerfil.getNmPerfil()).isEqualTo(UPDATED_NM_PERFIL);
        assertThat(testPerfil.getDsPerfil()).isEqualTo(UPDATED_DS_PERFIL);
        assertThat(testPerfil.isStExcluido()).isEqualTo(UPDATED_ST_EXCLUIDO);
        assertThat(testPerfil.isStAtivo()).isEqualTo(UPDATED_ST_ATIVO);
        assertThat(testPerfil.getIdFuncionalidade()).isEqualTo(UPDATED_ID_FUNCIONALIDADE);

        // Validate the Perfil in Elasticsearch
        Perfil perfilEs = perfilSearchRepository.findOne(testPerfil.getId());
        assertThat(perfilEs).isEqualToIgnoringGivenFields(testPerfil);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfil() throws Exception {
        int databaseSizeBeforeUpdate = perfilRepository.findAll().size();

        // Create the Perfil
        Perfil perfil = new Perfil();

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPerfilMockMvc.perform(put("/api/perfils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(perfil)))
            .andExpect(status().isCreated());

        // Validate the Perfil in the database
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);
        perfilSearchRepository.save(perfil);
        int databaseSizeBeforeDelete = perfilRepository.findAll().size();

        // Get the perfil
        restPerfilMockMvc.perform(delete("/api/perfils/{id}", perfil.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean perfilExistsInEs = perfilSearchRepository.exists(perfil.getId());
        assertThat(perfilExistsInEs).isFalse();

        // Validate the database is empty
        List<Perfil> perfilList = perfilRepository.findAll();
        assertThat(perfilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPerfil() throws Exception {
        // Initialize the database
        perfilRepository.saveAndFlush(perfil);
        perfilSearchRepository.save(perfil);

        // Search the perfil
        restPerfilMockMvc.perform(get("/api/_search/perfils?query=id:" + perfil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nmPerfil").value(hasItem(DEFAULT_NM_PERFIL)))
            .andExpect(jsonPath("$.[*].dsPerfil").value(hasItem(DEFAULT_DS_PERFIL.toString())))
            .andExpect(jsonPath("$.[*].stExcluido").value(hasItem(DEFAULT_ST_EXCLUIDO.booleanValue())))
            .andExpect(jsonPath("$.[*].stAtivo").value(hasItem(DEFAULT_ST_ATIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].idFuncionalidade").value(hasItem(DEFAULT_ID_FUNCIONALIDADE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Perfil.class);
        Perfil perfil1 = new Perfil();
        perfil1.setId(1L);
        Perfil perfil2 = new Perfil();
        perfil2.setId(perfil1.getId());
        assertThat(perfil1).isEqualTo(perfil2);
        perfil2.setId(2L);
        assertThat(perfil1).isNotEqualTo(perfil2);
        perfil1.setId(null);
        assertThat(perfil1).isNotEqualTo(perfil2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Perfil.class);
        Perfil perfil1 = new Perfil();
        perfil1.setId(1L);
        Perfil perfil2 = new Perfil();
        assertThat(perfil1).isNotEqualTo(perfil2);
        perfil2.setId(perfil1.getId());
        assertThat(perfil1).isEqualTo(perfil2);
        perfil2.setId(2L);
        assertThat(perfil1).isNotEqualTo(perfil2);
        perfil1.setId(null);
        assertThat(perfil1).isNotEqualTo(perfil2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(perfil.getId()).isEqualTo(42);
        assertThat(perfil.getId()).isNull();
    }
}
