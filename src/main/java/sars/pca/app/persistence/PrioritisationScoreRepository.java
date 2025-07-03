package sars.pca.app.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sars.pca.app.common.ScoreType;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.PrioritisationScore;

/**
 *
 * @author S2026987
 */
@Repository
public interface PrioritisationScoreRepository extends JpaRepository<PrioritisationScore, Long> {
    @Query("SELECT p.score FROM PrioritisationScore p WHERE (:customValueParam BETWEEN p.minimunValue AND p.maximumValue) AND p.scoreType=:scoreType")
    Integer findScorebyAmount(@Param("customValueParam") Double customValue, @Param("scoreType") ScoreType scoreType);

    @Query("SELECT p.score FROM PrioritisationScore p WHERE (:lineNum BETWEEN p.minimunLine AND p.maximumLine) AND p.scoreType=:scoreType")
    Integer findScorebyNumberOfLines(@Param("lineNum") Integer lineNumber, @Param("scoreType") ScoreType scoreType);

    @Query("SELECT p.score FROM PrioritisationScore p WHERE p.commodityAnswer=:yesNoEnum AND p.scoreType=:scoreType")
    Integer findScorebyCommodityAlignment(@Param("yesNoEnum") YesNoEnum yesNoEnum, @Param("scoreType") ScoreType scoreType);
}
