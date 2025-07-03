package sars.pca.app.service;

import java.util.List;
import sars.pca.app.domain.Commodity;
import sars.pca.app.domain.CountryOfOrigin;
import sars.pca.app.domain.CustomsRegistrationParticulars;
import sars.pca.app.domain.CustomsTurnOver;
import sars.pca.app.domain.CustomsValue;
import sars.pca.app.domain.DirectorDetails;
import sars.pca.app.domain.Emp201Return;
import sars.pca.app.domain.EnterpriseCompanyLink;
import sars.pca.app.domain.IbrData;
import sars.pca.app.domain.OutstandingReturnAndDebt;
import sars.pca.app.domain.PaymentAndRefund;
import sars.pca.app.domain.PublicOfficer;
import sars.pca.app.domain.SarNonCompliance;

/**
 *
 * @author S2026987
 */
public interface IbrDataServiceLocal {
    public IbrData getRegParticularsByCustomsCodePerIBRData(String customCode, String userSid);

    public List<DirectorDetails> getDirectorDetailsByCustomeCode(String customCode, String userSid);

    public List<CustomsTurnOver> getCustomsTurnOverByCustomeCode(String customCode, String userSid);

    public List<OutstandingReturnAndDebt> getMonthEndOutStandingReturnsAndDebtsByCustomeCode(String customCode, String userSid);

    public List<PaymentAndRefund> getPaymentAndRefundByCustomeCode(String customCode, String userSid);

    public List<EnterpriseCompanyLink> getEnterpriseCompanyLinkByCustomeCode(String customCode, String userSid);

    public List<Emp201Return> getEmp201ReturnByCustomeCode(String customCode, String userSid);

    public List<Commodity> getCommoditiesImportedPerCustomsValueByCustomeCode(String customCode, String userSid);

    public List<Commodity> getCommoditiesExportedPerCustomsValueByCustomeCode(String customCode, String userSid);

    public List<Commodity> getCommoditiesWarehousePerCustomsValueByCustomeCode(String customCode, String userSid);

    public List<Commodity> getTransitCommoditiesPerCustomsValueByCustomeCode(String customCode, String userSid);

    public List<CountryOfOrigin> getCountriesOfOriginPerCustomsValueByCustomeCode(String customCode, String userSid);

    public List<CustomsValue> getPurposeCodesPerCustomsValueByCustomeCode(String customCode, String userSid);

    public List<CustomsValue> getClearingAgentsPerCustomsValueByCustomeCode(String customCode, String userSid);

    public List<CustomsValue> getPortsOfEntryPerCustomsValueByCustomeCode(String customCode, String userSid);

    public List<CustomsRegistrationParticulars> getCustomsRegistrationParticulars(String customCode, String userSid);
    
    public List<SarNonCompliance> getSarNonCompliance(String clnbr, String userSid);
    
    public List<PublicOfficer> getPublicOfficer(String clnbr, String userSid);    
}
