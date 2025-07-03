package sars.pca.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sars.pca.app.domain.Letter;
import sars.pca.app.persistence.LetterRepository;

/**
 *
 * @author S2026064
 */
@Service
@Transactional
public class LetterService implements LetterServiceLocal {

    @Autowired
    private LetterRepository letterRepository;

    @Override
    public Letter save(Letter letter) {
        return letterRepository.save(letter);

    }
    @Override
    public Letter findById(Long id) {
        return letterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                "The requested id [" + id
                + "] does not exist."));

    }

    @Override
    public Letter deleteById(Long id) {
        Letter letter = findById(id);
        if (letter != null) {
            letterRepository.delete(letter);
        }
        return letter;

    }
    @Override
    public List<Letter> listAll() {
        return letterRepository.findAll();

    }
    @Override
    public boolean isExist(Letter letter) {
        return letterRepository.findById(letter.getId()) != null;

    }
    @Override
    public Letter update(Letter letter) {
        return letterRepository.save(letter);
    }
}
