import { PrescricaoMedicaComponent } from './prescricao-medica/prescricao-medica.component';
import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { DiarioErrosComponent } from './diario-erros/diario-erros.component';
import { LoginSuccessComponent } from '@nuvem/angular-base';

export const routes: Routes = [
    { path: 'prescricao-medica',
        loadChildren: 'src/app/prescricao-medica/prescricao-medica.module#PrescricaoMedicaModule'
    },
    { path: 'diario-erros', component: DiarioErrosComponent, data: { breadcrumb: 'Diário de Erros' } },
    { path: 'login-success', component: LoginSuccessComponent },
    {
        path: 'pacientes',
        loadChildren: 'src/app/pacientes/pacientes.module#PacientesModule',
    },
    {
        path: 'triagem',
        loadChildren: 'src/app/pacientes/pacientes.module#PacientesModule',
    },
    {
        path: 'internacao',
        loadChildren: 'src/app/internacao/internacao.module#InternacaoModule',
    },
];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes);
