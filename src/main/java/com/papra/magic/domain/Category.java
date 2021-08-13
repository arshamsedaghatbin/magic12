package com.papra.magic.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.papra.magic.domain.enumeration.AccountType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

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

    @Column(name = "description")
    private String description;

    @Column(name = "advice")
    private String advice;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @OneToMany(mappedBy = "category")
    @JsonIgnoreProperties(value = { "actions", "category" }, allowSetters = true)
    private Set<SubCategory> subcategories = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_category__action",
        joinColumns = @JoinColumn(name = "category_id"),
        inverseJoinColumns = @JoinColumn(name = "action_id")
    )
    @JsonIgnoreProperties(value = { "bookMarks", "categories", "subCategories", "sessions" }, allowSetters = true)
    private Set<Action> actions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Category title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public Category photoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Category photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Category photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getVoiceUrl() {
        return this.voiceUrl;
    }

    public Category voiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
        return this;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public byte[] getVoiceFile() {
        return this.voiceFile;
    }

    public Category voiceFile(byte[] voiceFile) {
        this.voiceFile = voiceFile;
        return this;
    }

    public void setVoiceFile(byte[] voiceFile) {
        this.voiceFile = voiceFile;
    }

    public String getVoiceFileContentType() {
        return this.voiceFileContentType;
    }

    public Category voiceFileContentType(String voiceFileContentType) {
        this.voiceFileContentType = voiceFileContentType;
        return this;
    }

    public void setVoiceFileContentType(String voiceFileContentType) {
        this.voiceFileContentType = voiceFileContentType;
    }

    public String getDescription() {
        return this.description;
    }

    public Category description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdvice() {
        return this.advice;
    }

    public Category advice(String advice) {
        this.advice = advice;
        return this;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public Category accountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Set<SubCategory> getSubcategories() {
        return this.subcategories;
    }

    public Category subcategories(Set<SubCategory> subCategories) {
        this.setSubcategories(subCategories);
        return this;
    }

    public Category addSubcategory(SubCategory subCategory) {
        this.subcategories.add(subCategory);
        subCategory.setCategory(this);
        return this;
    }

    public Category removeSubcategory(SubCategory subCategory) {
        this.subcategories.remove(subCategory);
        subCategory.setCategory(null);
        return this;
    }

    public void setSubcategories(Set<SubCategory> subCategories) {
        if (this.subcategories != null) {
            this.subcategories.forEach(i -> i.setCategory(null));
        }
        if (subCategories != null) {
            subCategories.forEach(i -> i.setCategory(this));
        }
        this.subcategories = subCategories;
    }

    public Set<Action> getActions() {
        return this.actions;
    }

    public Category actions(Set<Action> actions) {
        this.setActions(actions);
        return this;
    }

    public Category addAction(Action action) {
        this.actions.add(action);
        action.getCategories().add(this);
        return this;
    }

    public Category removeAction(Action action) {
        this.actions.remove(action);
        action.getCategories().remove(this);
        return this;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", voiceUrl='" + getVoiceUrl() + "'" +
            ", voiceFile='" + getVoiceFile() + "'" +
            ", voiceFileContentType='" + getVoiceFileContentType() + "'" +
            ", description='" + getDescription() + "'" +
            ", advice='" + getAdvice() + "'" +
            ", accountType='" + getAccountType() + "'" +
            "}";
    }
}
