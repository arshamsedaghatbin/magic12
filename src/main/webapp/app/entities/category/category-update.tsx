import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAction } from 'app/shared/model/action.model';
import { getEntities as getActions } from 'app/entities/action/action.reducer';
import { getEntity, updateEntity, createEntity, reset } from './category.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CategoryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const actions = useAppSelector(state => state.action.entities);
  const categoryEntity = useAppSelector(state => state.category.entity);
  const loading = useAppSelector(state => state.category.loading);
  const updating = useAppSelector(state => state.category.updating);
  const updateSuccess = useAppSelector(state => state.category.updateSuccess);

  const handleClose = () => {
    props.history.push('/category' + props.location.search);
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
      ...categoryEntity,
      ...values,
      actions: mapIdList(values.actions),
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
          ...categoryEntity,
          accountType: 'FREE',
          actions: categoryEntity?.actions?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="magic12App.category.home.createOrEditLabel" data-cy="CategoryCreateUpdateHeading">
            <Translate contentKey="magic12App.category.home.createOrEditLabel">Create or edit a Category</Translate>
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
                  id="category-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('magic12App.category.title')} id="category-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label={translate('magic12App.category.photoUrl')}
                id="category-photoUrl"
                name="photoUrl"
                data-cy="photoUrl"
                type="text"
              />
              <ValidatedBlobField
                label={translate('magic12App.category.photo')}
                id="category-photo"
                name="photo"
                data-cy="photo"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('magic12App.category.voiceUrl')}
                id="category-voiceUrl"
                name="voiceUrl"
                data-cy="voiceUrl"
                type="text"
              />
              <ValidatedBlobField
                label={translate('magic12App.category.voiceFile')}
                id="category-voiceFile"
                name="voiceFile"
                data-cy="voiceFile"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('magic12App.category.description')}
                id="category-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('magic12App.category.advice')}
                id="category-advice"
                name="advice"
                data-cy="advice"
                type="text"
              />
              <ValidatedField
                label={translate('magic12App.category.accountType')}
                id="category-accountType"
                name="accountType"
                data-cy="accountType"
                type="select"
              >
                <option value="FREE">{translate('magic12App.AccountType.FREE')}</option>
                <option value="GOLD">{translate('magic12App.AccountType.GOLD')}</option>
                <option value="SILVER">{translate('magic12App.AccountType.SILVER')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('magic12App.category.action')}
                id="category-action"
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/category" replace color="info">
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

export default CategoryUpdate;
