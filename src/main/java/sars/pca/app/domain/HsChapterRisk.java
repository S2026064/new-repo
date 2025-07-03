package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author S2024726
 */
@Entity
@Table(name = "hs_chapter_risk")
@Getter
@Setter
public class HsChapterRisk extends BaseEntity {
    @Column(name = "description", length = 1000)
    private String description;
}
