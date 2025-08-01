import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Auth } from '../model/Auth';
import { Cliente } from '../model/Cliente';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  url = 'http://localhost:8080/api/cliente/';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*', })
  };

  constructor(private httpClient: HttpClient) { }

  login(auth: Auth) {
    let url = 'http://localhost:8080/api/auth/login';
    return this.httpClient.post(url, JSON.stringify(auth), this.httpOptions);
  }

  register(cliente: Cliente){
    let url = 'http://localhost:8080/api/auth/register';
    return this.httpClient.post(url, JSON.stringify(cliente), this.httpOptions);
  }
  
}
