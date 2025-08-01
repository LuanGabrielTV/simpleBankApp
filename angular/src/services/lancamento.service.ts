import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TokenService } from './token.service';
import { Lancamento } from '../model/Lancamento';
import { Transferencia } from '../model/Transferencia';

@Injectable({
  providedIn: 'root'
})
export class LancamentoService {

  url = 'http://localhost:8080/api/lancamento/';
  httpOptions: any;

  constructor(private httpClient: HttpClient, private tokenService: TokenService) {
    this.httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': "http://localhost:4200", 'Authorization': 'Bearer ' + this.tokenService.getToken() })
    };
  }

  getLancamentosFromContaId(id: number) {

    let url = this.url + 'conta/' + id;
    return this.httpClient.get<Lancamento[]>(url, this.httpOptions);

  }

  getSaldoFromClienteId(id: number) {
    let url = this.url + 'saldo/' + id;
    return this.httpClient.get<number>(url, this.httpOptions);
  }

  sacar(saque: Lancamento) {
    return this.httpClient.post<Lancamento>(this.url, JSON.stringify(saque), this.httpOptions);

  }

  depositar(deposito: Lancamento) {
    return this.httpClient.post<Lancamento>(this.url, JSON.stringify(deposito), this.httpOptions);

  }

  transferir(transferencia: Transferencia) {

    let url = this.url + 'transferir';
    console.log(url)
    return this.httpClient.post<Transferencia>(url, JSON.stringify(transferencia), this.httpOptions);

  }
}
