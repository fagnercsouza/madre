import { ListaPacientesTriagem } from './../lista-pacientes-triagem';
import { Paciente } from './../../../../internacao/models/paciente';
import { ActivatedRoute } from '@angular/router';
import { TriagemModel } from '../../../models/triagem-model';
import { TriagemService } from './../triagem.service';
import { BreadcrumbService, CALENDAR_LOCALE } from '@nuvem/primeng-components';
import { OnInit, OnDestroy, Component, Input } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CLASSIFICACAO_RISCO } from 'src/app/pacientes/models/radioButton/classificacao-risco';
import * as moment from 'moment';

@Component({
    selector: 'app-formulario-triagem',
    templateUrl: './formulario-triagem.component.html',
    styleUrls: ['./formulario-triagem.component.css'],
})
export class FormularioTriagemComponent implements OnInit, OnDestroy {
    val: Paciente;
    results: Paciente[];

    @Input() formsTriagem: FormGroup;
    opcaoClassificacao = CLASSIFICACAO_RISCO;
    selectedValue: string;
    triagem: TriagemModel;
    formTriagem = this.fb.group({
        classificacaoDeRisco: ['', Validators.required],
        paciente: ['', Validators.required],
        pressaoArterial: [''],
        frequenciaCardiaca: [''],
        temperatura: [''],
        peso: [''],
        sinaisSintomas: ['', Validators.minLength(3)],
        dataHoraDoAtendimento: [new Date()],
        idade: [''],
        descricaoQueixa: ['', Validators.compose([Validators.required, Validators.minLength(3)])],
        vitimaDeAcidente: [''],
        removidoDeAmbulancia: [''],
        observacao: [''],
    });

    @Input() formularioTriagem: FormGroup;
    localizacao = CALENDAR_LOCALE;
    dataLimite = new Date();
    anosDisponiveis = `2000:${this.dataLimite.getFullYear()}`;
    formatoDeData = 'dd/mm/yy';
    listaPacientesTriagem = new Array<ListaPacientesTriagem>();
    idade = '';

    buscaPacientes(event) {
        this.triagemService.getResultPacientes(event.query).subscribe((data) => {
            this.listaPacientesTriagem = data.content;
        });
    }

    constructor(
        private breadcrumbService: BreadcrumbService,
        private fb: FormBuilder,
        private triagemService: TriagemService,
        private route: ActivatedRoute,
    ) {}
    private idadePaciente(dtNascimento: Date) {
        if (dtNascimento) {
            const idade = moment().diff(moment(dtNascimento), 'years');

            if (idade === 0) {
                this.idade = 'Menos de 1 ano';
                return;
            }

            this.idade = String(idade);

            return;
        }

        this.idade = '';
    }

    ngOnInit() {
        this.breadcrumbService.setItems([
            { label: 'Pacientes', routerLink: 'pacientes' },
            { label: 'Triagem', routerLink: 'pacientes/triagem' },
            { label: 'Formulário' },
        ]);

        const triagemId = this.route.snapshot.params['id'];

        if (triagemId) {
            this.carregarTriagem(triagemId);
        }
    }
    get editando() {
        return Boolean(this.triagem.id);
    }

    cadastrar(form: FormBuilder) {
        const tri = this.formTriagem.value;
        const triagem: TriagemModel = {
            classificacaoDeRisco: tri.classificacaoDeRisco,
            pressaoArterial: tri.pressaoArterial,
            frequenciaCardiaca: tri.frequenciaCardiaca,
            temperatura: tri.temperatura,
            peso: tri.peso,
            sinaisSintomas: tri.sinaisSintomas,
            dataHoraDoAtendimento: tri.dataHoraDoAtendimento,
            descricaoQueixa: tri.descricaoQueixa,
            vitimaDeAcidente: tri.vitimaDeAcidente,
            removidoDeAmbulancia: tri.removidoDeAmbulancia,
            observacao: tri.observacao,
            pacienteId: tri.paciente.id,
        };

        this.triagemService.cadastrarTriagem(triagem).subscribe((e) => {
            this.formTriagem.reset();
        });
        console.log(triagem);
    }

    carregarTriagem(id: number) {
        this.triagemService.buscarTriagemId(id).subscribe((triagem) => {
            console.log(triagem);

            this.formTriagem.patchValue(triagem);
            this.formTriagem.patchValue({ paciente: triagem.paciente.nome });
        });
    }

    pacienteSelecionado(evt: any) {
        this.idadePaciente(evt.dataDeNascimento);
        console.log(evt);
    }

    ngOnDestroy() {
        this.breadcrumbService.reset();
    }
}
