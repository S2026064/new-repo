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
@Table(name = "document_type")
@Getter
@Setter
public class RelevantMaterial extends BaseEntity {
    @Column(name = "description")
    private String description;
}
