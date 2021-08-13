import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { ISubCategory } from 'app/shared/model/sub-category.model';
import { getEntities as getSubCategories } from 'app/entities/sub-category/sub-category.reducer';
import { IPracticeSession } from 'app/shared/model/practice-session.model';
import { getEntities as getPracticeSessions } from 'app/entities/practice-session/practice-session.reducer';
import { getEntity, updateEntity, createEntity, reset } from './action.reducer';
import { IAction } from 'app/shared/model/action.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ActionUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const categories = useAppSelector(state => state.category.entities);
  const subCategories = useAppSelector(state => state.subCategory.entities);
  const practiceSessions = useAppSelector(state => state.practiceSession.entities);
  const actionEntity = useAppSelector(state => state.action.entity);
  const loading = useAppSelector(state => state.action.loading);
  const updating = useAppSelector(state => state.action.updating);
  const updateSuccess = useAppSelector(state => state.action.updateSuccess);

  const handleClose = () => {
    props.history.push('/action' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getCategories({}));
    dispatch(getSubCategories({}));
    dispatch(getPracticeSessions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...actionEntity,
      ...values,
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
          ...actionEntity,
          accountType: 'FREE',
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="magic12App.action.home.createOrEditLabel" data-cy="ActionCreateUpdateHeading">
            <Translate contentKey="magic12App.action.home.createOrEditLabel">Create or edit a Action</Translate>
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
                  id="action-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('magic12App.action.title')} id="action-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('magic12App.action.photoUrl')}
                id="action-photoUrl"
                name="photoUrl"
                data-cy="photoUrl"
                type="text"
              />
              <ValidatedBlobField
                label={translate('magic12App.action.photo')}
                id="action-photo"
                name="photo"
                data-cy="photo"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('magic12App.action.code')}
                id="action-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{}}
              />
              <ValidatedBlobField
                label={translate('magic12App.action.video')}
                id="action-video"
                name="video"
                data-cy="video"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('magic12App.action.videoUrl')}
                id="action-videoUrl"
                name="videoUrl"
                data-cy="videoUrl"
                type="text"
              />
              <ValidatedField
                label={translate('magic12App.action.masterDescription')}
                id="action-masterDescription"
                name="masterDescription"
                data-cy="masterDescription"
                type="text"
              />
              <ValidatedField
                label={translate('magic12App.action.accountType')}
                id="action-accountType"
                name="accountType"
                data-cy="accountType"
                type="select"
              >
                <option value="FREE">{translate('magic12App.AccountType.FREE')}</option>
                <option value="GOLD">{translate('magic12App.AccountType.GOLD')}</option>
                <option value="SILVER">{translate('magic12App.AccountType.SILVER')}</option>
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/action" replace color="info">
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

export default ActionUpdate;
