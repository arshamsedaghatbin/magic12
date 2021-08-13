package com.papra.magic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.papra.magic.domain.enumeration.AccountType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
public class Action implements Serializable {

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

    @Column(name = "code", unique = true)
    private String code;

    @Lob
    @Column(name = "video")
    private byte[] video;

    @Column(name = "video_content_type")
    private String videoContentType;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "master_description")
    private String masterDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @OneToMany(mappedBy = "action")
    @JsonIgnoreProperties(value = { "action" }, allowSetters = true)
    private Set<BookMarkAction> bookMarks = new HashSet<>();

    @ManyToMany(mappedBy = "actions")
    @JsonIgnoreProperties(value = { "subcategories", "actions" }, allowSetters = true)
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(mappedBy = "actions")
    @JsonIgnoreProperties(value = { "actions", "category" }, allowSetters = true)
    private Set<SubCategory> subCategories = new HashSet<>();

    @ManyToMany(mappedBy = "actions")
    @JsonIgnoreProperties(value = { "actions", "practice" }, allowSetters = true)
    private Set<PracticeSession> sessions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Action title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public Action photoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Action photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Action photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getCode() {
        return this.code;
    }

    public Action code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public byte[] getVideo() {
        return this.video;
    }

    public Action video(byte[] video) {
        this.video = video;
        return this;
    }

    public void setVideo(byte[] video) {
        this.video = video;
    }

    public String getVideoContentType() {
        return this.videoContentType;
    }

    public Action videoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
        return this;
    }

    public void setVideoContentType(String videoContentType) {
        this.videoContentType = videoContentType;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public Action videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getMasterDescription() {
        return this.masterDescription;
    }

    public Action masterDescription(String masterDescription) {
        this.masterDescription = masterDescription;
        return this;
    }

    public void setMasterDescription(String masterDescription) {
        this.masterDescription = masterDescription;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public Action accountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Set<BookMarkAction> getBookMarks() {
        return this.bookMarks;
    }

    public Action bookMarks(Set<BookMarkAction> bookMarkActions) {
        this.setBookMarks(bookMarkActions);
        return this;
    }

    public Action addBookMark(BookMarkAction bookMarkAction) {
        this.bookMarks.add(bookMarkAction);
        bookMarkAction.setAction(this);
        return this;
    }

    public Action removeBookMark(BookMarkAction bookMarkAction) {
        this.bookMarks.remove(bookMarkAction);
        bookMarkAction.setAction(null);
        return this;
    }

    public void setBookMarks(Set<BookMarkAction> bookMarkActions) {
        if (this.bookMarks != null) {
            this.bookMarks.forEach(i -> i.setAction(null));
        }
        if (bookMarkActions != null) {
            bookMarkActions.forEach(i -> i.setAction(this));
        }
        this.bookMarks = bookMarkActions;
    }

    public Set<Category> getCategories() {
        return this.categories;
    }

    public Action categories(Set<Category> categories) {
        this.setCategories(categories);
        return this;
    }

    public Action addCategory(Category category) {
        this.categories.add(category);
        category.getActions().add(this);
        return this;
    }

    public Action removeCategory(Category category) {
        this.categories.remove(category);
        category.getActions().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        if (this.categories != null) {
            this.categories.forEach(i -> i.removeAction(this));
        }
        if (categories != null) {
            categories.forEach(i -> i.addAction(this));
        }
        this.categories = categories;
    }

    public Set<SubCategory> getSubCategories() {
        return this.subCategories;
    }

    public Action subCategories(Set<SubCategory> subCategories) {
        this.setSubCategories(subCategories);
        return this;
    }

    public Action addSubCategory(SubCategory subCategory) {
        this.subCategories.add(subCategory);
        subCategory.getActions().add(this);
        return this;
    }

    public Action removeSubCategory(SubCategory subCategory) {
        this.subCategories.remove(subCategory);
        subCategory.getActions().remove(this);
        return this;
    }

    public void setSubCategories(Set<SubCategory> subCategories) {
        if (this.subCategories != null) {
            this.subCategories.forEach(i -> i.removeAction(this));
        }
        if (subCategories != null) {
            subCategories.forEach(i -> i.addAction(this));
        }
        this.subCategories = subCategories;
    }

    public Set<PracticeSession> getSessions() {
        return this.sessions;
    }

    public Action sessions(Set<PracticeSession> practiceSessions) {
        this.setSessions(practiceSessions);
        return this;
    }

    public Action addSession(PracticeSession practiceSession) {
        this.sessions.add(practiceSession);
        practiceSession.getActions().add(this);
        return this;
    }

    public Action removeSession(PracticeSession practiceSession) {
        this.sessions.remove(practiceSession);
        practiceSession.getActions().remove(this);
        return this;
    }

    public void setSessions(Set<PracticeSession> practiceSessions) {
        if (this.sessions != null) {
            this.sessions.forEach(i -> i.removeAction(this));
        }
        if (practiceSessions != null) {
            practiceSessions.forEach(i -> i.addAction(this));
        }
        this.sessions = practiceSessions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Action)) {
            return false;
        }
        return id != null && id.equals(((Action) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", code='" + getCode() + "'" +
            ", video='" + getVideo() + "'" +
            ", videoContentType='" + getVideoContentType() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", masterDescription='" + getMasterDescription() + "'" +
            ", accountType='" + getAccountType() + "'" +
            "}";
    }
}
