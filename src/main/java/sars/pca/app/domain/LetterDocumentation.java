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
@Table(name = "letter_documentation")
@Getter
@Setter
public class LetterDocumentation extends BaseEntity {
    @Column(name = "customs_declaration")
    private String customsDeclaration;
}
