import { IAction } from 'app/shared/model/action.model';
import { ICategory } from 'app/shared/model/category.model';
import { AccountType } from 'app/shared/model/enumerations/account-type.model';

export interface ISubCategory {
  id?: number;
  title?: string | null;
  photoUrl?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  voiceUrl?: string | null;
  voiceFileContentType?: string | null;
  voiceFile?: string | null;
  masterDescription?: string | null;
  masterAdvice?: string | null;
  accountType?: AccountType | null;
  actions?: IAction[] | null;
  category?: ICategory | null;
}

export const defaultValue: Readonly<ISubCategory> = {};
