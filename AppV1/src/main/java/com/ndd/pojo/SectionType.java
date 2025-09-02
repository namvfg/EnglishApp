package com.ndd.pojo;

import com.ndd.enums.SaveType;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "section_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SectionType.findAll", query = "SELECT s FROM SectionType s"),
    @NamedQuery(name = "SectionType.findById", query = "SELECT s FROM SectionType s WHERE s.id = :id"),
    @NamedQuery(name = "SectionType.findByName", query = "SELECT s FROM SectionType s WHERE s.name = :name")
})
public class SectionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "save_type", nullable = false)
    private SaveType saveType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sectionTypeId")
    private Set<Section> sectionSet;

    public SectionType() {
    }

    public SectionType(Integer id) {
        this.id = id;
    }

    public SectionType(Integer id, String name, SaveType saveType) {
        this.id = id;
        this.name = name;
        this.saveType = saveType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SaveType getSaveType() {
        return saveType;
    }

    public void setSaveType(SaveType saveType) {
        this.saveType = saveType;
    }

    @XmlTransient
    public Set<Section> getSectionSet() {
        return sectionSet;
    }

    public void setSectionSet(Set<Section> sectionSet) {
        this.sectionSet = sectionSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SectionType)) {
            return false;
        }
        SectionType other = (SectionType) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.ndd.pojo.SectionType[ id=" + id + " ]";
    }
}
