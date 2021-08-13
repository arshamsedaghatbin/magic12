import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAction } from 'app/shared/model/action.model';
import { getEntities as getActions } from 'app/entities/action/action.reducer';
import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { getEntity, updateEntity, createEntity, reset } from './sub-category.reducer';
import { ISubCategory } from 'app/shared/model/sub-category.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SubCategoryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const actions = useAppSelector(state => state.action.entities);
  const categories = useAppSelector(state => state.category.entities);
  const subCategoryEntity = useAppSelector(state => state.subCategory.entity);
  const loading = useAppSelector(state => state.subCategory.loading);
  const updating = useAppSelector(state => state.subCategory.updating);
  const updateSuccess = useAppSelector(state => state.subCategory.updateSuccess);

  const handleClose = () => {
    props.history.push('/sub-category' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getActions({}));
    dispatch(getCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...subCategoryEntity,
      ...values,
      actions: mapIdList(values.actions),
      category: categories.find(it => it.id.toString() === values.categoryId.toString()),
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
          ...subCategoryEntity,
          accountType: 'FREE',
          actions: subCategoryEntity?.actions?.map(e => e.id.toString()),
          categoryId: subCategoryEntity?.category?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="magic12App.subCategory.home.createOrEditLabel" data-cy="SubCategoryCreateUpdateHeading">
            <Translate contentKey="magic12App.subCategory.home.createOrEditLabel">Create or edit a SubCategory</Translate>
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
                  id="sub-category-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('magic12App.subCategory.title')}
                id="sub-category-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('magic12App.subCategory.photoUrl')}
                id="sub-category-photoUrl"
                name="photoUrl"
                data-cy="photoUrl"
                type="text"
              />
              <ValidatedBlobField
                label={translate('magic12App.subCategory.photo')}
                id="sub-category-photo"
                name="photo"
                data-cy="photo"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('magic12App.subCategory.voiceUrl')}
                id="sub-category-voiceUrl"
                name="voiceUrl"
                data-cy="voiceUrl"
                type="text"
              />
              <ValidatedBlobField
                label={translate('magic12App.subCategory.voiceFile')}
                id="sub-category-voiceFile"
                name="voiceFile"
                data-cy="voiceFile"
                openActionLabel={translate('entity.action.open')}
              />
              <ValidatedField
                label={translate('magic12App.subCategory.masterDescription')}
                id="sub-category-masterDescription"
                name="masterDescription"
                data-cy="masterDescription"
                type="text"
              />
              <ValidatedField
                label={translate('magic12App.subCategory.masterAdvice')}
                id="sub-category-masterAdvice"
                name="masterAdvice"
                data-cy="masterAdvice"
                type="text"
              />
              <ValidatedField
                label={translate('magic12App.subCategory.accountType')}
                id="sub-category-accountType"
                name="accountType"
                data-cy="accountType"
                type="select"
              >
                <option value="FREE">{translate('magic12App.AccountType.FREE')}</option>
                <option value="GOLD">{translate('magic12App.AccountType.GOLD')}</option>
                <option value="SILVER">{translate('magic12App.AccountType.SILVER')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('magic12App.subCategory.action')}
                id="sub-category-action"
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
                id="sub-category-category"
                name="categoryId"
                data-cy="category"
                label={translate('magic12App.subCategory.category')}
                type="select"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/sub-category" replace color="info">
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

export default SubCategoryUpdate;
