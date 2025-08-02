/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByFirstName", query = "SELECT u FROM User u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "User.findByLastName", query = "SELECT u FROM User u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByRole", query = "SELECT u FROM User u WHERE u.role = :role"),
    @NamedQuery(name = "User.findByAvatarUrl", query = "SELECT u FROM User u WHERE u.avatarUrl = :avatarUrl"),
    @NamedQuery(name = "User.findByCreatedDate", query = "SELECT u FROM User u WHERE u.createdDate = :createdDate"),
    @NamedQuery(name = "User.findByUpdatedDate", query = "SELECT u FROM User u WHERE u.updatedDate = :updatedDate")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "first_name")
    private String firstName;
    @Basic(optional = false)
    @Column(name = "last_name")
    private String lastName;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "role")
    private String role;
    @Basic(optional = false)
    @Column(name = "avatar_url")
    private String avatarUrl;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<UserWritingAnswer> userWritingAnswerSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<ProgressTracker> progressTrackerSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<UserVocabulary> userVocabularySet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<UserSpeakingAnswer> userSpeakingAnswerSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<PracticeSession> practiceSessionSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Set<StudyPlan> studyPlanSet;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String firstName, String lastName, String email, String password, String role, String avatarUrl, Date createdDate, Date updatedDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.avatarUrl = avatarUrl;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @XmlTransient
    public Set<UserWritingAnswer> getUserWritingAnswerSet() {
        return userWritingAnswerSet;
    }

    public void setUserWritingAnswerSet(Set<UserWritingAnswer> userWritingAnswerSet) {
        this.userWritingAnswerSet = userWritingAnswerSet;
    }

    @XmlTransient
    public Set<ProgressTracker> getProgressTrackerSet() {
        return progressTrackerSet;
    }

    public void setProgressTrackerSet(Set<ProgressTracker> progressTrackerSet) {
        this.progressTrackerSet = progressTrackerSet;
    }

    @XmlTransient
    public Set<UserVocabulary> getUserVocabularySet() {
        return userVocabularySet;
    }

    public void setUserVocabularySet(Set<UserVocabulary> userVocabularySet) {
        this.userVocabularySet = userVocabularySet;
    }

    @XmlTransient
    public Set<UserSpeakingAnswer> getUserSpeakingAnswerSet() {
        return userSpeakingAnswerSet;
    }

    public void setUserSpeakingAnswerSet(Set<UserSpeakingAnswer> userSpeakingAnswerSet) {
        this.userSpeakingAnswerSet = userSpeakingAnswerSet;
    }

    @XmlTransient
    public Set<PracticeSession> getPracticeSessionSet() {
        return practiceSessionSet;
    }

    public void setPracticeSessionSet(Set<PracticeSession> practiceSessionSet) {
        this.practiceSessionSet = practiceSessionSet;
    }

    @XmlTransient
    public Set<StudyPlan> getStudyPlanSet() {
        return studyPlanSet;
    }

    public void setStudyPlanSet(Set<StudyPlan> studyPlanSet) {
        this.studyPlanSet = studyPlanSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ndd.pojo.User[ id=" + id + " ]";
    }
    
}
