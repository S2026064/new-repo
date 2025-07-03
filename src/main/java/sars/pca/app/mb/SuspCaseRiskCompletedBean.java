package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.CaseStatus;
import sars.pca.app.domain.SuspiciousCase;
import sars.pca.app.service.SuspiciousCaseServiceLocal;

/**
 *
 * @author S2026987
 */
@ManagedBean
@ViewScoped
public class SuspCaseRiskCompletedBean extends BaseBean<SuspiciousCase> {

    @Autowired
    private SuspiciousCaseServiceLocal suspiciousCaseService;

    @PostConstruct
    public void init() {
        reset().setList(true);
        List<CaseStatus> caseStatuses = new ArrayList<>();
        caseStatuses.add(CaseStatus.ACTIVE_RA_REJECTED);
        caseStatuses.add(CaseStatus.ACTIVE_RA_APPROVAL);
        addCollections(suspiciousCaseService.findByStatusAndCrcsLocationOffice(caseStatuses, getActiveUser().getUser().getLocationOffice()));
    }

    public List<SuspiciousCase> getSuspiciousCases() {
        return this.getCollections();
    }
}
