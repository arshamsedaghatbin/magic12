import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BookMarkAction from './book-mark-action';
import BookMarkActionDetail from './book-mark-action-detail';
import BookMarkActionUpdate from './book-mark-action-update';
import BookMarkActionDeleteDialog from './book-mark-action-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BookMarkActionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BookMarkActionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BookMarkActionDetail} />
      <ErrorBoundaryRoute path={match.url} component={BookMarkAction} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BookMarkActionDeleteDialog} />
  </>
);

export default Routes;
