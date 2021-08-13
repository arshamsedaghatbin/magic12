import { IBookMarkAction } from 'app/shared/model/book-mark-action.model';
import { ICategory } from 'app/shared/model/category.model';
import { ISubCategory } from 'app/shared/model/sub-category.model';
import { IPracticeSession } from 'app/shared/model/practice-session.model';
import { AccountType } from 'app/shared/model/enumerations/account-type.model';

export interface IAction {
  id?: number;
  title?: string | null;
  photoUrl?: string | null;
  photoContentType?: string | null;
  photo?: string | null;
  code?: string | null;
  videoContentType?: string | null;
  video?: string | null;
  videoUrl?: string | null;
  masterDescription?: string | null;
  accountType?: AccountType | null;
  bookMarks?: IBookMarkAction[] | null;
  categories?: ICategory[] | null;
  subCategories?: ISubCategory[] | null;
  sessions?: IPracticeSession[] | null;
}

export const defaultValue: Readonly<IAction> = {};
