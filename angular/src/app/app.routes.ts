import { Routes } from '@angular/router';
import { LoginComponent } from '../login/login.component';
import { RegisterComponent } from '../register/register.component';
import { HomeComponent } from '../home/home.component';
import { ContaComponent } from '../conta/conta.component';
import { SaqueComponent } from '../saque/saque.component';
import { DepositoComponent } from '../deposito/deposito.component';
import { ExtratoComponent } from '../extrato/extrato.component';
import { TransferenciaComponent } from '../transferencia/transferencia.component';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'transferencia', component: TransferenciaComponent, title: 'Transferência' },
    { path: 'extrato', component: ExtratoComponent, title: 'Extrato' },
    { path: 'deposito', component: DepositoComponent, title: 'Depósito' },
    { path: 'saque', component: SaqueComponent, title: 'Saque' },
    { path: 'conta', component: ContaComponent, title: 'Conta'  },
    { path: 'home', component: HomeComponent, title: 'Home'  },
    { path: 'register', component: RegisterComponent, title: 'Cadastro' },
    { path: 'login', component: LoginComponent, title: 'Login' }
];
