import { Injectable } from '@angular/core';
import { jwtDecode } from "jwt-decode";
import { TokenInfo } from '../model/TokenInfo';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  getToken() {
    return JSON.parse(sessionStorage.getItem('token')!);
  }

  getClienteFromToken(token: string) {
    return jwtDecode(token) as TokenInfo;
  }
}
