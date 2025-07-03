package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.Program;

/**
 *
 * @author S2026015
 */
public interface ProgramServiceLocal {
    
    Program save(Program program);
    
    Program findById(Long id);
    
    Program update(Program program);
    
    Program deleteById(Long id);
    
    List<Program> listAll();
}
