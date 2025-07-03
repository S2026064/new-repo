package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.HsChapterRisk;

/**
 *
 * @author S2026987
 */
public interface HsChapterRiskServiceLocal {

    HsChapterRisk save(HsChapterRisk hsChapterRisk);

    HsChapterRisk findById(Long id);

    HsChapterRisk update(HsChapterRisk hsChapterRisk);

    HsChapterRisk deleteById(Long id);

    List<HsChapterRisk> listAll();

}
