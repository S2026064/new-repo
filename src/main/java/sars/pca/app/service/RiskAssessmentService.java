package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.RiskAssessment;
import sars.pca.app.persistence.RiskAssessmentRepository;

/**
 *
 * @author S2026080
 */
@Service
@Transactional
public class RiskAssessmentService implements RiskAssessmentServiceLocal {

    @Autowired
    private RiskAssessmentRepository riskAssessmentRepository;

    @Override
    public RiskAssessment save(RiskAssessment riskAssessment) {
        return riskAssessmentRepository.save(riskAssessment);
    }

    @Override
    public RiskAssessment findById(Long id) {

        return riskAssessmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public RiskAssessment update(RiskAssessment riskAssessment) {
        return riskAssessmentRepository.save(riskAssessment);
    }

    @Override
    public RiskAssessment deleteById(Long id) {
        RiskAssessment riskAssessment = findById(id);
        if (riskAssessment != null) {
            riskAssessmentRepository.delete(riskAssessment);
        }
        return riskAssessment;
    }

    @Override
    public List<RiskAssessment> listAll() {
        return riskAssessmentRepository.findAll();
    }
}
