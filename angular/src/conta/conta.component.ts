import { Component, ViewChild } from '@angular/core';
import { TokenService } from '../services/token.service';
import { TokenInfo } from '../model/TokenInfo';
import { Router, RouterModule } from '@angular/router';
import { ContaService } from '../services/conta.service';
import { Conta } from '../model/Conta';
import { NgbAlert } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Alert } from '../model/Alert';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-conta',
  imports: [CommonModule, FormsModule, ReactiveFormsModule, NgbAlert, RouterModule],
  templateUrl: './conta.component.html',
  styleUrl: './conta.component.scss'
})
export class ContaComponent {

  token: string | undefined | null;
  tokenInfo: TokenInfo | undefined;
  contas: Conta[];
  selectedConta: Conta;
  form: FormGroup;
  alert: Alert | undefined;
  selectedContaIndex: number | undefined;
  novoLimite: number | undefined;
  @ViewChild('selfClosingAlert', { static: false }) selfClosingAlert: NgbAlert | undefined;

  constructor(private fBuilder: FormBuilder, private tokenService: TokenService, private contaService: ContaService, private router: Router) {
    this.contas = [];
    this.selectedConta = new Conta();
    this.form = this.fBuilder.group({
      'conta': [this.selectedContaIndex, Validators.required],
      'valor': [this.novoLimite, Validators.compose([
        Validators.required, Validators.min(0)])]
    });
    this.alert = new Alert("", "");
  }

  ngOnInit(): void {

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

  criarNovaConta() {
    this.contaService.criarNovaConta(this.tokenInfo!.sub!).subscribe((data) => {
      console.log(data)
      this.getContas();
    });
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
  
  navigateToRoute(rota: string){
    this.router.navigate([rota]);
  }

  onSubmit() {
    this.selectedConta = this.contas[this.form.get('conta')?.value];
    this.novoLimite = this.form.get('valor')?.value;
    this.selectedConta.limite = this.novoLimite;
    this.contaService.alterar(this.selectedConta).subscribe({
      next: (response) => {
        this.raiseAlert("Limite atualizado.", "success", 2000);
      },
      error: (error) => {
        this.raiseAlert("O novo limite deve ser maior do que o atual. Tente novamente.", "danger", 2000);
        this.form.reset();
      }
    });
  }

  logout() {
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
