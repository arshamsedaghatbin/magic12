package com.papra.magic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A BookMarkAction.
 */
@Entity
@Table(name = "book_mark_action")
public class BookMarkAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_description")
    private String userDescription;

    @ManyToOne
    @JsonIgnoreProperties(value = { "bookMarks", "categories", "subCategories", "sessions" }, allowSetters = true)
    private Action action;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookMarkAction id(Long id) {
        this.id = id;
        return this;
    }

    public String getUserDescription() {
        return this.userDescription;
    }

    public BookMarkAction userDescription(String userDescription) {
        this.userDescription = userDescription;
        return this;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public Action getAction() {
        return this.action;
    }

    public BookMarkAction action(Action action) {
        this.setAction(action);
        return this;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookMarkAction)) {
            return false;
        }
        return id != null && id.equals(((BookMarkAction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookMarkAction{" +
            "id=" + getId() +
            ", userDescription='" + getUserDescription() + "'" +
            "}";
    }
}
