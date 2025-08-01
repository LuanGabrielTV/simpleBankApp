import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClienteService } from '../services/cliente.service';
import { Auth } from '../model/Auth';
import { NgbAlert, NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { Alert } from '../model/Alert';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [FormsModule, ReactiveFormsModule, NgbAlertModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})

export class LoginComponent {

  form: FormGroup;
  auth: Auth;
  @ViewChild('selfClosingAlert', { static: false }) selfClosingAlert: NgbAlert | undefined;
  alert: Alert | undefined;

  constructor(private fBuilder: FormBuilder, private router: Router, private clienteService: ClienteService) {
    this.auth = new Auth();
    this.form = this.fBuilder.group({
      'cpf': [this.auth.cpf, Validators.compose([
        Validators.required, Validators.minLength(11), Validators.maxLength(11)])],
      'password': [this.auth.password, Validators.required]
    });
    this.alert = new Alert("", "");
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }

  raiseAlert(message: string, type: string, duration: number){
    this.alert = new Alert(message, type);
    setTimeout(() => {
      this.selfClosingAlert?.close();
    }, duration);
    setTimeout(() => {
      this.alert = new Alert("", "");
    }, duration + 1000)
  }

  onSubmit() {
    this.auth = new Auth(this.form.get('cpf')?.value, this.form.get('password')?.value);
    this.clienteService.login(this.auth).subscribe({
      next: (token: any) => {
        sessionStorage.setItem('token', JSON.stringify(token['token']));
        this.raiseAlert("Seja bem-vindo!", "success", 2000);
        this.router.navigate(['/home']);
      },
      error: (error) => {
        this.form.reset();
        this.raiseAlert("As credenciais estão erradas! Tente novamente.", "danger", 2000);
      }
    });
  }

}
;