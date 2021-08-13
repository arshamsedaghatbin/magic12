import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAction } from 'app/shared/model/action.model';
import { getEntities as getActions } from 'app/entities/action/action.reducer';
import { IPractice } from 'app/shared/model/practice.model';
import { getEntities as getPractices } from 'app/entities/practice/practice.reducer';
import { getEntity, updateEntity, createEntity, reset } from './practice-session.reducer';
import { IPracticeSession } from 'app/shared/model/practice-session.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PracticeSessionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const actions = useAppSelector(state => state.action.entities);
  const practices = useAppSelector(state => state.practice.entities);
  const practiceSessionEntity = useAppSelector(state => state.practiceSession.entity);
  const loading = useAppSelector(state => state.practiceSession.loading);
  const updating = useAppSelector(state => state.practiceSession.updating);
  const updateSuccess = useAppSelector(state => state.practiceSession.updateSuccess);

  const handleClose = () => {
    props.history.push('/practice-session' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getActions({}));
    dispatch(getPractices({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...practiceSessionEntity,
      ...values,
      actions: mapIdList(values.actions),
      practice: practices.find(it => it.id.toString() === values.practiceId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...practiceSessionEntity,
          actions: practiceSessionEntity?.actions?.map(e => e.id.toString()),
          practiceId: practiceSessionEntity?.practice?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="magic12App.practiceSession.home.createOrEditLabel" data-cy="PracticeSessionCreateUpdateHeading">
            <Translate contentKey="magic12App.practiceSession.home.createOrEditLabel">Create or edit a PracticeSession</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="practice-session-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('magic12App.practiceSession.tiltle')}
                id="practice-session-tiltle"
                name="tiltle"
                data-cy="tiltle"
                type="text"
              />
              <ValidatedField
                label={translate('magic12App.practiceSession.action')}
                id="practice-session-action"
                data-cy="action"
                type="select"
                multiple
                name="actions"
              >
                <option value="" key="0" />
                {actions
                  ? actions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="practice-session-practice"
                name="practiceId"
                data-cy="practice"
                label={translate('magic12App.practiceSession.practice')}
                type="select"
              >
                <option value="" key="0" />
                {practices
                  ? practices.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/practice-session" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PracticeSessionUpdate;
