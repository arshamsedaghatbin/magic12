import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Category from './category';
import SubCategory from './sub-category';
import Action from './action';
import BookMarkAction from './book-mark-action';
import Practice from './practice';
import PracticeSession from './practice-session';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}category`} component={Category} />
      <ErrorBoundaryRoute path={`${match.url}sub-category`} component={SubCategory} />
      <ErrorBoundaryRoute path={`${match.url}action`} component={Action} />
      <ErrorBoundaryRoute path={`${match.url}book-mark-action`} component={BookMarkAction} />
      <ErrorBoundaryRoute path={`${match.url}practice`} component={Practice} />
      <ErrorBoundaryRoute path={`${match.url}practice-session`} component={PracticeSession} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
