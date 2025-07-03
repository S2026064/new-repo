package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2026015
 */
@Entity
@Table(name = "auditor")
@Getter
@Setter
public class Auditor extends BaseEntity{
    @Column(name = "name")
    private String name;
}
