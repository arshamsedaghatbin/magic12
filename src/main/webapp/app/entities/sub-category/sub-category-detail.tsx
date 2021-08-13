import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './sub-category.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SubCategoryDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const subCategoryEntity = useAppSelector(state => state.subCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="subCategoryDetailsHeading">
          <Translate contentKey="magic12App.subCategory.detail.title">SubCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{subCategoryEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="magic12App.subCategory.title">Title</Translate>
            </span>
          </dt>
          <dd>{subCategoryEntity.title}</dd>
          <dt>
            <span id="photoUrl">
              <Translate contentKey="magic12App.subCategory.photoUrl">Photo Url</Translate>
            </span>
          </dt>
          <dd>{subCategoryEntity.photoUrl}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="magic12App.subCategory.photo">Photo</Translate>
            </span>
          </dt>
          <dd>
            {subCategoryEntity.photo ? (
              <div>
                {subCategoryEntity.photoContentType ? (
                  <a onClick={openFile(subCategoryEntity.photoContentType, subCategoryEntity.photo)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {subCategoryEntity.photoContentType}, {byteSize(subCategoryEntity.photo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="voiceUrl">
              <Translate contentKey="magic12App.subCategory.voiceUrl">Voice Url</Translate>
            </span>
          </dt>
          <dd>{subCategoryEntity.voiceUrl}</dd>
          <dt>
            <span id="voiceFile">
              <Translate contentKey="magic12App.subCategory.voiceFile">Voice File</Translate>
            </span>
          </dt>
          <dd>
            {subCategoryEntity.voiceFile ? (
              <div>
                {subCategoryEntity.voiceFileContentType ? (
                  <a onClick={openFile(subCategoryEntity.voiceFileContentType, subCategoryEntity.voiceFile)}>
                    <Translate contentKey="entity.action.open">Open</Translate>&nbsp;
                  </a>
                ) : null}
                <span>
                  {subCategoryEntity.voiceFileContentType}, {byteSize(subCategoryEntity.voiceFile)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="masterDescription">
              <Translate contentKey="magic12App.subCategory.masterDescription">Master Description</Translate>
            </span>
          </dt>
          <dd>{subCategoryEntity.masterDescription}</dd>
          <dt>
            <span id="masterAdvice">
              <Translate contentKey="magic12App.subCategory.masterAdvice">Master Advice</Translate>
            </span>
          </dt>
          <dd>{subCategoryEntity.masterAdvice}</dd>
          <dt>
            <span id="accountType">
              <Translate contentKey="magic12App.subCategory.accountType">Account Type</Translate>
            </span>
          </dt>
          <dd>{subCategoryEntity.accountType}</dd>
          <dt>
            <Translate contentKey="magic12App.subCategory.action">Action</Translate>
          </dt>
          <dd>
            {subCategoryEntity.actions
              ? subCategoryEntity.actions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {subCategoryEntity.actions && i === subCategoryEntity.actions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="magic12App.subCategory.category">Category</Translate>
          </dt>
          <dd>{subCategoryEntity.category ? subCategoryEntity.category.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sub-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sub-category/${subCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SubCategoryDetail;
