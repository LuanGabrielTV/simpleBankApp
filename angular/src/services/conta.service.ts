import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Conta } from '../model/Conta';

@Injectable({
  providedIn: 'root'
})
export class ContaService {

  url = 'http://localhost:8080/api/conta/';
  httpOptions: any;

  constructor(private httpClient: HttpClient, private tokenService: TokenService) {
    this.httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "http://localhost:4200/", 'Authorization': 'Bearer ' + this.tokenService.getToken() })
    };
  }

  getContas() {
    return this.httpClient.get<Conta[]>(this.url, this.httpOptions);

  }

  getContasFromClienteId(id: number) {

    let url = this.url + 'cliente/' + id;
    return this.httpClient.get<Conta[]>(url, this.httpOptions);

  }

  alterar(conta: Conta) {
    console.log(JSON.stringify(conta));
    return this.httpClient.put<Conta[]>(this.url, JSON.stringify(conta), this.httpOptions);
  }

  criarNovaConta(cpf: string) {
    let conta = new Conta();
    conta.cpf = cpf;
    console.log(JSON.stringify(conta));
    return this.httpClient.post<Conta>(this.url, JSON.stringify(conta), this.httpOptions);

  }
}
