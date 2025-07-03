package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "office")
@Getter
@Setter
public class Office extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    public Office(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Office() {
    }
}
