package br.com.basis.madre.prescricao.domain.evento;

import java.io.Serializable;
import java.time.ZonedDateTime;

import br.com.basis.madre.prescricao.domain.PrescricaoMedicamento;
import br.com.basis.madre.prescricao.domain.enumeration.TipoEvento;
import lombok.Data;

@Data
public class EventoPrescricaoMedicamento implements Serializable{
	
	public EventoPrescricaoMedicamento(PrescricaoMedicamento prescricaoMedicamento2) {
		// TODO Auto-generated constructor stub
	}
	private static final long serialVersionUID = 1L;
	private PrescricaoMedicamento prescricaoMedicamento;
	private ZonedDateTime dataDeLancamento;
	private TipoEvento tipoDoEvento;
	private String login;
	

}
