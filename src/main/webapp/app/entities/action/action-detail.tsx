import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './action.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ActionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const actionEntity = useAppSelector(state => state.action.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="actionDetailsHeading">
          <Translate contentKey="magic12App.action.detail.title">Action</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{actionEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="magic12App.action.title">Title</Translate>
            </span>
          </dt>
          <dd>{actionEntity.title}</dd>
          <dt>
            <span id="photoUrl">
              <Translate contentKey="magic12App.action.photoUrl">Photo Url</Translate>
            </span>
          </dt>
          <dd>{actionEntity.photoUrl}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="magic12App.action.photo">Photo</Translate>
            </span>
          </dt>
          <dd>
            {actionEntity.photo ? (
              <div>
                {actionEntity.photoContentType ? (
                  <a onClick={openFile(actionEntity.photoContentType, actionEntity.photo)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {actionEntity.photoContentType}, {byteSize(actionEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="code">
              <Translate contentKey="magic12App.action.code">Code</Translate>
            </span>
          </dt>
          <dd>{actionEntity.code}</dd>
          <dt>
            <span id="video">
              <Translate contentKey="magic12App.action.video">Video</Translate>
            </span>
          </dt>
          <dd>
            {actionEntity.video ? (
              <div>
                {actionEntity.videoContentType ? (
                  <a onClick={openFile(actionEntity.videoContentType, actionEntity.video)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {actionEntity.videoContentType}, {byteSize(actionEntity.video)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="videoUrl">
              <Translate contentKey="magic12App.action.videoUrl">Video Url</Translate>
            </span>
          </dt>
          <dd>{actionEntity.videoUrl}</dd>
          <dt>
            <span id="masterDescription">
              <Translate contentKey="magic12App.action.masterDescription">Master Description</Translate>
            </span>
          </dt>
          <dd>{actionEntity.masterDescription}</dd>
          <dt>
            <span id="accountType">
              <Translate contentKey="magic12App.action.accountType">Account Type</Translate>
            </span>
          </dt>
          <dd>{actionEntity.accountType}</dd>
        </dl>
        <Button tag={Link} to="/action" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/action/${actionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ActionDetail;
