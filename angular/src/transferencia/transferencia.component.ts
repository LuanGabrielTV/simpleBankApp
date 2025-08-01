import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Alert } from '../model/Alert';
import { Transferencia } from '../model/Transferencia';
import { TokenInfo } from '../model/TokenInfo';
import { Conta } from '../model/Conta';
import { Router, RouterModule } from '@angular/router';
import { LancamentoService } from '../services/lancamento.service';
import { TokenService } from '../services/token.service';
import { ContaService } from '../services/conta.service';
import { NgbAlert } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-transferencia',
  imports: [FormsModule, ReactiveFormsModule, NgbAlert, RouterModule, CommonModule],
  templateUrl: './transferencia.component.html',
  styleUrl: './transferencia.component.scss'
})

export class TransferenciaComponent {
  form: FormGroup;
  transferencia: Transferencia | undefined;
  alert: Alert | undefined;
  selectedContaOrigem: Conta;
  selectedContaDestino: Conta;
  token: string | undefined | null;
  tokenInfo: TokenInfo | undefined;
  contas: Conta[];
  allContas: Conta[];
  selectedContaOrigemIndex: number | undefined;
  selectedContaDestinoIndex: number | undefined;

  @ViewChild('selfClosingAlert', { static: false }) selfClosingAlert: NgbAlert | undefined;

  constructor(private fBuilder: FormBuilder, private router: Router, private lancamentoService: LancamentoService, private tokenService: TokenService, private contaService: ContaService) {
    this.selectedContaOrigem = new Conta();
    this.selectedContaDestino = new Conta();
    this.contas = [];
    this.allContas = [];
    this.transferencia = new Transferencia();
    this.form = this.fBuilder.group({
      'contaOrigem': [this.selectedContaOrigemIndex, Validators.required],
      'contaDestino': [this.selectedContaDestinoIndex, Validators.required],
      'valor': [this.transferencia.valor, Validators.compose([
        Validators.required, Validators.min(0)])]
    });
    this.alert = new Alert("", "");
  }

  getContas() {
    this.contaService.getContasFromClienteId(this.tokenInfo!.id!).subscribe((data) => {
      this.contas = data as unknown as Conta[];
    });
  }

  getAllContas() {
    this.contaService.getContas().subscribe((data) => {
      this.allContas = data as unknown as Conta[];
    });
  }

  ngOnInit() {

    this.token = this.tokenService.getToken();
    if (this.token == null || this.token == undefined) {
      this.router.navigate(['/login']);
    }
    this.tokenInfo = this.tokenService.getClienteFromToken(this.token!);

    this.getContas();
    this.getAllContas();

  }

  logout() {
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);
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

  onSubmit() {
    this.selectedContaOrigem = this.contas[this.form.get('contaOrigem')?.value];
    this.selectedContaDestino = this.allContas[this.form.get('contaDestino')?.value];
    this.transferencia = new Transferencia(this.selectedContaOrigem.id!, this.selectedContaDestino.id!, this.form.get('valor')?.value);

    this.lancamentoService.transferir(this.transferencia).subscribe({
      next: (response) => {
        this.raiseAlert("Transferência realizada.", "success", 2000);
      },
      error: (error) => {
        this.raiseAlert("Não foi possível realizar a transferência. Verifique se há saldo para a operação, e se as contas são diferentes.", "danger", 2000);
        this.form.reset();
      }
    });

  }
}
