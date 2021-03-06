package br.com.basis.madre.prescricao.domain;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.basis.madre.prescricao.domain.enumeration.TipoProcedimentoEspecial;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * A TipoProcedimento.
 */
@Data
@Entity
@Table(name = "tipo_procedimento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "madre-prescricao-tipoprocedimento")
public class TipoProcedimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Field(type = FieldType.Keyword)
    private Long id;

    /**
     * descrição de procedimentos especiais diversos
     */
    @NotNull
    @Size(max = 100)
    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;
    
    /**
	 * Tipo do procedimento especial
	 */
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_procedimento_especial")
	private TipoProcedimentoEspecial tipoProcedimentoEspecial;
    

    public TipoProcedimento descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

}
