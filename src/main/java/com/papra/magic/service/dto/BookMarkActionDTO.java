package com.papra.magic.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.papra.magic.domain.BookMarkAction} entity.
 */
public class BookMarkActionDTO implements Serializable {

    private Long id;

    private String userDescription;

    private ActionDTO action;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public ActionDTO getAction() {
        return action;
    }

    public void setAction(ActionDTO action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookMarkActionDTO)) {
            return false;
        }

        BookMarkActionDTO bookMarkActionDTO = (BookMarkActionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookMarkActionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookMarkActionDTO{" +
            "id=" + getId() +
            ", userDescription='" + getUserDescription() + "'" +
            ", action=" + getAction() +
            "}";
    }
}
