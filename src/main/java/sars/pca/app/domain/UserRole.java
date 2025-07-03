/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sars.pca.app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_role")
@Getter
@Setter
public class UserRole extends BaseEntity {

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "report_sett_id")
    private ReportSetting reportSetting;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_sett_id")
    private AdministrationSetting AdminSetting;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "vddlsarcase_sett_id")
    private VddlOrSarCaseSetting vddlOrSarCaseSetting;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "risk_sett_id")
    private RiskManagementSettting riskManagementSettting;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "audit_sett_id")
    private AuditingSettting auditingSetting;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "debt_mngmnt_sett_id")
    private DebtManagementSetting debtManagementSetting;

    public UserRole(String description) {
        this.description = description;
    }
    public UserRole() {
    }
}
