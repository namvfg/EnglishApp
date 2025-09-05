package com.ndd.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "section")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Section.findAll", query = "SELECT s FROM Section s"),
    @NamedQuery(name = "Section.findById", query = "SELECT s FROM Section s WHERE s.id = :id")
})
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Lesson lessonId;

    @JoinColumn(name = "section_type_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SectionType sectionTypeId;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "question", columnDefinition = "JSON")
    private String question;

    @Column(name = "answer", columnDefinition = "JSON")
    private String answer;

    @Column(name = "correct_answer", columnDefinition = "JSON")
    private String correctAnswer;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    public Section() {
    }

    public Section(int id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Lesson getLessonId() {
        return lessonId;
    }

    public void setLessonId(Lesson lessonId) {
        this.lessonId = lessonId;
    }

    public SectionType getSectionTypeId() {
        return sectionTypeId;
    }

    public void setSectionTypeId(SectionType sectionTypeId) {
        this.sectionTypeId = sectionTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
