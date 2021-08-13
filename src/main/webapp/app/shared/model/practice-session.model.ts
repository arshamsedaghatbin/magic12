import { IAction } from 'app/shared/model/action.model';
import { IPractice } from 'app/shared/model/practice.model';

export interface IPracticeSession {
  id?: number;
  tiltle?: string | null;
  actions?: IAction[] | null;
  practice?: IPractice | null;
}

export const defaultValue: Readonly<IPracticeSession> = {};
