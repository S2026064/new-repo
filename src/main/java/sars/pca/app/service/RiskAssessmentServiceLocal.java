package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.RiskAssessment;

/**
 *
 * @author S2026080
 */
public interface RiskAssessmentServiceLocal {

    RiskAssessment save(RiskAssessment riskAssessment);

    RiskAssessment findById(Long id);

    RiskAssessment update(RiskAssessment riskAssessment);

    RiskAssessment deleteById(Long id);

    List<RiskAssessment> listAll();
}
