/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ndd.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "user_vocabulary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserVocabulary.findAll", query = "SELECT u FROM UserVocabulary u"),
    @NamedQuery(name = "UserVocabulary.findById", query = "SELECT u FROM UserVocabulary u WHERE u.id = :id"),
    @NamedQuery(name = "UserVocabulary.findByCreatedDate", query = "SELECT u FROM UserVocabulary u WHERE u.createdDate = :createdDate"),
    @NamedQuery(name = "UserVocabulary.findByUpdatedDate", query = "SELECT u FROM UserVocabulary u WHERE u.updatedDate = :updatedDate"),
    @NamedQuery(name = "UserVocabulary.findByLastReview", query = "SELECT u FROM UserVocabulary u WHERE u.lastReview = :lastReview"),
    @NamedQuery(name = "UserVocabulary.findByNote", query = "SELECT u FROM UserVocabulary u WHERE u.note = :note")})
public class UserVocabulary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_review")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastReview;
    @Size(max = 255)
    @Column(name = "note")
    private String note;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;
    @JoinColumn(name = "vocab_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vocabulary vocabId;

    public UserVocabulary() {
    }

    public UserVocabulary(Integer id) {
        this.id = id;
    }

    public UserVocabulary(Integer id, Date createdDate, Date updatedDate, Date lastReview) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.lastReview = lastReview;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getLastReview() {
        return lastReview;
    }

    public void setLastReview(Date lastReview) {
        this.lastReview = lastReview;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Vocabulary getVocabId() {
        return vocabId;
    }

    public void setVocabId(Vocabulary vocabId) {
        this.vocabId = vocabId;
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
        if (!(object instanceof UserVocabulary)) {
            return false;
        }
        UserVocabulary other = (UserVocabulary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ndd.pojo.UserVocabulary[ id=" + id + " ]";
    }
    
}
