import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PracticeSession from './practice-session';
import PracticeSessionDetail from './practice-session-detail';
import PracticeSessionUpdate from './practice-session-update';
import PracticeSessionDeleteDialog from './practice-session-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PracticeSessionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PracticeSessionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PracticeSessionDetail} />
      <ErrorBoundaryRoute path={match.url} component={PracticeSession} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PracticeSessionDeleteDialog} />
  </>
);

export default Routes;
