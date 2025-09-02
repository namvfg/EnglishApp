package com.ndd.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "user_answer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserAnswer.findAll", query = "SELECT u FROM UserAnswer u"),
    @NamedQuery(name = "UserAnswer.findById", query = "SELECT u FROM UserAnswer u WHERE u.id = :id")
})
public class UserAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "section_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Section sectionId;

    @JoinColumn(name = "session_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PracticeSession practiceSessionId;

    @Column(name = "answer", columnDefinition = "JSON", nullable = false)
    private String answer;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "score")
    private Float score;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public UserAnswer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Section getExerciseId() {
        return sectionId;
    }

    public void setExerciseId(Section exerciseId) {
        this.sectionId = exerciseId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the practiceSessionId
     */
    public PracticeSession getPracticeSessionId() {
        return practiceSessionId;
    }

    /**
     * @param practiceSessionId the practiceSessionId to set
     */
    public void setPracticeSessionId(PracticeSession practiceSessionId) {
        this.practiceSessionId = practiceSessionId;
    }
}
