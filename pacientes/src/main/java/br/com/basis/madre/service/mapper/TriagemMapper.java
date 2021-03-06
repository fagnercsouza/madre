package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.Triagem;
import br.com.basis.madre.service.dto.TriagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity Triagem and its DTO TriagemDTO.
 */
@Mapper(componentModel = "spring", uses = {PacienteMapper.class})
public interface TriagemMapper extends EntityMapper<TriagemDTO, Triagem> {

    @Mapping(source = "paciente.id", target = "pacienteId")
    TriagemDTO toDto(Triagem triagem);

    @Mapping(source = "pacienteId", target = "paciente")
    Triagem toEntity(TriagemDTO triagemDTO);

    default Triagem fromId(Long id) {
        if (id == null) {
            return null;
        }
        Triagem triagem = new Triagem();
        triagem.setId(id);
        return triagem;
    }
}
