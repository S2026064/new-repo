package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.VddLedCase;

/**
 *
 * @author S2026015
 */
public interface VddlServiceLocal {

    VddLedCase save(VddLedCase disclosureOrDemandLed);

    VddLedCase findById(Long id);

    VddLedCase update(VddLedCase disclosureOrDemandLed);

    VddLedCase deleteById(Long id);

    List<VddLedCase> listAll();

    boolean isExist(VddLedCase disclosureOrDemandLed);
//
//    VddLedCase findByCaseRefNumber(String caseRefNumber);
//
//    VddLedCase findByCaseRefNumberAndVddlStatus(String caseRefNumber, VddlStatus vddlStatus);
//
//    List<VddLedCase> findByVddlStatus(VddlStatus vddlStatus);
//
//    VddLedCase findLastInsertedVddlRecord();

}
