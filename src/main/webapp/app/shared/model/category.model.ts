import { ISubCategory } from 'app/shared/model/sub-category.model';
import { IAction } from 'app/shared/model/action.model';
import { AccountType } from 'app/shared/model/enumerations/account-type.model';

export interface ICategory {
  id?: number;
  title?: string | null;
  photoUrl?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  voiceUrl?: string | null;
  voiceFileContentType?: string | null;
  voiceFile?: string | null;
  description?: string | null;
  advice?: string | null;
  accountType?: AccountType | null;
  subcategories?: ISubCategory[] | null;
  actions?: IAction[] | null;
}

export const defaultValue: Readonly<ICategory> = {};
