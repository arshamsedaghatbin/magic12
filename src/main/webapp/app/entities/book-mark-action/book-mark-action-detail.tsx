import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './book-mark-action.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BookMarkActionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const bookMarkActionEntity = useAppSelector(state => state.bookMarkAction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookMarkActionDetailsHeading">
          <Translate contentKey="magic12App.bookMarkAction.detail.title">BookMarkAction</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookMarkActionEntity.id}</dd>
          <dt>
            <span id="userDescription">
              <Translate contentKey="magic12App.bookMarkAction.userDescription">User Description</Translate>
            </span>
          </dt>
          <dd>{bookMarkActionEntity.userDescription}</dd>
          <dt>
            <Translate contentKey="magic12App.bookMarkAction.action">Action</Translate>
          </dt>
          <dd>{bookMarkActionEntity.action ? bookMarkActionEntity.action.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/book-mark-action" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/book-mark-action/${bookMarkActionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BookMarkActionDetail;
