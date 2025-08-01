export class Transferencia {
    origemId: number | undefined;
    destinoId: number | undefined;
    valor: number | undefined;

    constructor(origemId?: number, destinoId?: number, valor?: number) {
        this.origemId = origemId;
        this.destinoId = destinoId;
        this.valor = valor;
    }
}