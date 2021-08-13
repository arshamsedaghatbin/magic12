package com.papra.magic.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.papra.magic.domain.PracticeSession} entity.
 */
public class PracticeSessionDTO implements Serializable {

    private Long id;

    private String tiltle;

    private Set<ActionDTO> actions = new HashSet<>();

    private PracticeDTO practice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTiltle() {
        return tiltle;
    }

    public void setTiltle(String tiltle) {
        this.tiltle = tiltle;
    }

    public Set<ActionDTO> getActions() {
        return actions;
    }

    public void setActions(Set<ActionDTO> actions) {
        this.actions = actions;
    }

    public PracticeDTO getPractice() {
        return practice;
    }

    public void setPractice(PracticeDTO practice) {
        this.practice = practice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PracticeSessionDTO)) {
            return false;
        }

        PracticeSessionDTO practiceSessionDTO = (PracticeSessionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, practiceSessionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PracticeSessionDTO{" +
            "id=" + getId() +
            ", tiltle='" + getTiltle() + "'" +
            ", actions=" + getActions() +
            ", practice=" + getPractice() +
            "}";
    }
}
