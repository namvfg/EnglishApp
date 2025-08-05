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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "vocabulary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vocabulary.findAll", query = "SELECT v FROM Vocabulary v"),
    @NamedQuery(name = "Vocabulary.findById", query = "SELECT v FROM Vocabulary v WHERE v.id = :id"),
    @NamedQuery(name = "Vocabulary.findByWord", query = "SELECT v FROM Vocabulary v WHERE v.word = :word"),
    @NamedQuery(name = "Vocabulary.findByMeaning", query = "SELECT v FROM Vocabulary v WHERE v.meaning = :meaning"),
    @NamedQuery(name = "Vocabulary.findByCreatedDate", query = "SELECT v FROM Vocabulary v WHERE v.createdDate = :createdDate"),
    @NamedQuery(name = "Vocabulary.findByUpdatedDate", query = "SELECT v FROM Vocabulary v WHERE v.updatedDate = :updatedDate")})
public class Vocabulary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "word")
    private String word;
    @Size(max = 200)
    @Column(name = "meaning")
    private String meaning;
    @Lob
    @Size(max = 65535)
    @Column(name = "example")
    private String example;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vocabId")
    private Set<UserVocabulary> userVocabularySet;

    public Vocabulary() {
    }

    public Vocabulary(Integer id) {
        this.id = id;
    }

    public Vocabulary(Integer id, Date createdDate, Date updatedDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
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
    public Set<UserVocabulary> getUserVocabularySet() {
        return userVocabularySet;
    }

    public void setUserVocabularySet(Set<UserVocabulary> userVocabularySet) {
        this.userVocabularySet = userVocabularySet;
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
        if (!(object instanceof Vocabulary)) {
            return false;
        }
        Vocabulary other = (Vocabulary) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ndd.pojo.Vocabulary[ id=" + id + " ]";
    }
    
}
