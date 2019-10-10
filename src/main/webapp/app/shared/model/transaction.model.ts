import { IClient } from 'app/shared/model/client.model';
import { IUser } from 'app/core/user/user.model';

export interface ITransaction {
  id?: number;
  frais?: number;
  montant?: number;
  commissionEtat?: number;
  commissionSysteme?: number;
  commissionReceveur?: number;
  commissionEnvoyeur?: number;
  clientEnvoyeur?: IClient;
  clientReceveur?: IClient;
  userEnvoyer?: IUser;
  userReceveur?: IUser;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public frais?: number,
    public montant?: number,
    public commissionEtat?: number,
    public commissionSysteme?: number,
    public commissionReceveur?: number,
    public commissionEnvoyeur?: number,
    public clientEnvoyeur?: IClient,
    public clientReceveur?: IClient,
    public userEnvoyer?: IUser,
    public userReceveur?: IUser
  ) {}
}
