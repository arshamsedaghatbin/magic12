package com.papra.magic.service.dto;

import com.papra.magic.domain.enumeration.AccountType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.papra.magic.domain.Category} entity.
 */
public class CategoryDTO implements Serializable {

    private Long id;

    private String title;

    private String photoUrl;

    @Lob
    private byte[] photo;

    private String photoContentType;
    private String voiceUrl;

    @Lob
    private byte[] voiceFile;

    private String voiceFileContentType;
    private String description;

    private String advice;

    private AccountType accountType;

    private Set<ActionDTO> actions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public byte[] getVoiceFile() {
        return voiceFile;
    }

    public void setVoiceFile(byte[] voiceFile) {
        this.voiceFile = voiceFile;
    }

    public String getVoiceFileContentType() {
        return voiceFileContentType;
    }

    public void setVoiceFileContentType(String voiceFileContentType) {
        this.voiceFileContentType = voiceFileContentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Set<ActionDTO> getActions() {
        return actions;
    }

    public void setActions(Set<ActionDTO> actions) {
        this.actions = actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", voiceUrl='" + getVoiceUrl() + "'" +
            ", voiceFile='" + getVoiceFile() + "'" +
            ", description='" + getDescription() + "'" +
            ", advice='" + getAdvice() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", actions=" + getActions() +
            "}";
    }
}
