export interface IClient {
  id?: number;
  prenom?: string;
  nom?: string;
  telephone?: string;
  cni?: string;
}

export class Client implements IClient {
  constructor(public id?: number, public prenom?: string, public nom?: string, public telephone?: string, public cni?: string) {}
}
