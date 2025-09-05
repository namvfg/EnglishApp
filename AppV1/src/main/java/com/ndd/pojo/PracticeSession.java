package com.ndd.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "practice_session")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PracticeSession.findAll", query = "SELECT p FROM PracticeSession p"),
    @NamedQuery(name = "PracticeSession.findById", query = "SELECT p FROM PracticeSession p WHERE p.id = :id")
})
public class PracticeSession implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Lesson lessonId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Column(name = "start_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startAt;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "score")
    private Float score;

    @Column(name = "feedback", columnDefinition = "TEXT")
    private String feedback;

    public PracticeSession() {
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

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
