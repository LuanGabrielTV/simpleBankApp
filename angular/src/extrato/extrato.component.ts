import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { LancamentoService } from '../services/lancamento.service';
import { TokenService } from '../services/token.service';
import { ContaService } from '../services/conta.service';
import { Conta } from '../model/Conta';
import { Lancamento } from '../model/Lancamento';
import { TokenInfo } from '../model/TokenInfo';
import { CommonModule, formatDate } from '@angular/common';

@Component({
  selector: 'app-extrato',
  imports: [FormsModule, ReactiveFormsModule, RouterModule, CommonModule],
  templateUrl: './extrato.component.html',
  styleUrl: './extrato.component.scss'
})
export class ExtratoComponent implements OnInit {

  form: FormGroup;
  lancamentos: Lancamento[];
  selectedConta: Conta;
  token: string | undefined | null;
  tokenInfo: TokenInfo | undefined;
  contas: Conta[];
  selectedContaIndex: number | undefined;

  constructor(private fBuilder: FormBuilder, private router: Router, private lancamentoService: LancamentoService, private tokenService: TokenService, private contaService: ContaService) {
    this.selectedConta = new Conta();
    this.contas = [];
    this.lancamentos = [];
    this.form = this.fBuilder.group({
      'conta': [this.selectedContaIndex, Validators.required]
    });
  }

  ngOnInit() {

    this.token = this.tokenService.getToken();
    if (this.token == null || this.token == undefined) {
      this.router.navigate(['/login']);
    }
    this.tokenInfo = this.tokenService.getClienteFromToken(this.token!);

    this.getContas();

  }

  logout() {
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);
  }


  getContas() {
    this.contaService.getContasFromClienteId(this.tokenInfo!.id!).subscribe((data) => {
      this.contas = data as unknown as Conta[];
    })
  }

  onSubmit() {
    this.selectedConta = this.contas[this.form.get('conta')?.value];
    this.lancamentoService.getLancamentosFromContaId(this.selectedConta.id!).subscribe((data) => {
      this.lancamentos = data as unknown as Lancamento[];
    });
  }
}
