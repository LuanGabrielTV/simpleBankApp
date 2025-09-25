import { Component, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClienteService } from '../services/cliente.service';
import { Cliente } from '../model/Cliente';
import { CommonModule } from '@angular/common';
import { Alert } from '../model/Alert';
import { NgbAlert } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-register',
  imports: [FormsModule, ReactiveFormsModule, CommonModule, NgbAlert],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  form: FormGroup;
  cliente: Cliente | undefined;
  alert: Alert | undefined;
  @ViewChild('selfClosingAlert', { static: false }) selfClosingAlert: NgbAlert | undefined;

  constructor(private fBuilder: FormBuilder, private router: Router, private clienteService: ClienteService) {
    this.cliente = new Cliente();
    this.form = this.fBuilder.group({
      'nome': [this.cliente.nome, Validators.required],
      'cpf': [this.cliente.cpf, Validators.compose([
        Validators.required, Validators.minLength(11), Validators.maxLength(11)])],
      'password': [this.cliente.password, Validators.compose([
        Validators.required, Validators.minLength(8), Validators.maxLength(16)])],
      'telefone': [this.cliente.telefone, Validators.compose([
        Validators.required, Validators.minLength(11), Validators.maxLength(11)])],
    });
    this.alert = new Alert("", "");
  }

  goToLogin() {
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
    this.cliente = new Cliente(undefined, this.form.get('nome')?.value, this.form.get('cpf')?.value, this.form.get('telefone')?.value, this.form.get('password')?.value);
    this.clienteService.register(this.cliente).subscribe({
      next: (response) => {
        this.router.navigate(['/login']);
      },
      error: (error) => {
        this.raiseAlert("Esse cpf já está cadastrado.", "danger", 2000);
        this.form.get('cpf')?.setValue("");
      }
    });
  }

}
