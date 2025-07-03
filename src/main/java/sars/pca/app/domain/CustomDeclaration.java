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
@Table(name = "custom_declaration")
@Getter
@Setter
public class CustomDeclaration extends BaseEntity{
    @Column(name = "description")
    private String description;
}
