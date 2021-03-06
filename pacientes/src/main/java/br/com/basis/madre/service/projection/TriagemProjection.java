package br.com.basis.madre.service.projection;

import br.com.basis.madre.domain.enumeration.ClassificacaoDeRisco;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface TriagemProjection {

    Long getId();
    ZonedDateTime getDataHoraDoAtendimento();
    ClassificacaoDeRisco getClassificacaoDeRisco();
    PacienteProjection getPaciente();

    static interface PacienteProjection {
        Long getId();
        String getNome();
        String getNomeSocial();
        GenitoresProjection getGenitores();

        static interface GenitoresProjection {
            String getNomeDaMae();
        }
    }

}
