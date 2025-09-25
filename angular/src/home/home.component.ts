import { Component, OnInit } from '@angular/core';
import { TokenService } from '../services/token.service';
import { TokenInfo } from '../model/TokenInfo';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LancamentoService } from '../services/lancamento.service';

@Component({
  selector: 'app-home',
  imports: [RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {

  token: string | undefined | null;
  tokenInfo: TokenInfo | undefined;
  saldo: number | undefined;

  constructor(private tokenService: TokenService, private lancamentoService: LancamentoService, private router: Router) {

  }

  navigateToRoute(rota: string){
    this.router.navigate([rota]);
  }

  ngOnInit(): void {

    this.token = this.tokenService.getToken();
    if (this.token == null || this.token == undefined) {
      this.router.navigate(['/login']);
    }
    this.tokenInfo = this.tokenService.getClienteFromToken(this.token!);
    this.lancamentoService.getSaldoFromClienteId(this.tokenInfo.id!).subscribe((data) => {
      this.saldo = data as unknown as number;
    });
  }

  logout() {
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

}
