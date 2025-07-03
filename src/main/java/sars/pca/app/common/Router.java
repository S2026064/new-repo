package sars.pca.app.common;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Router implements Serializable {

    private boolean administrator;
    private boolean vddlOrSarCases;
    private boolean riskAssessment;
    private boolean auditing;
    private boolean debtManagement;
    private boolean reports;

    public Router reset() {
        this.setAdministrator(false);
        this.setAuditing(false);
        this.setDebtManagement(false);
        this.setReports(false);
        this.setVddlOrSarCases(false);
        this.setRiskAssessment(false);
        return this;
    }
}
