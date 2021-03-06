package br.com.basis.suprimentos.service.mapper;

import br.com.basis.suprimentos.domain.Almoxarifado;
import br.com.basis.suprimentos.service.dto.AlmoxarifadoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CentroDeAtividadeMapper.class, CaracteristicaArmazenamentoMapper.class})
public interface AlmoxarifadoMapper extends EntityMapper<AlmoxarifadoDTO, Almoxarifado> {
    @Mapping(source = "caracteristicaArmazenamento.id", target = "caracteristicaArmazenamentoId")
    AlmoxarifadoDTO toDto(Almoxarifado almoxarifado);

    @Mapping(target = "temposPorClasses", ignore = true)
    @Mapping(source = "caracteristicaArmazenamentoId", target = "caracteristicaArmazenamento")
    @Mapping(target = "estoques", ignore = true)
    Almoxarifado toEntity(AlmoxarifadoDTO almoxarifadoDTO);

    default Almoxarifado fromId(Long id) {
        if (id == null) {
            return null;
        }
        Almoxarifado almoxarifado = new Almoxarifado();
        almoxarifado.setId(id);
        return almoxarifado;
    }
}
