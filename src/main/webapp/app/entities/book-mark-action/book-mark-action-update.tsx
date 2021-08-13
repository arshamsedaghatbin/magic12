import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAction } from 'app/shared/model/action.model';
import { getEntities as getActions } from 'app/entities/action/action.reducer';
import { getEntity, updateEntity, createEntity, reset } from './book-mark-action.reducer';
import { IBookMarkAction } from 'app/shared/model/book-mark-action.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BookMarkActionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const actions = useAppSelector(state => state.action.entities);
  const bookMarkActionEntity = useAppSelector(state => state.bookMarkAction.entity);
  const loading = useAppSelector(state => state.bookMarkAction.loading);
  const updating = useAppSelector(state => state.bookMarkAction.updating);
  const updateSuccess = useAppSelector(state => state.bookMarkAction.updateSuccess);

  const handleClose = () => {
    props.history.push('/book-mark-action' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getActions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bookMarkActionEntity,
      ...values,
      action: actions.find(it => it.id.toString() === values.actionId.toString()),
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
          ...bookMarkActionEntity,
          actionId: bookMarkActionEntity?.action?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="magic12App.bookMarkAction.home.createOrEditLabel" data-cy="BookMarkActionCreateUpdateHeading">
            <Translate contentKey="magic12App.bookMarkAction.home.createOrEditLabel">Create or edit a BookMarkAction</Translate>
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
                  id="book-mark-action-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('magic12App.bookMarkAction.userDescription')}
                id="book-mark-action-userDescription"
                name="userDescription"
                data-cy="userDescription"
                type="text"
              />
              <ValidatedField
                id="book-mark-action-action"
                name="actionId"
                data-cy="action"
                label={translate('magic12App.bookMarkAction.action')}
                type="select"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/book-mark-action" replace color="info">
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

export default BookMarkActionUpdate;
