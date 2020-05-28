import { TipoMedicamento } from './cadastro-medicamento/tipoMedicamento';
import { Pageable } from './../../shared/pageable';
import { Observable } from 'rxjs';
import { Unidade } from './dispensacao/unidade';
import { Prescricao } from './dispensacao/prescricao';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Apresentacao } from './cadastro-medicamento/apresentacao';

@Injectable({
    providedIn: 'root',
})
export class FarmaciaService {
    constructor(private httpServe: HttpClient) {}
    private readonly apiUrl = '/farmacia/api';

    getPrescricao(
        nome: string,
        dataInicio: string,
        local: string,
    ): Observable<Pageable<Prescricao>> {
        return this.httpServe.get<Pageable<Prescricao>>(`${this.apiUrl}/prescricao`, {
            params: new HttpParams()
                .set('nome', nome)
                .set('dataInicio', dataInicio)
                .set('local', local),
        });
    }

    getResult(): Observable<Array<TipoMedicamento>> {
        return this.httpServe.get<Array<TipoMedicamento>>(`${this.apiUrl}/tipo-medicamentos`);
    }
    getResultUnidade(): Observable<Array<Unidade>> {
        return this.httpServe.get<Array<Unidade>>(`${this.apiUrl}/unidades`);
    }
    getResultApresentacao(): Observable<Array<Apresentacao>> {
        return this.httpServe.get<Array<Apresentacao>>(`${this.apiUrl}/apresentacaos`);
    }

    cadastrar(medicamento: any): Observable<any> {
        const dto = {
            codigo: null,
            nome: medicamento.medicamento,
            descricao: medicamento.descricao,
            concentracao: medicamento.concentracao,
            ativo: medicamento.ativo,

            apresentacaoId: {
                id: medicamento.apresentacao.id,
                nome: medicamento.apresentacao.nome,
            },

            unidadeId: {
                id: medicamento.unidade.id,
                nome: medicamento.unidade.nome,
            },

            tipoMedicamentoId: {
                id: medicamento.tipo.id,
                nome: medicamento.tipo.nome,
            },
        };
        console.log(dto);

        return this.httpServe.post<any>(`${this.apiUrl}/medicamentos`, dto);
    }
}
