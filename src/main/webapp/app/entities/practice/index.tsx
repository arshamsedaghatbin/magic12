import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Practice from './practice';
import PracticeDetail from './practice-detail';
import PracticeUpdate from './practice-update';
import PracticeDeleteDialog from './practice-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PracticeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PracticeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PracticeDetail} />
      <ErrorBoundaryRoute path={match.url} component={Practice} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PracticeDeleteDialog} />
  </>
);

export default Routes;
