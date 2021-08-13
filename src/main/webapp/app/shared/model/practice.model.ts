import { IPracticeSession } from 'app/shared/model/practice-session.model';
import { AccountType } from 'app/shared/model/enumerations/account-type.model';

export interface IPractice {
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
  briefMasterAdvice?: string | null;
  accountType?: AccountType | null;
  sessions?: IPracticeSession[] | null;
}

export const defaultValue: Readonly<IPractice> = {};
