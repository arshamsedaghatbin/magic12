import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SubCategory from './sub-category';
import SubCategoryDetail from './sub-category-detail';
import SubCategoryUpdate from './sub-category-update';
import SubCategoryDeleteDialog from './sub-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SubCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SubCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SubCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={SubCategory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SubCategoryDeleteDialog} />
  </>
);

export default Routes;
