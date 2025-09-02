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

@Entity
@Table(name = "user_speaking_answer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserSpeakingAnswer.findAll", query = "SELECT u FROM UserSpeakingAnswer u"),
    @NamedQuery(name = "UserSpeakingAnswer.findById", query = "SELECT u FROM UserSpeakingAnswer u WHERE u.id = :id")
})
public class UserSpeakingAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Lesson lessonId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "audio_url")
    private String audioUrl;

    @Lob
    @Column(name = "transcript")
    private String transcript;

    @Column(name = "pronunciation_score")
    private Float pronunciationScore;

    @Column(name = "fluency_score")
    private Float fluencyScore;

    @Column(name = "coherence_score")
    private Float coherenceScore;

    @Column(name = "lexical_resource_score")
    private Float lexicalResourceScore;

    @Column(name = "grammar_score")
    private Float grammarScore;
    
    @Column(name = "overall_score")
    private Float overallScore;

    @Lob
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

    public UserSpeakingAnswer() {
    }

    public UserSpeakingAnswer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Lesson getLessonId() {
        return lessonId;
    }

    public void setLessonId(Lesson lessonId) {
        this.lessonId = lessonId;
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

    public Float getCoherenceScore() {
        return coherenceScore;
    }

    public void setCoherenceScore(Float coherenceScore) {
        this.coherenceScore = coherenceScore;
    }

    public Float getLexicalResourceScore() {
        return lexicalResourceScore;
    }

    public void setLexicalResourceScore(Float lexicalResourceScore) {
        this.lexicalResourceScore = lexicalResourceScore;
    }

    public Float getGrammarScore() {
        return grammarScore;
    }

    public void setGrammarScore(Float grammarScore) {
        this.grammarScore = grammarScore;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserSpeakingAnswer)) {
            return false;
        }
        UserSpeakingAnswer other = (UserSpeakingAnswer) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "com.ndd.pojo.UserSpeakingAnswer[ id=" + id + " ]";
    }

    /**
     * @return the overallScore
     */
    public Float getOverallScore() {
        return overallScore;
    }

    /**
     * @param overallScore the overallScore to set
     */
    public void setOverallScore(Float overallScore) {
        this.overallScore = overallScore;
    }
}