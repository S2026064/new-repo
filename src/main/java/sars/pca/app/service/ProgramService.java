package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.Program;
import sars.pca.app.persistence.ProgramRepository;

/**
 *
 * @author S2026015
 */
@Service
@Transactional
public class ProgramService implements ProgramServiceLocal {

    @Autowired
    private ProgramRepository programRepository;

    @Override
    public Program save(Program program) {
        return programRepository.save(program);
    }

    @Override
    public Program findById(Long id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));
    }

    @Override
    public Program update(Program program) {
        return programRepository.save(program);
    }

    @Override
    public Program deleteById(Long id) {
        Program relevantMaterial = findById(id);
        if (relevantMaterial != null) {
            programRepository.delete(relevantMaterial);
        }
        return relevantMaterial;
    }

    @Override
    public List<Program> listAll() {
        return programRepository.findAll();
    }

}
