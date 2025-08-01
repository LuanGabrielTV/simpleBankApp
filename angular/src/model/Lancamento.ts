export class Lancamento {
    valor: number | undefined;
    tipo: string | undefined;
    contaId: number | undefined;
    data: Date | undefined;
    operacao: string | undefined;

    constructor(valor?: number, tipo?: string, contaId?: number, data?: Date, operacao?: string) {
        this.valor = valor;
        this.tipo = tipo;
        this.contaId = contaId;
        this.data = data;
        this.operacao = operacao;
    }
}