package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.AutorizacaoFornecimento;
import br.com.basis.suprimentos.service.dto.AutorizacaoFornecimentoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FornecedorMapper.class})
public interface AutorizacaoFornecimentoMapper extends EntityMapper<AutorizacaoFornecimentoDTO, AutorizacaoFornecimento> {
    @Mapping(source = "fornecedor.id", target = "fornecedorId")
    @Mapping(source = "fornecedor.nomeFantasia", target = "fornecedorNome")
    AutorizacaoFornecimentoDTO toDto(AutorizacaoFornecimento autorizacaoFornecimento);

    @Mapping(source = "fornecedorId", target = "fornecedor")
    AutorizacaoFornecimento toEntity(AutorizacaoFornecimentoDTO autorizacaoFornecimentoDTO);

    default AutorizacaoFornecimento fromId(Long id) {
        if (id == null) {
            return null;
        }
        AutorizacaoFornecimento autorizacaoFornecimento = new AutorizacaoFornecimento();
        autorizacaoFornecimento.setId(id);
        return autorizacaoFornecimento;
    }
}
