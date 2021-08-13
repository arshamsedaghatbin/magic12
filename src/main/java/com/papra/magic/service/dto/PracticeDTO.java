package com.papra.magic.service.dto;

import com.papra.magic.domain.enumeration.AccountType;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link com.papra.magic.domain.Practice} entity.
 */
public class PracticeDTO implements Serializable {

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
    private String masterDescription;

    private String masterAdvice;

    private String briefMasterAdvice;

    private AccountType accountType;

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

    public String getMasterDescription() {
        return masterDescription;
    }

    public void setMasterDescription(String masterDescription) {
        this.masterDescription = masterDescription;
    }

    public String getMasterAdvice() {
        return masterAdvice;
    }

    public void setMasterAdvice(String masterAdvice) {
        this.masterAdvice = masterAdvice;
    }

    public String getBriefMasterAdvice() {
        return briefMasterAdvice;
    }

    public void setBriefMasterAdvice(String briefMasterAdvice) {
        this.briefMasterAdvice = briefMasterAdvice;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PracticeDTO)) {
            return false;
        }

        PracticeDTO practiceDTO = (PracticeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, practiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PracticeDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", voiceUrl='" + getVoiceUrl() + "'" +
            ", voiceFile='" + getVoiceFile() + "'" +
            ", masterDescription='" + getMasterDescription() + "'" +
            ", masterAdvice='" + getMasterAdvice() + "'" +
            ", briefMasterAdvice='" + getBriefMasterAdvice() + "'" +
            ", accountType='" + getAccountType() + "'" +
            "}";
    }
}
