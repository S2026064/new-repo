package sars.pca.app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sars.pca.app.common.AttachmentType;

@Entity
@Table(name = "attachment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Attachment extends BaseEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "document_size")
    private Double documentSize;

    @Column(name = "attachment_type")
    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;

    @ManyToOne(optional = true, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = SuspiciousCase.class)
    @JoinColumn(name = "susp_case_id", nullable = true)
    private SuspiciousCase suspiciousCase;
}
