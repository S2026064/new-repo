package sars.pca.app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "comment")
@Getter
@Setter
public class Comment extends BaseEntity {

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = true, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "suspiciousCase_id", nullable = true)
    private SuspiciousCase suspiciousCase;
}
