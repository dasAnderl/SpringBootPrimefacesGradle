package com.anderl.hibernate.ext;


import org.hibernate.criterion.Criterion;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created by ga2unte on 12/2/13.
 */
public class HibernateCriterionWrapper<T> implements ColumnControl<T> {

    private T value = null;
    private HibernateCriterionEnum hibernateCriterionEnum;
    private boolean visible;
    private String labelMsgKey;
    private String id;
    private CriterionFieldJoinMappings.CriterionMapper criterionMapper;

    private String getId(CriterionFieldJoinMappings.CriterionMapper criterionMapper) {
        return criterionMapper.getFieldPath().replace(".", "");
    }

    public HibernateCriterionWrapper(CriterionFieldJoinMappings.CriterionMapper criterionMapper, HibernateCriterionEnum hibernateCriterionEnum, boolean visible, String labelMsgKey) {
        this.criterionMapper = criterionMapper;
        this.hibernateCriterionEnum = hibernateCriterionEnum;
        this.visible = visible;
        this.labelMsgKey = labelMsgKey;
        id = getId(criterionMapper);
    }

    public HibernateCriterionWrapper(CriterionFieldJoinMappings.CriterionMapper criterionMapper, HibernateCriterionEnum hibernateCriterionEnum, T value, boolean visible, String labelMsgKey) {
        this(criterionMapper, hibernateCriterionEnum, visible, labelMsgKey);
        this.value = value;
    }

    public boolean isValid() {

        if (StringUtils.isEmpty(criterionMapper.getCriterionPath())) return false;
        if (hibernateCriterionEnum.isNullValueAllowed() && value == null) return true;
        if (StringUtils.isEmpty(value)) return false;
        //used for multicriterias e.g. aopId and Id -> exclude invalid criteria because an aopid is not valid as type for id
        if (isIdProperty()) {
            try {
                value = (T) new Long(value.toString());
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        if (hibernateCriterionEnum.isMultiValue()) {
            if (value instanceof Collection) {
                return !CollectionUtils.isEmpty((Collection) value);
            }
            if (value.getClass().isArray()) {
                return ((Object[]) value).length > 0;
            }
        }
        return true;
    }

    private boolean isIdProperty() {
        if (criterionMapper.getCriterionPath().equals("id")
                || criterionMapper.getCriterionPath().endsWith(".id")
                || criterionMapper.getCriterionPath().equals("changedEntityId")
                || criterionMapper.getCriterionPath().equals("clientId")
                || criterionMapper.getCriterionPath().equals("assessmentId")
                ) {
            return true;
        }
        return false;
    }

    public Criterion getCriterion() {
        //TODO ga2unte: remove this check, just provide AbstractStaticEntity.type here
        if (value != null && value.getClass().getSuperclass() != null && value.getClass().getSuperclass().getSimpleName().contains("AbstractStaticEntity")) {
            final Field typeField = ReflectionUtils.findField(value.getClass(), "type");
            return hibernateCriterionEnum.get(criterionMapper.getCriterionPath(), ReflectionUtils.getField(typeField, "type"));
        }
        return hibernateCriterionEnum.get(criterionMapper.getCriterionPath(), getValue());
    }

    @Override
    public String getSortingProperty() {
        return criterionMapper.getCriterionPath();
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public void setVisible(boolean active) {
        this.visible = active;
    }

    @Override
    public String getLabelMsgKey() {
        return labelMsgKey;
    }

    @Override
    public String getId() {

        return id;
    }

    public CriterionFieldJoinMappings.CriterionMapper getCriterionMapper() {
        return criterionMapper;
    }
}
