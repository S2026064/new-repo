package sars.pca.app.mb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.data.FilterEvent;
import org.springframework.beans.factory.annotation.Autowired;
import sars.pca.app.domain.Program;
import sars.pca.app.service.ProgramServiceLocal;

/**
 *
 * @author S2026015
 */
@ManagedBean
@ViewScoped
public class ProgramBean extends BaseBean<Program> {

    @Autowired
    private ProgramServiceLocal programService;
    private List<Program> filteredPrograms = new ArrayList<>();

    @PostConstruct
    public void init() {
        reset().setList(true);
        setPanelTitleName("Programs");
        addCollections(programService.listAll());
    }

    public void addOrUpdate(Program program) {
        reset().setAdd(true);
        if (program != null) {
            setPanelTitleName("Update Program");
            program.setUpdatedBy(getActiveUser().getSid());
            program.setUpdatedDate(new Date());
        } else {
            program = new Program();
            setPanelTitleName("Add Program");
            program.setCreatedBy(getActiveUser().getSid());
            program.setCreatedDate(new Date());
            addCollection(program);
        }
        addEntity(program);
    }

    public void save(Program program) {
        if (program.getId() != null) {
            programService.update(program);
            addInformationMessage("Program was successfully updated");
        } else {
            programService.save(program);
            addInformationMessage("Program successfully saved");
        }
        reset().setList(true);
        setPanelTitleName("Programs");
    }

    public void cancel(Program program) {
        if (program.getId() == null && getPrograms().contains(program)) {
            removeEntity(program);
        }
        reset().setList(true);
    }

    public void delete(Program program) {
        try {
            programService.deleteById(program.getId());
            removeEntity(program);
            addInformationMessage("Program was successfully deleted");
            reset().setList(true);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getMessage());
        }
    }

    public void filterListener(FilterEvent filterEvent) {
        getCollections().clear();
        if (filteredPrograms.isEmpty()) {
            addCollections(programService.listAll());
        } else {
            addCollections(filteredPrograms);
        }
    }

    public List<Program> getPrograms() {
        return this.getCollections();
    }

    public List<Program> getFilteredPrograms() {
        return filteredPrograms;
    }

    public void setFilteredPrograms(List<Program> filteredPrograms) {
        this.filteredPrograms = filteredPrograms;
    }

}
