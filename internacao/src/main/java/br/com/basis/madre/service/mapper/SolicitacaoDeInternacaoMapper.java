package br.com.basis.madre.service.mapper;

import br.com.basis.madre.domain.SolicitacaoDeInternacao;
import br.com.basis.madre.service.dto.SolicitacaoDeInternacaoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CIDMapper.class, EquipeMapper.class, CRMMapper.class,
    ProcedimentoMapper.class})
public interface SolicitacaoDeInternacaoMapper extends
    EntityMapper<SolicitacaoDeInternacaoDTO, SolicitacaoDeInternacao> {

    @Mapping(source = "cidPrincipal.id", target = "cidPrincipalId")
    @Mapping(source = "cidSecundario.id", target = "cidSecundarioId")
    @Mapping(source = "equipe.id", target = "equipeId")
    @Mapping(source = "crm.id", target = "crmId")
    @Mapping(source = "procedimento.id", target = "procedimentoId")
    SolicitacaoDeInternacaoDTO toDto(SolicitacaoDeInternacao solicitacaoDeInternacao);

    @Mapping(source = "cidPrincipalId", target = "cidPrincipal")
    @Mapping(source = "cidSecundarioId", target = "cidSecundario")
    @Mapping(source = "equipeId", target = "equipe")
    @Mapping(source = "crmId", target = "crm")
    @Mapping(source = "procedimentoId", target = "procedimento")
    SolicitacaoDeInternacao toEntity(SolicitacaoDeInternacaoDTO solicitacaoDeInternacaoDTO);

    default SolicitacaoDeInternacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        SolicitacaoDeInternacao solicitacaoDeInternacao = new SolicitacaoDeInternacao();
        solicitacaoDeInternacao.setId(id);
        return solicitacaoDeInternacao;
    }

}
