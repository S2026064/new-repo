package sars.pca.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;
import sars.pca.app.common.ConnectionType;
import sars.pca.app.common.DatasourceFactory;
import sars.pca.app.common.DatasourceService;
import sars.pca.app.common.PersonType;
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
@Service
public class IbrDataService implements IbrDataServiceLocal {
    Connection connection = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    @Override
    public IbrData getRegParticularsByCustomsCodePerIBRData(String customCode, String userSid) {
        IbrData ibrData = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Registration_Particulars_as_per_IBR_Data(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("Customs Code") != null) {
                    ibrData = new IbrData();
                    ibrData.setCreatedBy(userSid);
                    ibrData.setCreatedDate(new Date());
                    ibrData.setCustomsCode(rs.getString("Customs Code"));
                    ibrData.setIbrCustomsNumber(rs.getString("IBR Custums Nbr"));
                    ibrData.setRegistrationName(rs.getString("Registred Name"));
                    ibrData.setTradingName(rs.getString("Trading Name"));
                    ibrData.setNatureOfBusinessIncomeTax(rs.getString("Nature_of_Bus_Tax"));
                    ibrData.setNatureOfBusinessCustoms(rs.getString("Nature of Business Cust"));
                    ibrData.setPostalAddress(rs.getString("Postal Address"));
                    ibrData.setPhysicalAddress(rs.getString("Physical Address"));
                    ibrData.setAuditorsCipc(rs.getString("Auditors"));
                    ibrData.setAttorneys(rs.getString("Attorneys"));
                    ibrData.setRestrictedTaxPayer(rs.getString("Restricted Taxpayer"));
                    ibrData.setCompanyNumber(rs.getString("Comapny Nbr"));
                    ibrData.setVatNumber(rs.getString("VAT Nbr"));
                    ibrData.setIncomeTaxNumber(rs.getString("Income Tax Nbr"));
                    ibrData.setUifNumber(rs.getString("UIF Nbr"));
                    ibrData.setPayeNumber(rs.getString("PAYE Nbr"));
                    ibrData.setSdlNumber(rs.getString("SDL Nbr"));
                    ibrData.setSapFinNumber(rs.getString("SAP Fin Nbr"));
                    ibrData.setBondSuretyAmount("");
                    ibrData.setLinkedCustomsCodes(rs.getString("Linked customs codes"));
                    ibrData.setClNbr(rs.getString("cl_nbr"));
                    ibrData.setEmailAddress(rs.getString("Email"));
                    ibrData.setFaxNumber(rs.getString("Fax Nbr"));
                    ibrData.setWebAddress(rs.getString("Website Address"));
                    ibrData.setWorkTelephoneNumber(rs.getString("Telephone Nbr"));
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return ibrData;
    }

    @Override
    public List<DirectorDetails> getDirectorDetailsByCustomeCode(String customCode, String userSid) {
        List<DirectorDetails> directorDetailses = new ArrayList<>();
        DirectorDetails directorDetails = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Director_Details(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    directorDetails = new DirectorDetails();
                    directorDetails.setCreatedBy(userSid);
                    directorDetails.setCreatedDate(new Date());
                    directorDetails.setCompanyNumber(rs.getString("Company Nbr"));
                    directorDetails.setDirectorStatus(rs.getString("Director Status"));
                    directorDetails.setDirectorType(rs.getString("Director Type"));
                    directorDetails.setFirstNames(rs.getString("First Names"));
                    directorDetails.setIdNumber(rs.getString("Id No"));
                    directorDetails.setSurname(rs.getString("Surname"));
                    directorDetails.setImportDate(rs.getString("ImportDate"));

                    directorDetailses.add(directorDetails);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return directorDetailses;
    }

    @Override
    public List<CustomsTurnOver> getCustomsTurnOverByCustomeCode(String customCode, String userSid) {
        List<CustomsTurnOver> customsTurnOvers = new ArrayList<>();
        CustomsTurnOver customsTurnOver = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Customs_Turnover_for_the_previous_5_Years(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    customsTurnOver = new CustomsTurnOver();
                    customsTurnOver.setCreatedBy(userSid);
                    customsTurnOver.setCreatedDate(new Date());
                    customsTurnOver.setCalYear(rs.getString("Cal Year"));
                    customsTurnOver.setImportValue(rs.getString("Import Value"));
                    customsTurnOver.setExportValue(rs.getString("Export Value"));
                    customsTurnOver.setImportDuty(rs.getString("Import Duty"));
                    customsTurnOver.setImportVat(rs.getString("Import VAT"));
                    customsTurnOver.setImportLine(rs.getString("Import Lines"));
                    customsTurnOver.setExportLine(rs.getString("Export Lines"));
                    customsTurnOver.setImportDate(rs.getString("ImportDate"));

                    customsTurnOvers.add(customsTurnOver);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return customsTurnOvers;
    }

    @Override
    public List<OutstandingReturnAndDebt> getMonthEndOutStandingReturnsAndDebtsByCustomeCode(String customCode, String userSid) {
        List<OutstandingReturnAndDebt> outstandingReturnAndDebts = new ArrayList<>();
        OutstandingReturnAndDebt outstandingReturnAndDebt = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Month_End_Outstanding_Returns_and_Deb(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    outstandingReturnAndDebt = new OutstandingReturnAndDebt();
                    outstandingReturnAndDebt.setCreatedBy(userSid);
                    outstandingReturnAndDebt.setCreatedDate(new Date());
                    outstandingReturnAndDebt.setClNbr(rs.getString("cl_nbr"));
                    outstandingReturnAndDebt.setAccountType(rs.getString("Accnt Type"));
                    outstandingReturnAndDebt.setAccountNumber(rs.getString("Accnt Nbrs"));
                    outstandingReturnAndDebt.setOutstandingReturnNumber(rs.getString("Nbr Outst Returns"));
                    outstandingReturnAndDebt.setOldest(rs.getString("Oldest"));
                    outstandingReturnAndDebt.setOutstandingDebt(rs.getString("Outst Debt"));
                    outstandingReturnAndDebt.setOldestDebt(rs.getString("Oldest Debt"));
                    outstandingReturnAndDebt.setImportDate(rs.getString("ImportDate"));

                    outstandingReturnAndDebts.add(outstandingReturnAndDebt);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return outstandingReturnAndDebts;
    }

    @Override
    public List<PaymentAndRefund> getPaymentAndRefundByCustomeCode(String customCode, String userSid) {
        List<PaymentAndRefund> paymentAndRefunds = new ArrayList<>();
        PaymentAndRefund paymentAndRefund = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Payments_and_Refunds(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    paymentAndRefund = new PaymentAndRefund();
                    paymentAndRefund.setCreatedBy(userSid);
                    paymentAndRefund.setCreatedDate(new Date());
                    paymentAndRefund.setRefNumber(rs.getString("Ref Nbr"));
                    paymentAndRefund.setAccountType(rs.getString("Acc Type"));
                    paymentAndRefund.setPaymentRefundYear(rs.getString("Year"));
                    paymentAndRefund.setCategory(rs.getString("Category"));
                    paymentAndRefund.setPayment(rs.getString("Payments"));
                    paymentAndRefund.setUnallocatedPayment(rs.getString("Unallocated Payment"));
                    paymentAndRefund.setRefund(rs.getString("Refunds"));
                    paymentAndRefund.setImportDate(rs.getString("ImportDate"));

                    paymentAndRefunds.add(paymentAndRefund);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return paymentAndRefunds;
    }

    @Override
    public List<EnterpriseCompanyLink> getEnterpriseCompanyLinkByCustomeCode(String customCode, String userSid) {
        List<EnterpriseCompanyLink> enterpriseCompanyLinks = new ArrayList<>();
        EnterpriseCompanyLink enterpriseCompanyLink = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Enterprise_Company(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    enterpriseCompanyLink = new EnterpriseCompanyLink();
                    enterpriseCompanyLink.setCreatedBy(userSid);
                    enterpriseCompanyLink.setCreatedDate(new Date());
                    enterpriseCompanyLink.setRefNumber(rs.getString("Ref_Nbr"));
                    enterpriseCompanyLink.setName(rs.getString("Name"));
                    enterpriseCompanyLink.setLinkType(rs.getString("Link_Type"));
                    enterpriseCompanyLink.setLinkName(rs.getString("Link_Name"));
                    enterpriseCompanyLink.setLinkId(rs.getString("Link_Id"));
                    enterpriseCompanyLink.setShare(rs.getString("Share"));
                    enterpriseCompanyLink.setCompCnt(rs.getString("Comp_Cnt"));
                    enterpriseCompanyLink.setListed(rs.getString("Listed"));
                    enterpriseCompanyLink.setDirCnt(rs.getString("Dir_Cnt"));
                    enterpriseCompanyLink.setItTurnOver(rs.getString("IT Turnover"));
                    enterpriseCompanyLink.setItAssYear(rs.getString("IT_ass_Year"));
                    enterpriseCompanyLink.setTotOutDebt(rs.getString("Tot_Debt"));
                    enterpriseCompanyLink.setTotOutReturn(rs.getString("Out_Returns"));
                    enterpriseCompanyLink.setProperties(rs.getString("Tot_Properties"));
                    enterpriseCompanyLink.setVehicles(rs.getString("Tot_Vehicles"));
                    enterpriseCompanyLink.setAirCraft(rs.getString("Tot_Aircraft"));
                    enterpriseCompanyLink.setLinkedClNumber(rs.getString("Link_Cl_Nbr"));
                    enterpriseCompanyLink.setDistKey(rs.getString("Dist_Key"));
                    enterpriseCompanyLink.setRestricted(rs.getString("Restricted"));

                    enterpriseCompanyLinks.add(enterpriseCompanyLink);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return enterpriseCompanyLinks;
    }

    @Override
    public List<Emp201Return> getEmp201ReturnByCustomeCode(String customCode, String userSid) {
        List<Emp201Return> emp201Returns = new ArrayList<>();
        Emp201Return emp201Return = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Detail_of_the_last_12_EMP201_Returns(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    emp201Return = new Emp201Return();
                    emp201Return.setCreatedBy(userSid);
                    emp201Return.setCreatedDate(new Date());
                    emp201Return.setPayeNumber(rs.getString("PAYE Nbr"));
                    emp201Return.setPeriod(rs.getString("Period"));
                    emp201Return.setAmountPaye(rs.getString("Amt PAYE"));
                    emp201Return.setAmountSdl(rs.getString("Amt SDL"));
                    emp201Return.setAmountUif(rs.getString("Amt UIF"));
                    emp201Return.setTotal(rs.getString("Total"));
                    emp201Return.setDateDeclared(rs.getString("Date_Declared"));
                    emp201Return.setInterest(rs.getString("Interest"));
                    emp201Return.setPenalty(rs.getString("Penalty"));
                    emp201Return.setPrevTotalPayment(rs.getString("Prev Total Payment"));
                    emp201Return.setDateRecieved(rs.getString("Date Received"));
                    emp201Return.setImportDate(rs.getString("ImportDate"));
                    emp201Return.setClNbr(rs.getString("Cl_Nbr"));

                    emp201Returns.add(emp201Return);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return emp201Returns;
    }

    @Override
    public List<Commodity> getCommoditiesImportedPerCustomsValueByCustomeCode(String customCode, String userSid) {
        List<Commodity> commodities = new ArrayList<>();
        Commodity commodity = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Commodities_Imported_per_Customs_Value(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    commodity = new Commodity();
                    commodity.setCreatedBy(userSid);
                    commodity.setCreatedDate(new Date());
                    commodity.setTarrifCode(rs.getString("TarrifCode"));
                    commodity.setTradeType(rs.getString("TradeType"));
                    commodity.setSectionHead(rs.getString("Section_Heading"));
                    commodity.setCountryOfOrigin(rs.getString("CountryOfOrigin"));
                    commodity.setLines(rs.getString("Lines"));
                    commodity.setCustomsValue(rs.getString("Customs_Value"));
                    commodity.setDuty(rs.getString("Duty"));
                    commodity.setVatDue(rs.getString("Vat"));
                    commodity.setPurposeCode(rs.getString("PurposeCode"));
                    commodity.setAgentCode(rs.getString("AgentCode"));
                    commodity.setCustomOfficeName(rs.getString("CustomOfficeName"));
                    commodity.setImportDate(rs.getString("ImportDate"));

                    commodities.add(commodity);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return commodities;
    }

    @Override
    public List<Commodity> getCommoditiesExportedPerCustomsValueByCustomeCode(String customCode, String userSid) {
        List<Commodity> commodities = new ArrayList<>();
        Commodity commodity = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Commodities_Exported_per_Customs_Value(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    commodity = new Commodity();
                    commodity.setCreatedBy(userSid);
                    commodity.setCreatedDate(new Date());
                    commodity.setTarrifCode(rs.getString("TarrifCode"));
                    commodity.setTradeType(rs.getString("TradeType"));
                    commodity.setSectionHead(rs.getString("Section_Heading"));
                    commodity.setCountryOfOrigin(rs.getString("CountryOfOrigin"));
                    commodity.setLines(rs.getString("Lines"));
                    commodity.setCustomsValue(rs.getString("Customs_Value"));
                    commodity.setDuty(rs.getString("Duty"));
                    commodity.setVatDue(rs.getString("Vat"));
                    commodity.setPurposeCode(rs.getString("PurposeCode"));
                    commodity.setAgentCode(rs.getString("AgentCode"));
                    commodity.setCustomOfficeName(rs.getString("CustomOfficeName"));
                    commodity.setImportDate(rs.getString("ImportDate"));

                    commodities.add(commodity);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return commodities;
    }

    @Override
    public List<Commodity> getCommoditiesWarehousePerCustomsValueByCustomeCode(String customCode, String userSid) {
        List<Commodity> commodities = new ArrayList<>();
        Commodity commodity = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Commodities_Warehoused_per_Customs_Value(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    commodity = new Commodity();
                    commodity.setCreatedBy(userSid);
                    commodity.setCreatedDate(new Date());
                    commodity.setTarrifCode(rs.getString("TarrifCode"));
                    commodity.setSectionHead(rs.getString("Section_Heading"));
                    commodity.setLines(rs.getString("Lines"));
                    commodity.setCustomsValue(rs.getString("Customs_Value"));
                    commodity.setDuty(rs.getString("Duty"));
                    commodity.setVatDue(rs.getString("Vat"));
                    commodity.setImportDate(rs.getString("ImportDate"));

                    commodities.add(commodity);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return commodities;
    }

    @Override
    public List<Commodity> getTransitCommoditiesPerCustomsValueByCustomeCode(String customCode, String userSid) {
        List<Commodity> commodities = new ArrayList<>();
        Commodity commodity = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Transit_Commodities_per_Customs_Value(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    commodity = new Commodity();
                    commodity.setCreatedBy(userSid);
                    commodity.setCreatedDate(new Date());
                    commodity.setTarrifCode(rs.getString("TarrifCode"));
                    commodity.setSectionHead(rs.getString("Section_Heading"));
                    commodity.setLines(rs.getString("Lines"));
                    commodity.setCustomsValue(rs.getString("Customs_Value"));
                    commodity.setDuty(rs.getString("Duty"));
                    commodity.setVatDue(rs.getString("Vat"));
                    commodity.setImportDate(rs.getString("ImportDate"));

                    commodities.add(commodity);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return commodities;
    }

    @Override
    public List<CountryOfOrigin> getCountriesOfOriginPerCustomsValueByCustomeCode(String customCode, String userSid) {
        List<CountryOfOrigin> countryOfOrigins = new ArrayList<>();
        CountryOfOrigin countryOfOrigin = null;
        //Country country = null;
        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Countries_of_Origin_per_Customs_Value(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    countryOfOrigin = new CountryOfOrigin();
                    countryOfOrigin.setCreatedBy(userSid);
                    countryOfOrigin.setCreatedDate(new Date());
                    countryOfOrigin.setCountryName(rs.getString("CountryOfOrigin"));
                    countryOfOrigin.setLines(rs.getString("Lines"));
                    countryOfOrigin.setCustomsValue(rs.getString("Customs_Value"));
                    countryOfOrigin.setDuty(rs.getString("Duty"));
                    countryOfOrigin.setVatDue(rs.getString("Vat"));
                    countryOfOrigin.setImportDate(rs.getString("ImportDate"));

                    countryOfOrigins.add(countryOfOrigin);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return countryOfOrigins;
    }

    @Override
    public List<CustomsValue> getPurposeCodesPerCustomsValueByCustomeCode(String customCode, String userSid) {
        List<CustomsValue> customsValues = new ArrayList<>();
        CustomsValue customsValue = null;

        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Purpose_Codes_per_Customs_Value(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    customsValue = new CustomsValue();
                    customsValue.setCreatedBy(userSid);
                    customsValue.setCreatedDate(new Date());
                    customsValue.setPurposeCode(rs.getString("Purpose Code"));
                    customsValue.setLines(rs.getString("Lines"));
                    customsValue.setCustomsValue(rs.getString("Customs Value"));
                    customsValue.setDuty(rs.getString("Duty"));
                    customsValue.setVatDue(rs.getString("Vat"));

                    customsValues.add(customsValue);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return customsValues;
    }

    @Override
    public List<CustomsValue> getClearingAgentsPerCustomsValueByCustomeCode(String customCode, String userSid) {
        List<CustomsValue> customsValues = new ArrayList<>();
        CustomsValue customsValue = null;

        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Clearing_Agents_per_Customs_Value(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    customsValue = new CustomsValue();
                    customsValue.setCreatedBy(userSid);
                    customsValue.setCreatedDate(new Date());
                    customsValue.setAgentCode(rs.getString("AgentCode"));
                    customsValue.setClearingAgent(rs.getString("Clearing Agents"));
                    customsValue.setLines(rs.getString("Lines"));
                    customsValue.setCustomsValue(rs.getString("Customs_Value"));
                    customsValue.setDuty(rs.getString("Duty"));
                    customsValue.setVatDue(rs.getString("Vat"));

                    customsValue.setImportDate(rs.getString("ImportDate"));

                    customsValues.add(customsValue);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return customsValues;
    }

    @Override
    public List<CustomsValue> getPortsOfEntryPerCustomsValueByCustomeCode(String customCode, String userSid) {
        List<CustomsValue> customsValues = new ArrayList<>();
        CustomsValue customsValue = null;

        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Ports_of_Entry_per_Customs_Value(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    customsValue = new CustomsValue();
                    customsValue.setCreatedBy(userSid);
                    customsValue.setCreatedDate(new Date());
                    customsValue.setCustomOfficeName(rs.getString("CustomOfficeName"));
                    customsValue.setLines(rs.getString("Lines"));
                    customsValue.setCustomsValue(rs.getString("Customs_Value"));
                    customsValue.setDuty(rs.getString("Duty"));
                    customsValue.setVatDue(rs.getString("Vat"));
                    customsValue.setImportDate(rs.getString("ImportDate"));

                    customsValues.add(customsValue);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return customsValues;
    }

    @Override
    public List<CustomsRegistrationParticulars> getCustomsRegistrationParticulars(String customCode, String userSid) {

        List<CustomsRegistrationParticulars> customsRegistrationParticularses = new ArrayList<>();
        CustomsRegistrationParticulars customsRegistrationParticulars = null;

        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Customs_Registration_Particulars_carried(?)}");
            stmt.setString(1, customCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("IBR Custums Nbr") != null) {
                    customsRegistrationParticulars = new CustomsRegistrationParticulars();
                    customsRegistrationParticulars.setCreatedBy(userSid);
                    customsRegistrationParticulars.setCreatedDate(new Date());
                    customsRegistrationParticulars.setRegistrationType(rs.getString("Registration Type"));
                    customsRegistrationParticulars.setAddress(rs.getString("Address"));
                    customsRegistrationParticulars.setStatus(rs.getString("Status"));
                    customsRegistrationParticulars.setActivationDate(rs.getString("Date Active Inactive"));
                    customsRegistrationParticulars.setImportDate(rs.getString("ImportDate"));

                    customsRegistrationParticularses.add(customsRegistrationParticulars);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + customCode).log(Level.SEVERE, null, e);
            }
        }
        return customsRegistrationParticularses;
    }

    @Override
    public List<SarNonCompliance> getSarNonCompliance(String clnbr, String userSid) {
        List<SarNonCompliance> sarNonCompliances = new ArrayList<>();
        SarNonCompliance sarNonCompliance = null;

        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Suspicious_Activity_Reports_and_Report_Suspicious_Noncomplianc(?)}");
            stmt.setString(1, clnbr);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("cl_nbr") != null) {
                    sarNonCompliance = new SarNonCompliance();
                    sarNonCompliance.setCreatedBy(userSid);
                    sarNonCompliance.setCreatedDate(new Date());
                    // sarNonCompliance.setClNbr(rs.getString("cl_nbr"));
                    sarNonCompliance.setIncidentId(rs.getString("Incident Id"));
                    sarNonCompliance.setIncident(rs.getString("Incident"));
                    sarNonCompliance.setIncidentDate(rs.getString("Incident Date"));
                    sarNonCompliance.setIncidentDescription(rs.getString("Inc Description"));
                    sarNonCompliance.setTaxType(rs.getString("TAX Type"));
                    sarNonCompliance.setIncidentValue(rs.getString("Value"));
                    sarNonCompliance.setOtherInfo(rs.getString("Other info"));

                    sarNonCompliance.setImportDate(rs.getString("ImportDate"));

                    sarNonCompliance.setOldestDebt("");

                    sarNonCompliances.add(sarNonCompliance);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + clnbr).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + clnbr).log(Level.SEVERE, null, e);
            }
        }
        return sarNonCompliances;
    }

    @Override
    public List<PublicOfficer> getPublicOfficer(String clnbr, String userSid) {
        List<PublicOfficer> publicOfficers = new ArrayList<>();
        PublicOfficer publicOfficer = null;

        try {
            DatasourceService datasourceService = DatasourceFactory.getDatabase(ConnectionType.IBR_PCA_DATABASE);
            connection = datasourceService.getDatasourceConnection();
            stmt = connection.prepareStatement("{call  dbo.usp_READ_PROD_Public_Officers(?)}");
            stmt.setString(1, clnbr);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("cl_nbr") != null) {
                    publicOfficer = new PublicOfficer();
                    publicOfficer.setCreatedBy(userSid);
                    publicOfficer.setCreatedDate(new Date());
//                    publicOfficer.setBirthDate(new Date());
                    publicOfficer.setPersonType(PersonType.PUBLIC_OFFICER);
                    publicOfficer.setPublicOfficerAt(rs.getString("Puplic Officer At:"));
                    publicOfficer.setLinkedRefNBR(rs.getString("Linked Ref-Nbr"));
                    publicOfficer.setLinkedAccountType("Linked Account Type");
                    publicOfficer.setLinkedItTurnover(rs.getString("Linked IT-Turnover"));
                    publicOfficer.setLinkedItAssYr(rs.getString("Linked IT Ass-Yr"));
                    publicOfficer.setLinkedTotOutstDebt(rs.getString("Linked Tot Outst. Debt"));
                    publicOfficer.setLinkedTotOutstReturns(rs.getString("Linked Tot Outst. Returns"));
                    publicOfficer.setPoName(rs.getString("PO Name"));
                    publicOfficer.setPoIdNumber(rs.getString("PO ID Number"));
                    publicOfficer.setPoTaxNbr(rs.getString("PO Tax Nbr"));
                    publicOfficer.setPoPhoneNbr(rs.getString("PO Phone Nbr"));
                    publicOfficer.setPoCellNbr(rs.getString("PO Cell Nbr"));
                    publicOfficer.setImportDate(rs.getString("ImportDate"));

                    publicOfficers.add(publicOfficer);
                }
            }

        } catch (SQLException e) {
            Logger.getLogger(IbrDataService.class.getName() + ":" + clnbr).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.getLogger(IbrDataService.class.getName() + ":" + clnbr).log(Level.SEVERE, null, e);
            }
        }
        return publicOfficers;
    }
}
