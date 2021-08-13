package com.papra.magic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.papra.magic.domain.enumeration.AccountType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Practice.
 */
@Entity
@Table(name = "practice")
public class Practice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "photo_url")
    private String photoUrl;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "voice_url")
    private String voiceUrl;

    @Lob
    @Column(name = "voice_file")
    private byte[] voiceFile;

    @Column(name = "voice_file_content_type")
    private String voiceFileContentType;

    @Column(name = "master_description")
    private String masterDescription;

    @Column(name = "master_advice")
    private String masterAdvice;

    @Column(name = "brief_master_advice")
    private String briefMasterAdvice;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @OneToMany(mappedBy = "practice")
    @JsonIgnoreProperties(value = { "actions", "practice" }, allowSetters = true)
    private Set<PracticeSession> sessions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Practice id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Practice title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public Practice photoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Practice photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Practice photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getVoiceUrl() {
        return this.voiceUrl;
    }

    public Practice voiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
        return this;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public byte[] getVoiceFile() {
        return this.voiceFile;
    }

    public Practice voiceFile(byte[] voiceFile) {
        this.voiceFile = voiceFile;
        return this;
    }

    public void setVoiceFile(byte[] voiceFile) {
        this.voiceFile = voiceFile;
    }

    public String getVoiceFileContentType() {
        return this.voiceFileContentType;
    }

    public Practice voiceFileContentType(String voiceFileContentType) {
        this.voiceFileContentType = voiceFileContentType;
        return this;
    }

    public void setVoiceFileContentType(String voiceFileContentType) {
        this.voiceFileContentType = voiceFileContentType;
    }

    public String getMasterDescription() {
        return this.masterDescription;
    }

    public Practice masterDescription(String masterDescription) {
        this.masterDescription = masterDescription;
        return this;
    }

    public void setMasterDescription(String masterDescription) {
        this.masterDescription = masterDescription;
    }

    public String getMasterAdvice() {
        return this.masterAdvice;
    }

    public Practice masterAdvice(String masterAdvice) {
        this.masterAdvice = masterAdvice;
        return this;
    }

    public void setMasterAdvice(String masterAdvice) {
        this.masterAdvice = masterAdvice;
    }

    public String getBriefMasterAdvice() {
        return this.briefMasterAdvice;
    }

    public Practice briefMasterAdvice(String briefMasterAdvice) {
        this.briefMasterAdvice = briefMasterAdvice;
        return this;
    }

    public void setBriefMasterAdvice(String briefMasterAdvice) {
        this.briefMasterAdvice = briefMasterAdvice;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public Practice accountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Set<PracticeSession> getSessions() {
        return this.sessions;
    }

    public Practice sessions(Set<PracticeSession> practiceSessions) {
        this.setSessions(practiceSessions);
        return this;
    }

    public Practice addSession(PracticeSession practiceSession) {
        this.sessions.add(practiceSession);
        practiceSession.setPractice(this);
        return this;
    }

    public Practice removeSession(PracticeSession practiceSession) {
        this.sessions.remove(practiceSession);
        practiceSession.setPractice(null);
        return this;
    }

    public void setSessions(Set<PracticeSession> practiceSessions) {
        if (this.sessions != null) {
            this.sessions.forEach(i -> i.setPractice(null));
        }
        if (practiceSessions != null) {
            practiceSessions.forEach(i -> i.setPractice(this));
        }
        this.sessions = practiceSessions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Practice)) {
            return false;
        }
        return id != null && id.equals(((Practice) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Practice{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", voiceUrl='" + getVoiceUrl() + "'" +
            ", voiceFile='" + getVoiceFile() + "'" +
            ", voiceFileContentType='" + getVoiceFileContentType() + "'" +
            ", masterDescription='" + getMasterDescription() + "'" +
            ", masterAdvice='" + getMasterAdvice() + "'" +
            ", briefMasterAdvice='" + getBriefMasterAdvice() + "'" +
            ", accountType='" + getAccountType() + "'" +
            "}";
    }
}
