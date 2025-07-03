package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.HsChapterRisk;
import sars.pca.app.persistence.HsChapterRiskRepository;

/**
 *
 * @author S2026987
 */
@Service
@Transactional
public class HsChapterRiskService implements HsChapterRiskServiceLocal {

    @Autowired
    private HsChapterRiskRepository hsChapterRiskRepository;

    @Override
    public HsChapterRisk save(HsChapterRisk hsChapterRisk) {
        return hsChapterRiskRepository.save(hsChapterRisk);
    }

    @Override
    public HsChapterRisk findById(Long id) {
        return hsChapterRiskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public HsChapterRisk update(HsChapterRisk hsChapterRisk) {
        return hsChapterRiskRepository.save(hsChapterRisk);
    }

    @Override
    public HsChapterRisk deleteById(Long id) {
        HsChapterRisk hsChapterRisk = findById(id);
        if (hsChapterRisk != null) {
            hsChapterRiskRepository.delete(hsChapterRisk);
        }
        return hsChapterRisk;
    }

    @Override
    public List<HsChapterRisk> listAll() {
        return hsChapterRiskRepository.findAll();
    }

}
