import {EmailVerification} from './emailVerification';
import {Role} from './role';

export class User {
  public id: number;
  public firstName: string;
  public secondName: string;
  public email: string;
  public password: string;
  public role: Role;
  public emailVerification: EmailVerification;
}
