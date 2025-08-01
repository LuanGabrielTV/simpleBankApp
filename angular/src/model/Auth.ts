export class Auth {
    cpf: string | undefined;
    password: string | undefined;

    constructor(cpf?: string, password?: string){
        this.cpf = cpf;
        this.password = password;
    }
}