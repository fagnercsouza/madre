import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, Validators } from '@angular/forms';
import { Almoxarifado } from '@suprimentos/models/almoxarifado';
import { InclusaoSaldoEstoqueDTO } from '@suprimentos/models/inclusao-saldo-estoque';
import { Material } from '@suprimentos/models/material';
import { AlmoxarifadoService } from '@suprimentos/services/almoxarifado.service';
import { EstoqueGeralService } from '@suprimentos/services/estoque-geral.service';
import { MaterialService } from '@suprimentos/services/material.service';

@Component({
    selector: 'app-inclusao-saldo-estoque',
    templateUrl: './inclusao-saldo-estoque.component.html',
})
export class InclusaoSaldoEstoqueComponent {
    public almoxarifados: Almoxarifado[];
    public materiais: Material[];

    public form = this.fb.group({
        almoxarifadoId: [null, Validators.required],
        materialId: [null, Validators.required],
        quantidade: [null, [Validators.required, Validators.min(1)]],
        valorTotal: [null, [Validators.required, Validators.min(0)]],
    });

    constructor(
        private fb: FormBuilder,
        private estoqueAlmoxarifadoService: EstoqueGeralService,
        private almoxarifadoService: AlmoxarifadoService,
        private materialService: MaterialService,
    ) {}

    public incluir(): void {
        this.estoqueAlmoxarifadoService
            .incluirSaldoEstoque(this.normalizeForm(this.form))
            .subscribe(
                (resposta) => {
                    console.log(resposta);
                },
                (erro) => {
                    console.error(erro);
                },
            );
    }

    public normalizeForm(form: AbstractControl): InclusaoSaldoEstoqueDTO {
        const value = form.value;

        for (const prop in value) {
            if (value[prop]?.id) {
                value[prop] = value[prop].id;
            }
        }

        return {
            almoxarifadoId: value['almoxarifadoId'],
            materialId: value['materialId'],
            quantidade: value['quantidade'],
            valorTotal: value['valorTotal'],
        };
    }

    public getAlmoxarifadoPorDescricao(descricao: string): void {
        this.almoxarifadoService
            .getAlmoxarifadosPorDescricao(descricao)
            .subscribe((almoxarifados) => (this.almoxarifados = almoxarifados));
    }

    public getMateriaisPorNome(nome: string): void {
        this.materialService
            .obterMateriaisPeloNome(nome)
            .subscribe((materiais) => (this.materiais = materiais));
    }
}
