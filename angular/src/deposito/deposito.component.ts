import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { NgbAlert } from '@ng-bootstrap/ng-bootstrap';
import { Lancamento } from '../model/Lancamento';
import { Alert } from '../model/Alert';
import { Conta } from '../model/Conta';
import { TokenInfo } from '../model/TokenInfo';
import { LancamentoService } from '../services/lancamento.service';
import { TokenService } from '../services/token.service';
import { ContaService } from '../services/conta.service';

@Component({
  selector: 'app-deposito',
  imports: [FormsModule, ReactiveFormsModule, NgbAlert, RouterModule, CommonModule],
  templateUrl: './deposito.component.html',
  styleUrl: './deposito.component.scss'
})
export class DepositoComponent {
  form: FormGroup;
  deposito: Lancamento | undefined;
  alert: Alert | undefined;
  selectedConta: Conta;
  token: string | undefined | null;
  tokenInfo: TokenInfo | undefined;
  contas: Conta[];
  selectedContaIndex: number | undefined;

  @ViewChild('selfClosingAlert', { static: false }) selfClosingAlert: NgbAlert | undefined;

  constructor(private fBuilder: FormBuilder, private router: Router, private lancamentoService: LancamentoService, private tokenService: TokenService, private contaService: ContaService) {
    this.selectedConta = new Conta();
    this.contas = [];
    this.deposito = new Lancamento();
    this.form = this.fBuilder.group({
      'conta': [this.selectedContaIndex, Validators.required],
      'valor': [this.deposito.valor, Validators.compose([
        Validators.required, Validators.min(0)])]
    });
    this.alert = new Alert("", "");
  }

  ngOnInit() {

    this.token = this.tokenService.getToken();
    if (this.token == null || this.token == undefined) {
      this.router.navigate(['/login']);
    }
    this.tokenInfo = this.tokenService.getClienteFromToken(this.token!);

    this.getContas();

  }

  getContas() {
    this.contaService.getContasFromClienteId(this.tokenInfo!.id!).subscribe((data) => {
      this.contas = data as unknown as Conta[];
    })
  }

  raiseAlert(message: string, type: string, duration: number) {
    this.alert = new Alert(message, type);
    setTimeout(() => {
      this.selfClosingAlert?.close();
    }, duration);
    setTimeout(() => {
      this.alert = new Alert("", "");
    }, duration + 1000)
  }

  navigateToRoute(rota: string) {
    this.router.navigate([rota]);
  }


  logout() {
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  onSubmit() {
    this.selectedConta = this.contas[this.form.get('conta')?.value];
    this.deposito = new Lancamento(this.form.get('valor')?.value, "CREDITO", this.selectedConta.id!);
    this.lancamentoService.depositar(this.deposito).subscribe({
      next: (response) => {
        this.raiseAlert("Depósito realizado.", "success", 2000);
      },
      error: (error) => {
        this.raiseAlert("Não foi possível realizar o depósito. Tente novamente.", "danger", 2000);
        this.form.reset();
      }
    });
  }
}
