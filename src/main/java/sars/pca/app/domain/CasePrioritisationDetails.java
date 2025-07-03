package sars.pca.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "case_prio_details")
@Getter
@Setter
public class CasePrioritisationDetails extends BaseEntity {

    @Column(name = "total_custom_declared_import")
    private Double totalCustomDeclaredImport;

    @Column(name = "total_custom_declared_export")
    private Double totalCustomDeclaredExport;

    @Column(name = "total_volume_transaction_import")
    private Integer totalVolumeTransactionImport;

    @Column(name = "total_volume_transaction_export")
    private Integer totalVolumetransactionExport;

    @Column(name = "number_of_risk_areas")
    private Integer numberOfRiskAreas;

    public CasePrioritisationDetails() {
        this.totalCustomDeclaredImport = 0.00;
        this.totalCustomDeclaredExport = 0.00;
        this.totalVolumeTransactionImport = 0;
        this.totalVolumetransactionExport = 0;
        this.numberOfRiskAreas = 0;
    }
}
