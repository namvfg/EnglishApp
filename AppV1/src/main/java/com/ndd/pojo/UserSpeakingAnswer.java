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
import javax.persistence.Lob;
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
@Table(name = "user_speaking_answer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserSpeakingAnswer.findAll", query = "SELECT u FROM UserSpeakingAnswer u"),
    @NamedQuery(name = "UserSpeakingAnswer.findById", query = "SELECT u FROM UserSpeakingAnswer u WHERE u.id = :id"),
    @NamedQuery(name = "UserSpeakingAnswer.findByAudioUrl", query = "SELECT u FROM UserSpeakingAnswer u WHERE u.audioUrl = :audioUrl"),
    @NamedQuery(name = "UserSpeakingAnswer.findByPronunciationScore", query = "SELECT u FROM UserSpeakingAnswer u WHERE u.pronunciationScore = :pronunciationScore"),
    @NamedQuery(name = "UserSpeakingAnswer.findByFluencyScore", query = "SELECT u FROM UserSpeakingAnswer u WHERE u.fluencyScore = :fluencyScore"),
    @NamedQuery(name = "UserSpeakingAnswer.findByCreatedDate", query = "SELECT u FROM UserSpeakingAnswer u WHERE u.createdDate = :createdDate"),
    @NamedQuery(name = "UserSpeakingAnswer.findByUpdatedDate", query = "SELECT u FROM UserSpeakingAnswer u WHERE u.updatedDate = :updatedDate")})
public class UserSpeakingAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "audio_url")
    private String audioUrl;
    @Lob
    @Size(max = 65535)
    @Column(name = "transcript")
    private String transcript;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pronunciation_score")
    private Float pronunciationScore;
    @Column(name = "fluency_score")
    private Float fluencyScore;
    @Lob
    @Size(max = 65535)
    @Column(name = "feedback")
    private String feedback;
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
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Lesson lessonId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public UserSpeakingAnswer() {
    }

    public UserSpeakingAnswer(Integer id) {
        this.id = id;
    }

    public UserSpeakingAnswer(Integer id, String audioUrl, Date createdDate, Date updatedDate) {
        this.id = id;
        this.audioUrl = audioUrl;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public Float getPronunciationScore() {
        return pronunciationScore;
    }

    public void setPronunciationScore(Float pronunciationScore) {
        this.pronunciationScore = pronunciationScore;
    }

    public Float getFluencyScore() {
        return fluencyScore;
    }

    public void setFluencyScore(Float fluencyScore) {
        this.fluencyScore = fluencyScore;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
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

    public Lesson getLessonId() {
        return lessonId;
    }

    public void setLessonId(Lesson lessonId) {
        this.lessonId = lessonId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof UserSpeakingAnswer)) {
            return false;
        }
        UserSpeakingAnswer other = (UserSpeakingAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ndd.pojo.UserSpeakingAnswer[ id=" + id + " ]";
    }
    
}
