package br.com.basis.madre.prescricao.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

/**
 * A DTO for the
 * {@link br.com.basis.madre.prescricao.domain.PrescricaoProcedimento} entity.
 */
@Data
public class PrescricaoProcedimentoDTO extends PrescricaoMedicaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<ItemPrescricaoProcedimentoDTO> itemPrescricaoProcedimentoDTO = new HashSet<>();

}
