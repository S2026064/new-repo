package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.Letter;

/**
 *
 * @author S2026987
 */
public interface LetterServiceLocal {

    Letter save(Letter letter);

    Letter findById(Long id);

    Letter update(Letter letter);

    Letter deleteById(Long id);

    List<Letter> listAll();

    boolean isExist(Letter letter);
    
}
