import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './practice-session.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PracticeSessionDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const practiceSessionEntity = useAppSelector(state => state.practiceSession.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="practiceSessionDetailsHeading">
          <Translate contentKey="magic12App.practiceSession.detail.title">PracticeSession</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{practiceSessionEntity.id}</dd>
          <dt>
            <span id="tiltle">
              <Translate contentKey="magic12App.practiceSession.tiltle">Tiltle</Translate>
            </span>
          </dt>
          <dd>{practiceSessionEntity.tiltle}</dd>
          <dt>
            <Translate contentKey="magic12App.practiceSession.action">Action</Translate>
          </dt>
          <dd>
            {practiceSessionEntity.actions
              ? practiceSessionEntity.actions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {practiceSessionEntity.actions && i === practiceSessionEntity.actions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="magic12App.practiceSession.practice">Practice</Translate>
          </dt>
          <dd>{practiceSessionEntity.practice ? practiceSessionEntity.practice.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/practice-session" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/practice-session/${practiceSessionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PracticeSessionDetail;
