import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './category.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CategoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const categoryEntity = useAppSelector(state => state.category.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoryDetailsHeading">
          <Translate contentKey="magic12App.category.detail.title">Category</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="magic12App.category.title">Title</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.title}</dd>
          <dt>
            <span id="photoUrl">
              <Translate contentKey="magic12App.category.photoUrl">Photo Url</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.photoUrl}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="magic12App.category.photo">Photo</Translate>
            </span>
          </dt>
          <dd>
            {categoryEntity.photo ? (
              <div>
                {categoryEntity.photoContentType ? (
                  <a onClick={openFile(categoryEntity.photoContentType, categoryEntity.photo)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {categoryEntity.photoContentType}, {byteSize(categoryEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="voiceUrl">
              <Translate contentKey="magic12App.category.voiceUrl">Voice Url</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.voiceUrl}</dd>
          <dt>
            <span id="voiceFile">
              <Translate contentKey="magic12App.category.voiceFile">Voice File</Translate>
            </span>
          </dt>
          <dd>
            {categoryEntity.voiceFile ? (
              <div>
                {categoryEntity.voiceFileContentType ? (
                  <a onClick={openFile(categoryEntity.voiceFileContentType, categoryEntity.voiceFile)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {categoryEntity.voiceFileContentType}, {byteSize(categoryEntity.voiceFile)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="description">
              <Translate contentKey="magic12App.category.description">Description</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.description}</dd>
          <dt>
            <span id="advice">
              <Translate contentKey="magic12App.category.advice">Advice</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.advice}</dd>
          <dt>
            <span id="accountType">
              <Translate contentKey="magic12App.category.accountType">Account Type</Translate>
            </span>
          </dt>
          <dd>{categoryEntity.accountType}</dd>
          <dt>
            <Translate contentKey="magic12App.category.action">Action</Translate>
          </dt>
          <dd>
            {categoryEntity.actions
              ? categoryEntity.actions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {categoryEntity.actions && i === categoryEntity.actions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/category/${categoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategoryDetail;
