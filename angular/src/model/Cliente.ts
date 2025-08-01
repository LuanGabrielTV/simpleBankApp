export class Cliente {
    id: string | undefined
    nome: string | undefined
    cpf: string | undefined
    telefone: string | undefined
    password: string | undefined

    constructor(id?: string, nome?: string, cpf?: string, telefone?: string, password?: string) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.password = password;
    }
}