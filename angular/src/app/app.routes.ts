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
    { path: 'transferencia', component: TransferenciaComponent },
    { path: 'extrato', component: ExtratoComponent },
    { path: 'deposito', component: DepositoComponent },
    { path: 'saque', component: SaqueComponent },
    { path: 'conta', component: ContaComponent },
    { path: 'home', component: HomeComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'login', component: LoginComponent }
];
