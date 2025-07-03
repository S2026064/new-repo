package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.common.ScoreType;
import sars.pca.app.common.YesNoEnum;
import sars.pca.app.domain.PrioritisationScore;
import sars.pca.app.service.PrioritisationScoreServiceLocal;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class PrioritisationScoreBean extends BaseBean<PrioritisationScore> {

    @Autowired
    private PrioritisationScoreServiceLocal prioritisationScoreService;
    private List<ScoreType> scoreTypes = new ArrayList<>();
    private List<YesNoEnum> yesNoOptions = new ArrayList<>();

    @PostConstruct
    public void init() {
        reset().setList(true);
        setPanelTitleName("List of Prioritisation Scores");
        addCollections(prioritisationScoreService.listAll());
        scoreTypes.addAll(Arrays.asList(ScoreType.values()));
        yesNoOptions.addAll(Arrays.asList(YesNoEnum.values()));
    }

    public void addOrUpdate(PrioritisationScore prioritisationScore) {
        reset().setAdd(true);
        if (prioritisationScore != null) {
            setPanelTitleName("Update Prioritisation Score");
            prioritisationScore.setUpdatedBy(getActiveUser().getSid());
            prioritisationScore.setUpdatedDate(new Date());
        } else {
            prioritisationScore = new PrioritisationScore();
            setPanelTitleName("Add Prioritisation Score");
            prioritisationScore.setCreatedBy(getActiveUser().getSid());
            prioritisationScore.setCreatedDate(new Date());
            addCollection(prioritisationScore);
        }
        addEntity(prioritisationScore);
    }

    public void save(PrioritisationScore prioritisationScore) {
        if (prioritisationScore.getId() != null) {
            prioritisationScoreService.update(prioritisationScore);
            addInformationMessage("Prioritisation Score was successfully updated");
        } else {
            prioritisationScoreService.save(prioritisationScore);
            addInformationMessage("Prioritisation Score successfully saved");
        }
        reset().setList(true);
        setPanelTitleName("List of Prioritisation Scores");
    }

    public void cancel(PrioritisationScore prioritisationScore) {
        if (prioritisationScore.getId() == null && getPrioritisationScores().contains(prioritisationScore)) {
            removeEntity(prioritisationScore);
        }
        reset().setList(true);
    }

    public void delete(PrioritisationScore prioritisationScore) {
        try {
            prioritisationScoreService.deleteById(prioritisationScore.getId());
            removeEntity(prioritisationScore);
            addInformationMessage("Prioritisation Score was successfully deleted");
            reset().setList(true);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }

    public List<PrioritisationScore> getPrioritisationScores() {
        return this.getCollections();
    }

    public List<ScoreType> getScoreTypes() {
        return scoreTypes;
    }

    public void setScoreTypes(List<ScoreType> scoreTypes) {
        this.scoreTypes = scoreTypes;
    }

    public List<YesNoEnum> getYesNoOptions() {
        return yesNoOptions;
    }

    public void setYesNoOptions(List<YesNoEnum> yesNoOptions) {
        this.yesNoOptions = yesNoOptions;
    }
    
}
