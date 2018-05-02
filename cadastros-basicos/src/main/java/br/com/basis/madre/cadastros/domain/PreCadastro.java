package br.com.basis.madre.cadastros.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A PreCadastro.
 */
@Entity
@Table(name = "pre_cadastro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "precadastro")
public class PreCadastro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nome_do_paciente", length = 150, nullable = false)
    private String nomeDoPaciente;

    @Size(max = 150)
    @Column(name = "nome_social", length = 150)
    private String nomeSocial;

    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "nome_da_mae", length = 150, nullable = false)
    private String nomeDaMae;

    @NotNull
    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @Size(min = 15, max = 15)
    @Column(name = "n_cartao_sus", length = 15)
    private String numCartaoSus;

    @NotNull
    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome_do_paciente() {
        return nomeDoPaciente;
    }

    public PreCadastro nome_do_paciente(String nome_do_paciente) {
        this.nomeDoPaciente = nome_do_paciente;
        return this;
    }

    public void setNome_do_paciente(String nome_do_paciente) {
        this.nomeDoPaciente = nome_do_paciente;
    }

    public String getNome_social() {
        return nomeSocial;
    }

    public PreCadastro nome_social(String nome_social) {
        this.nomeSocial = nome_social;
        return this;
    }

    public void setNome_social(String nome_social) {
        this.nomeSocial = nome_social;
    }

    public String getNome_da_mae() {
        return nomeDaMae;
    }

    public PreCadastro nomeDaMae(String nome_da_mae) {
        this.nomeDaMae = nome_da_mae;
        return this;
    }

    public LocalDate getData_de_nascimento() {
        return dataDeNascimento;
    }
    
    public void setNome_da_mae(String nome_da_mae) {
        this.nomeDaMae = nome_da_mae;
    }

    public PreCadastro data_de_nascimento(LocalDate data_de_nascimento) {
        this.dataDeNascimento = data_de_nascimento;
        return this;
    }

    public void setData_de_nascimento(LocalDate data_de_nascimento) {
        this.dataDeNascimento = data_de_nascimento;
    }

    public String getn_cartao_sus() {
        return numCartaoSus;
    }

    public PreCadastro n_cartao_sus(String n_cartao_sus) {
        this.numCartaoSus = n_cartao_sus;
        return this;
    }

    public void setn_cartao_sus(String n_cartao_sus) {
        this.numCartaoSus = n_cartao_sus;
    }

    public Boolean isAtivo() {
        return ativo;
    }

    public PreCadastro ativo(Boolean ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreCadastro preCadastro = (PreCadastro) o;
        if (preCadastro.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), preCadastro.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PreCadastro{" +
            "id=" + getId() +
            ", nome_do_paciente='" + getNome_do_paciente() + "'" +
            ", nome_social='" + getNome_social() + "'" +
            ", nome_da_mae='" + getNome_da_mae() + "'" +
            ", data_de_nascimento='" + getData_de_nascimento() + "'" +
            ", n_cartao_sus='" + getn_cartao_sus() + "'" +
            ", ativo='" + isAtivo() + "'" +
            "}";
    }
}
