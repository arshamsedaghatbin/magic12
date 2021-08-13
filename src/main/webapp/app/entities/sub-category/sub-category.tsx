import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './sub-category.reducer';
import { ISubCategory } from 'app/shared/model/sub-category.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SubCategory = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const subCategoryList = useAppSelector(state => state.subCategory.entities);
  const loading = useAppSelector(state => state.subCategory.loading);
  const totalItems = useAppSelector(state => state.subCategory.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="sub-category-heading" data-cy="SubCategoryHeading">
        <Translate contentKey="magic12App.subCategory.home.title">Sub Categories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="magic12App.subCategory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="magic12App.subCategory.home.createLabel">Create new Sub Category</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {subCategoryList && subCategoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="magic12App.subCategory.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('title')}>
                  <Translate contentKey="magic12App.subCategory.title">Title</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('photoUrl')}>
                  <Translate contentKey="magic12App.subCategory.photoUrl">Photo Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('photo')}>
                  <Translate contentKey="magic12App.subCategory.photo">Photo</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('voiceUrl')}>
                  <Translate contentKey="magic12App.subCategory.voiceUrl">Voice Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('voiceFile')}>
                  <Translate contentKey="magic12App.subCategory.voiceFile">Voice File</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('masterDescription')}>
                  <Translate contentKey="magic12App.subCategory.masterDescription">Master Description</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('masterAdvice')}>
                  <Translate contentKey="magic12App.subCategory.masterAdvice">Master Advice</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('accountType')}>
                  <Translate contentKey="magic12App.subCategory.accountType">Account Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="magic12App.subCategory.category">Category</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {subCategoryList.map((subCategory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${subCategory.id}`} color="link" size="sm">
                      {subCategory.id}
                    </Button>
                  </td>
                  <td>{subCategory.title}</td>
                  <td>{subCategory.photoUrl}</td>
                  <td>
                    {subCategory.photo ? (
                      <div>
                        {subCategory.photoContentType ? (
                          <a onClick={openFile(subCategory.photoContentType, subCategory.photo)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {subCategory.photoContentType}, {byteSize(subCategory.photo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{subCategory.voiceUrl}</td>
                  <td>
                    {subCategory.voiceFile ? (
                      <div>
                        {subCategory.voiceFileContentType ? (
                          <a onClick={openFile(subCategory.voiceFileContentType, subCategory.voiceFile)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {subCategory.voiceFileContentType}, {byteSize(subCategory.voiceFile)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{subCategory.masterDescription}</td>
                  <td>{subCategory.masterAdvice}</td>
                  <td>
                    <Translate contentKey={`magic12App.AccountType.${subCategory.accountType}`} />
                  </td>
                  <td>{subCategory.category ? <Link to={`category/${subCategory.category.id}`}>{subCategory.category.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${subCategory.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${subCategory.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${subCategory.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="magic12App.subCategory.home.notFound">No Sub Categories found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={subCategoryList && subCategoryList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default SubCategory;
