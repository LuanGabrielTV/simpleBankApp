import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { NgbAlert } from '@ng-bootstrap/ng-bootstrap';
import { Lancamento } from '../model/Lancamento';
import { Alert } from '../model/Alert';
import { Router, RouterModule } from '@angular/router';
import { Conta } from '../model/Conta';
import { LancamentoService } from '../services/lancamento.service';
import { TokenInfo } from '../model/TokenInfo';
import { TokenService } from '../services/token.service';
import { ContaService } from '../services/conta.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-saque',
  imports: [FormsModule, ReactiveFormsModule, NgbAlert, RouterModule, CommonModule],
  templateUrl: './saque.component.html',
  styleUrl: './saque.component.scss'
})
export class SaqueComponent implements OnInit {

  form: FormGroup;
  saque: Lancamento | undefined;
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
    this.saque = new Lancamento();
    this.form = this.fBuilder.group({
      'conta': [this.selectedContaIndex, Validators.required],
      'valor': [this.saque.valor, Validators.compose([
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

  logout() {
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  onSubmit() {
    this.selectedConta = this.contas[this.form.get('conta')?.value];
    this.saque = new Lancamento(this.form.get('valor')?.value, "DEBITO", this.selectedConta.id!);
    this.lancamentoService.sacar(this.saque).subscribe({
      next: (response) => {
        this.raiseAlert("Saque realizado.", "success", 2000);
      },
      error: (error) => {
        this.raiseAlert("Não há saldo para tal saque.", "danger", 2000);
        this.form.get('valor')?.setValue(0);
      }
    });
  }
}
