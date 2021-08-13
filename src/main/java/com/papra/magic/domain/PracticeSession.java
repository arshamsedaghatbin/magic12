package com.papra.magic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A PracticeSession.
 */
@Entity
@Table(name = "practice_session")
public class PracticeSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tiltle")
    private String tiltle;

    @ManyToMany
    @JoinTable(
        name = "rel_practice_session__action",
        joinColumns = @JoinColumn(name = "practice_session_id"),
        inverseJoinColumns = @JoinColumn(name = "action_id")
    )
    @JsonIgnoreProperties(value = { "bookMarks", "categories", "subCategories", "sessions" }, allowSetters = true)
    private Set<Action> actions = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "sessions" }, allowSetters = true)
    private Practice practice;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PracticeSession id(Long id) {
        this.id = id;
        return this;
    }

    public String getTiltle() {
        return this.tiltle;
    }

    public PracticeSession tiltle(String tiltle) {
        this.tiltle = tiltle;
        return this;
    }

    public void setTiltle(String tiltle) {
        this.tiltle = tiltle;
    }

    public Set<Action> getActions() {
        return this.actions;
    }

    public PracticeSession actions(Set<Action> actions) {
        this.setActions(actions);
        return this;
    }

    public PracticeSession addAction(Action action) {
        this.actions.add(action);
        action.getSessions().add(this);
        return this;
    }

    public PracticeSession removeAction(Action action) {
        this.actions.remove(action);
        action.getSessions().remove(this);
        return this;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    public Practice getPractice() {
        return this.practice;
    }

    public PracticeSession practice(Practice practice) {
        this.setPractice(practice);
        return this;
    }

    public void setPractice(Practice practice) {
        this.practice = practice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PracticeSession)) {
            return false;
        }
        return id != null && id.equals(((PracticeSession) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PracticeSession{" +
            "id=" + getId() +
            ", tiltle='" + getTiltle() + "'" +
            "}";
    }
}
