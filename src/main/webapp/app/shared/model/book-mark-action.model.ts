import { IAction } from 'app/shared/model/action.model';

export interface IBookMarkAction {
  id?: number;
  userDescription?: string | null;
  action?: IAction | null;
}

export const defaultValue: Readonly<IBookMarkAction> = {};
