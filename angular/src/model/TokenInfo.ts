export class TokenInfo {
    id: number | undefined;
    iss: string | undefined;
    sub: string | undefined;
    name: string | undefined;

    constructor(id?: number, iss?: string, sub?: string, name?: string){
        this.id = id;
        this.iss = iss;
        this.sub = sub;
        this.name = name;
    }
}