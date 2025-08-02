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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "user_writing_answer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserWritingAnswer.findAll", query = "SELECT u FROM UserWritingAnswer u"),
    @NamedQuery(name = "UserWritingAnswer.findById", query = "SELECT u FROM UserWritingAnswer u WHERE u.id = :id"),
    @NamedQuery(name = "UserWritingAnswer.findByTaskScore", query = "SELECT u FROM UserWritingAnswer u WHERE u.taskScore = :taskScore"),
    @NamedQuery(name = "UserWritingAnswer.findByCoherenceScore", query = "SELECT u FROM UserWritingAnswer u WHERE u.coherenceScore = :coherenceScore"),
    @NamedQuery(name = "UserWritingAnswer.findByLexicalScore", query = "SELECT u FROM UserWritingAnswer u WHERE u.lexicalScore = :lexicalScore"),
    @NamedQuery(name = "UserWritingAnswer.findByGrammarScore", query = "SELECT u FROM UserWritingAnswer u WHERE u.grammarScore = :grammarScore"),
    @NamedQuery(name = "UserWritingAnswer.findByOverallScore", query = "SELECT u FROM UserWritingAnswer u WHERE u.overallScore = :overallScore"),
    @NamedQuery(name = "UserWritingAnswer.findByCreatedDate", query = "SELECT u FROM UserWritingAnswer u WHERE u.createdDate = :createdDate"),
    @NamedQuery(name = "UserWritingAnswer.findByUpdatedDate", query = "SELECT u FROM UserWritingAnswer u WHERE u.updatedDate = :updatedDate")})
public class UserWritingAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "content")
    private String content;
    @Lob
    @Column(name = "feedback")
    private String feedback;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "task_score")
    private Float taskScore;
    @Column(name = "coherence_score")
    private Float coherenceScore;
    @Column(name = "lexical_score")
    private Float lexicalScore;
    @Column(name = "grammar_score")
    private Float grammarScore;
    @Column(name = "overall_score")
    private Float overallScore;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Exercise exerciseId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public UserWritingAnswer() {
    }

    public UserWritingAnswer(Integer id) {
        this.id = id;
    }

    public UserWritingAnswer(Integer id, String content, Date createdDate, Date updatedDate) {
        this.id = id;
        this.content = content;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Float getTaskScore() {
        return taskScore;
    }

    public void setTaskScore(Float taskScore) {
        this.taskScore = taskScore;
    }

    public Float getCoherenceScore() {
        return coherenceScore;
    }

    public void setCoherenceScore(Float coherenceScore) {
        this.coherenceScore = coherenceScore;
    }

    public Float getLexicalScore() {
        return lexicalScore;
    }

    public void setLexicalScore(Float lexicalScore) {
        this.lexicalScore = lexicalScore;
    }

    public Float getGrammarScore() {
        return grammarScore;
    }

    public void setGrammarScore(Float grammarScore) {
        this.grammarScore = grammarScore;
    }

    public Float getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(Float overallScore) {
        this.overallScore = overallScore;
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

    public Exercise getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Exercise exerciseId) {
        this.exerciseId = exerciseId;
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
        if (!(object instanceof UserWritingAnswer)) {
            return false;
        }
        UserWritingAnswer other = (UserWritingAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.ndd.pojo.UserWritingAnswer[ id=" + id + " ]";
    }
    
}
