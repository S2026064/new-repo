/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sars.pca.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.springframework.stereotype.Service;
import sars.pca.app.common.DateUtil;
import sars.pca.app.common.LetterHead;
import sars.pca.app.domain.AuditReportOffencePenalty;
import sars.pca.app.domain.Auditor;
import sars.pca.app.domain.CustomDeclaration;
import sars.pca.app.domain.DutyVat;
import sars.pca.app.domain.Letter;
import sars.pca.app.domain.Program;
import sars.pca.app.domain.RelevantMaterial;
import sars.pca.app.gen.dto.dgm.RequestOperationType;
import sars.pca.app.gen.dto.req.GeneratePDFDocument;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Footer;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Header;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.AddressDetails;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.AddressDetails.Item;

import sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.ContactDetail;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.OtherItems;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.ColumnNames;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row;
import sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items;

import sars.pca.app.gen.dto.req.PDFDocumentGenerationManagementRequest;
import sars.pca.app.gen.dto.req.PDFDocumentGenerationManagementRequest.Details;
import sars.pca.app.gen.dto.req.PDFDocumentGenerationManagementRequest.Details.Detail;

/**
 *
 * @author S2026080
 */
@Service
public class EnhancedLetterGenerationService {

    private MailService mailService = new MailService();
    private LDAPService lDAPService = new LDAPService();

    private String point = " • ";
    private String good = "<br></br>";

    public String constructMsg(Letter letter) throws IOException {
        try {

            PDFDocumentGenerationManagementRequest request = new PDFDocumentGenerationManagementRequest();

            request.setRequestOperation(RequestOperationType.GENERATE_LETTER.toString());

            Details details = new Details();
            Detail detail = new Detail();
            GeneratePDFDocument generatePDFDocument = new GeneratePDFDocument();

            generatePDFDocument.setHeader(createHeader(letter));
            generatePDFDocument.setContentItems(createContentItems(letter));
            generatePDFDocument.setTables(createTables(letter));

            generatePDFDocument.setFooter(createFooter(letter));

            detail.setGeneratePDFDocument(generatePDFDocument);
            details.setDetail(detail);
            request.setDetails(details);

            JacksonXmlModule xmlModule = new JacksonXmlModule();
            xmlModule.setDefaultUseWrapper(false);
            ObjectMapper objectMapper = new XmlMapper(xmlModule);
            String xml = objectMapper.writeValueAsString(request);
            XmlMapper xmlMapper = new XmlMapper();
            request = objectMapper.readValue(xml, PDFDocumentGenerationManagementRequest.class);
            request.setXmlnsXsi("http://www.w3.org/2001/XMLSchema-instance");
            request.setXmlnsXsd("http://www.w3.org/2001/XMLSchema");
            request.setXmlns("http://www.sars.gov.za/enterpriseMessagingModel/PDFDocumentGenerationManagement/xml/schemas/version/1.0");
            request.getDetails().getDetail().getGeneratePDFDocument().setXmlns("http://www.sars.gov.za/enterpriseMessagingModel/GeneratePDFDocument/xml/schemas/");

            JAXBContext context = JAXBContext.newInstance(PDFDocumentGenerationManagementRequest.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(request, stringWriter);

            return stringWriter.toString();
        } catch (JAXBException ex) {
            Logger.getLogger(EnhancedLetterGenerationService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(EnhancedLetterGenerationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Tables createTables(Letter letter) {
        Tables tables = new Tables();
        List<Table> table = new ArrayList<>();
        if (letter.getLetterHead().equals(LetterHead.REQUEST_FOR_DOCUMENTATION) || letter.getLetterHead().equals(LetterHead.NOTIFICATION_OF_AUDIT_DESK) || letter.getLetterHead().equals(LetterHead.NOTIFICATION_OF_AUDIT_FIELD)) {
            Table tableItem = new Table();
            tableItem.setTableName("Details of Documentation Required");
            ColumnNames columnNames = new ColumnNames();
            List<String> columnNamesList = new ArrayList<>();
            columnNamesList.add("Documentation");
            columnNamesList.add("Customs Declaration(s)");
            columnNames.setColumnName(columnNamesList);
            tableItem.setColumnNames(columnNames);
            Rows rows = new Rows();
            List<Row> row = new ArrayList<>();
            if (letter.getId() != null) {
                Row tableRow = new Row();
                Items items = new Items();
                StringBuilder documentation = new StringBuilder();
                sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item docItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item();
                sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item declararionItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item();
                docItem.setName("Documentation");
                for (RelevantMaterial documentType : letter.getRelevantMaterials()) {
                    documentation.append(".");
                    documentation.append(documentType.getDescription());
                    documentation.append("\n");
                }
                docItem.setValue(documentation.toString());
                items.getItem().add(docItem);
                declararionItem.setName("Customs Declaration(s)");
                documentation = new StringBuilder();
                for (CustomDeclaration declaration : letter.getDeclarations()) {
                    documentation.append(".");
                    documentation.append(declaration.getDescription());
                    documentation.append("\n");
                }
                declararionItem.setValue(documentation.toString());
                items.getItem().add(declararionItem);
                tableRow.setItems(items);
                row.clear();
                row.add(tableRow);
                rows.setRow(row);
                tableItem.setRows(rows);

            }
            table.add(tableItem);
        }
        if (letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS) || letter.getLetterHead().equals(LetterHead.IMPOSITION_OF_PENALTY) || letter.getLetterHead().equals(LetterHead.NOTICE_OF_INTENT)) {

            Table tableItem1 = new Table();
            tableItem1.setTableName("Contravention/Offence Details");
            ColumnNames columnNames = new ColumnNames();
            List<String> columnNamesList = new ArrayList<>();
            columnNamesList.add("Section / Rule Contravened");
            columnNamesList.add("Circumstance");
            columnNamesList.add("Offence");
            if (letter.getLetterHead().equals(LetterHead.IMPOSITION_OF_PENALTY)) {
                columnNamesList.add("Penalty");
            }
            if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS) && !letter.getLetterHead().equals(LetterHead.IMPOSITION_OF_PENALTY)) {
                columnNamesList.add("Deposit for Possible Penalty");
            }
            columnNames.setColumnName(columnNamesList);
            tableItem1.setColumnNames(columnNames);
            Rows rows = new Rows();
            List<Row> row = new ArrayList<>();
            if (!letter.getSuspiciousCase().getAuditPlan().getAuditReport().getAuditReportOffencePenalties().isEmpty()) {

                for (AuditReportOffencePenalty offence : letter.getSuspiciousCase().getAuditPlan().getAuditReport().getAuditReportOffencePenalties()) {
                    Row tableRow = new Row();
                    Items items = new Items();
                    sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item sectionRuleContravened = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item();
                    sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item circumstanceItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item();
                    sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item offenceItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item();
                    sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item offencePenaltyItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item();
                    sectionRuleContravened.setName("Section / Rule Contravened");
                    circumstanceItem.setName("Circumstance");
                    offenceItem.setName("Offence");
                    if (letter.getLetterHead().equals(LetterHead.IMPOSITION_OF_PENALTY)) {
                        offencePenaltyItem.setName("Penalty");
                    }
                    if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS) && !letter.getLetterHead().equals(LetterHead.IMPOSITION_OF_PENALTY)) {
                        offencePenaltyItem.setName("Deposit for Possible Penalty");
                    }
                    sectionRuleContravened.setValue(offence.getOffencePenalty().getSectionRule().getDescription());
                    items.getItem().add(sectionRuleContravened);

                    circumstanceItem.setValue(offence.getOffencePenalty().getOffenceClassification().getDescription());
                    items.getItem().add(circumstanceItem);

                    offenceItem.setValue(offence.getOffencePenalty().getOffenceDescription().getDescription());
                    items.getItem().add(offenceItem);
                    if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS)) {
                        String amount = String.valueOf(offence.getOffencePenalty().getSubOffenceOrPenaltyAmount());
                        offencePenaltyItem.setValue(amount);
                        items.getItem().add(offencePenaltyItem);
                    }
                    tableRow.setItems(items);
                    row.clear();
                    row.add(tableRow);
                    rows.setRow(row);
                    tableItem1.setRows(rows);

                }

            }
            table.add(tableItem1);
        }

        if (letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS) || letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND) || letter.getLetterHead().equals(LetterHead.NOTICE_OF_INTENT)) {

            Table tableItem2 = new Table();
            if (letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {
                tableItem2.setTableName("Duties and Taxes Underpaid");
            }
            if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {
                tableItem2.setTableName("Duty/VAT");
            }

            ColumnNames columnNames = new ColumnNames();
            List<String> columnNamesList = new ArrayList<>();
            if (letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {
                columnNamesList.add("Declaration and Line Number");
            }
            if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {
                columnNamesList.add("Declaration Number");

                columnNamesList.add("Line Number");
            }
            columnNamesList.add("Type of Duty or Tax Underpaid");
            columnNamesList.add("Amount");
            columnNames.setColumnName(columnNamesList);
            tableItem2.setColumnNames(columnNames);
            Rows rows = new Rows();
            List<Row> row = new ArrayList<>();
            if (!letter.getDutyVats().isEmpty()) {
                for (DutyVat dutyVat : letter.getDutyVats()) {
                    Row tableRow = new Row();
                    Items items = new Items();

                    sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item declarationNumberItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item();
                    sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item lineNumberItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item();
                    sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item dutyTypeItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item();
                    sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item amountItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Tables.Table.Rows.Row.Items.Item();
                    if (letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {
                        declarationNumberItem.setName("Declaration and Line Number");
                    }
                    if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {
                        declarationNumberItem.setName("Declaration Number");
                        lineNumberItem.setName("Line Number");
                    }
                    dutyTypeItem.setName("Type of Duty or Tax Underpaid");
                    amountItem.setName("Deposit for Possible Penalty");

                    declarationNumberItem.setValue(dutyVat.getDeclarationLineNumber());
                    items.getItem().add(declarationNumberItem);
                    if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {
                        lineNumberItem.setValue(dutyVat.getLineNumber());
                        items.getItem().add(lineNumberItem);
                    }

                    dutyTypeItem.setValue(dutyVat.getTypeOfDuty());
                    items.getItem().add(dutyTypeItem);
                    String amount = String.valueOf(dutyVat.getAmount());
                    String dutyAmount = (amount);
                    amountItem.setValue(dutyAmount);
                    items.getItem().add(amountItem);

                    tableRow.setItems(items);
                    row.clear();
                    row.add(tableRow);
                    rows.setRow(row);
                    tableItem2.setRows(rows);

                }

            }
            table.add(tableItem2);
        }
        tables.setTable(table);
        return tables;
    }

    private Header createHeader(Letter letter) {

        Header header = new Header();
        AddressDetails addressDetails = new AddressDetails();
        Item clientNameItem = new Item();
        clientNameItem.setName("Client Name");
        clientNameItem.setValue(letter.getSuspiciousCase().getIbrData().getRegistrationName());
        addressDetails.getItem().add(clientNameItem);

        Item clientAddressLine1Item = new Item();
        clientAddressLine1Item.setName("Client Address line 1");
        clientAddressLine1Item.setValue(letter.getSuspiciousCase().getIbrData().getPostalAddress());
        addressDetails.getItem().add(clientAddressLine1Item);

        Item clientAddressLine2Item = new Item();
        clientAddressLine2Item.setName("Client Address line 2");
        clientAddressLine2Item.setValue(letter.getAddress().getLine2());
        addressDetails.getItem().add(clientAddressLine2Item);

        Item clientAddressLine3Item = new Item();
        clientAddressLine3Item.setName("Client Address line 3");
        clientAddressLine3Item.setValue(letter.getAddress().getLine3());
        addressDetails.getItem().add(clientAddressLine3Item);

        Item clientPostalCodeItem = new Item();
        clientPostalCodeItem.setName("Client Postal Code");
        clientPostalCodeItem.setValue(letter.getAddress().getCode());
        addressDetails.getItem().add(clientPostalCodeItem);

        header.setAddressDetails(addressDetails);

        ContactDetail contactDetail = new ContactDetail();
        sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.ContactDetail.Item nameItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.ContactDetail.Item();
        nameItem.setName("Name");
        nameItem.setValue(letter.getUser().getFullNames());
        contactDetail.getItem().add(nameItem);

        sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.ContactDetail.Item emailItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.ContactDetail.Item();
        emailItem.setName("Email");
        emailItem.setValue(letter.getUser().getEmailAddress());
        contactDetail.getItem().add(emailItem);
        header.setContactDetail(contactDetail);

        sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.Details details = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.Details();
        sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.Details.Item customCodeItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.Details.Item();
        sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.Details.Item caseNumberItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.Details.Item();
        if (letter.getId() != null) {
            customCodeItem.setName("CustomsCode");
            customCodeItem.setValue(letter.getSuspiciousCase().getIbrData().getIbrCustomsNumber());
            details.getItem().add(customCodeItem);

            caseNumberItem.setName("Case Number");
            caseNumberItem.setValue(letter.getSuspiciousCase().getCaseRefNumber());
            details.getItem().add(caseNumberItem);
        } else {
            customCodeItem.setName("CustomsCode");
            customCodeItem.setValue(letter.getSuspiciousCase().getIbrData().getIbrCustomsNumber());
            details.getItem().add(customCodeItem);

            caseNumberItem.setName("Case Number");
            caseNumberItem.setValue(letter.getSuspiciousCase().getCaseRefNumber());
            details.getItem().add(caseNumberItem);
        }
        if (letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND) || letter.getLetterHead().equals(LetterHead.IMPOSITION_OF_PENALTY) || letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS) || letter.getLetterHead().equals(LetterHead.FINALIZATION_OF_AUDIT) || letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS)) {
            sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.Details.Item auditPeriodItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.Details.Item();
            auditPeriodItem.setName("Audit Period");
            auditPeriodItem.setValue(String.valueOf(letter.getSuspiciousCase().getAuditPlan().getAuditPeriod()));
            details.getItem().add(auditPeriodItem);
        }
        sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.Details.Item issueDateItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.Details.Item();
        issueDateItem.setName("Issue Date");
        issueDateItem.setValue(DateUtil.convertStringToDate(letter.getIssueDate()));
        details.getItem().add(issueDateItem);
        header.setDetails(details);

        OtherItems otherItems = new OtherItems();
        sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.OtherItems.Item departmentItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.OtherItems.Item();
        departmentItem.setName("Department");
        departmentItem.setValue("CUSTOMS");
        otherItems.getItem().add(departmentItem);

        sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.OtherItems.Item letterNameItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Header.OtherItems.Item();
        letterNameItem.setName("Name");
        letterNameItem.setValue(letter.getLetterHead().toString());
        otherItems.getItem().add(letterNameItem);
        header.setOtherItems(otherItems);

        return header;

    }

    private ContentItems createContentItems(Letter letter) {
        ContentItems contentItems = new ContentItems();
        sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item salutationItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
        salutationItem.setName("Salutation");
        salutationItem.setValue(letter.getSalutation().toString());
        contentItems.getItem().add(salutationItem);
        if (letter.getLetterHead().equals(LetterHead.FINALIZATION_OF_AUDIT)) {
            sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item auditPeriodItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
            auditPeriodItem.setName("AuditPeriod");
            auditPeriodItem.setValue(String.valueOf(letter.getSuspiciousCase().getAuditPlan().getAuditPeriod()));
            contentItems.getItem().add(auditPeriodItem);
        }
        if (letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {

            sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item amountForfeitureItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
            amountForfeitureItem.setName("AmountForfeiture");
            amountForfeitureItem.setValue(String.valueOf(letter.getSuspiciousCase().getAuditPlan().getAuditReport().getRevenueLiability().getForfieture()));
            contentItems.getItem().add(amountForfeitureItem);
        }
        if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND) && !letter.getLetterHead().equals(LetterHead.IMPOSITION_OF_PENALTY) && !letter.getLetterHead().equals(LetterHead.NOTICE_OF_INTENT) && !letter.getLetterHead().equals(LetterHead.FINALIZATION_OF_AUDIT)) {

            sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item tradingNameItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
            tradingNameItem.setName("TradingName");
            tradingNameItem.setValue(letter.getSuspiciousCase().getIbrData().getRegistrationName());
            contentItems.getItem().add(tradingNameItem);
        }
        if (letter.getLetterHead().equals(LetterHead.REQUEST_FOR_DOCUMENTATION) || letter.getLetterHead().equals(LetterHead.NOTIFICATION_OF_AUDIT_DESK) || letter.getLetterHead().equals(LetterHead.NOTIFICATION_OF_AUDIT_FIELD) || letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {

            sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item ammountDaysDueItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
            ammountDaysDueItem.setName("AmountDaysDue");
            ammountDaysDueItem.setValue(letter.getDaysDue());
            contentItems.getItem().add(ammountDaysDueItem);
            if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS) && !letter.getLetterHead().equals(LetterHead.NOTICE_OF_INTENT) && !letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND) && !letter.getLetterHead().equals(LetterHead.NOTIFICATION_OF_AUDIT_FIELD) && !letter.getLetterHead().equals(LetterHead.NOTIFICATION_OF_AUDIT_DESK)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item customCodeItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                customCodeItem.setName("CustomsCode");
                if (letter.getId() != null) {

                    customCodeItem.setValue(letter.getSuspiciousCase().getIbrData().getIbrCustomsNumber());

                }
                contentItems.getItem().add(customCodeItem);
            }
        }
        if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS) && !letter.getLetterHead().equals(LetterHead.REQUEST_FOR_DOCUMENTATION) && !letter.getLetterHead().equals(LetterHead.NOTIFICATION_OF_AUDIT_DESK) && !letter.getLetterHead().equals(LetterHead.NOTIFICATION_OF_AUDIT_FIELD) && !letter.getLetterHead().equals(LetterHead.FINALIZATION_OF_AUDIT)) {
            if (!letter.getLetterHead().equals(LetterHead.NOTICE_OF_INTENT)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item noiDateItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                noiDateItem.setName("NOIDate");
                noiDateItem.setValue(DateUtil.convertStringToDate(letter.getNoticeOfIntentDate()));
                contentItems.getItem().add(noiDateItem);
            }

            if (letter.getLetterHead().equals(LetterHead.NOTICE_OF_INTENT)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item notificatioOfAuditDateItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                notificatioOfAuditDateItem.setName("NotificationAuditDate");
                notificatioOfAuditDateItem.setValue(DateUtil.convertStringToDate(letter.getNotificationOfAuditDate()));
                contentItems.getItem().add(notificatioOfAuditDateItem);

                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item actionAndCircumstanceItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                actionAndCircumstanceItem.setName("Action or Circumstance of contravention detection");
                actionAndCircumstanceItem.setValue(letter.getCircumstanceOfContravention());
                contentItems.getItem().add(actionAndCircumstanceItem);
            }
            if (!letter.getLetterHead().equals(LetterHead.IMPOSITION_OF_PENALTY)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item goodsDealtWithItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                goodsDealtWithItem.setName("GoodsDealtWith");
                if (letter.isGoodsDealt()) {
                    goodsDealtWithItem.setValue("<br></br>");
                } else {
                    goodsDealtWithItem.setValue("As the goods cannot readily be found, it is the intention of the Commissioner to demand from you in terms of Section 88(2)(a) of the Act an amount equal to the value for duty purposes of such goods, plus unpaid duties thereon. An amount of R{Amount} is hereby demanded in respect of forfeiture.");
                }
                contentItems.getItem().add(goodsDealtWithItem);
            }
            if (!letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND) && !letter.getLetterHead().equals(LetterHead.NOTICE_OF_INTENT)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item partiallyItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                partiallyItem.setName("partiallyfully");
                partiallyItem.setValue(letter.getPartiallyPayment().toString());
                contentItems.getItem().add(partiallyItem);
            }
            if (letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item additionalExplanationItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                additionalExplanationItem.setName("Additional Explanation");
                additionalExplanationItem.setValue(letter.getAdditionalExplanation());
                contentItems.getItem().add(additionalExplanationItem);
            }
            if (letter.getLetterHead().equals(LetterHead.IMPOSITION_OF_PENALTY) || letter.getLetterHead().equals(LetterHead.NOTICE_OF_INTENT)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item totalAItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                totalAItem.setName("TotalA");
                for (AuditReportOffencePenalty offence : letter.getSuspiciousCase().getAuditPlan().getAuditReport().getAuditReportOffencePenalties()) {
                    totalAItem.setValue(String.valueOf(offence.getOffencePenalty().getSubOffenceOrPenaltyAmount()));
                }

                contentItems.getItem().add(totalAItem);
            }
            if (letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item totalAItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                totalAItem.setName("TotalA");
                for (DutyVat dutyVat : letter.getDutyVats()) {
                    totalAItem.setValue(String.valueOf(dutyVat.getAmount()));
                }

                contentItems.getItem().add(totalAItem);
            }
            if (letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS)) {
                for (DutyVat dutyVat : letter.getDutyVats()) {
                    sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item totalBItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                    totalBItem.setName("TotalB");
                    totalBItem.setValue(String.valueOf(dutyVat.getSubAmount()));
                    contentItems.getItem().add(totalBItem);
                }
            }
            if (letter.getLetterHead().equals(LetterHead.LETTER_OF_DEMAND) || letter.getLetterHead().equals(LetterHead.IMPOSITION_OF_PENALTY)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item repDateItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                repDateItem.setName("RepDate");
                repDateItem.setValue(DateUtil.convertStringToDate(letter.getRepresentationDate()));
                contentItems.getItem().add(repDateItem);
            }
        }

        if (letter.getLetterHead()
                .equals(LetterHead.LETTER_OF_FINDINGS) || letter.getLetterHead().equals(LetterHead.NOTICE_OF_INTENT)) {
            if (letter.getLetterHead().equals(LetterHead.LETTER_OF_FINDINGS)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item amountDaysToContactItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                amountDaysToContactItem.setName("AmountDaystoContact");
                amountDaysToContactItem.setValue(letter.getDaysDue());
                contentItems.getItem().add(amountDaysToContactItem);
            }

            if (letter.getLetterHead().equals(LetterHead.NOTICE_OF_INTENT)) {
                sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item amountDaysItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
                amountDaysItem.setName("AmountDays");
                amountDaysItem.setValue(letter.getDaysDue());
                contentItems.getItem().add(amountDaysItem);
            }

            sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item totalVatDueItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
            totalVatDueItem.setName("TotalVatDuty");
            totalVatDueItem.setValue(String.valueOf(letter.getSuspiciousCase().getAuditPlan().getAuditReport().getTotalLiability().getTotalVat()));
            contentItems.getItem().add(totalVatDueItem);
        }

        if (letter.getLetterHead().equals(LetterHead.NOTIFICATION_OF_AUDIT_DESK) || letter.getLetterHead().equals(LetterHead.NOTIFICATION_OF_AUDIT_FIELD)) {
            sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item auditTypeItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
            auditTypeItem.setName("AUDIT TYPE");
            auditTypeItem.setValue(letter.getSuspiciousCase().getAuditPlan().getAuditType().toString() + " Audit");
            contentItems.getItem().add(auditTypeItem);

            sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item auditorNameItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
            sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item programItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.ContentItems.Item();
            StringBuilder programList = new StringBuilder();
            auditorNameItem.setName("AuditorName");
            StringBuilder auditorNameList = new StringBuilder();
            programItem.setName("Program");
            for (Auditor letterAuditor : letter.getAuditors()) {
                auditorNameList.append(".");
                auditorNameList.append(letterAuditor.getName());
                auditorNameList.append("\n");
            }
            auditorNameItem.setValue(auditorNameList.toString());
            contentItems.getItem().add(auditorNameItem);

            for (Program program : letter.getPrograms()) {

                programList.append(".");
                programList.append(program.getDescription());
                programList.append("\n");
            }
            programItem.setValue(programList.toString());
            contentItems.getItem().add(programItem);

        }
        return contentItems;
    }

    private Footer createFooter(Letter letter) {
        Footer footer = new Footer();
        sars.pca.app.gen.dto.req.GeneratePDFDocument.Footer.Item footerItem = new sars.pca.app.gen.dto.req.GeneratePDFDocument.Footer.Item();
        footerItem.setName("LETTER_ID");
        switch (letter.getLetterHead()) {
            case IMPOSITION_OF_PENALTY:
                footerItem.setValue("PCA_CI_PenaltyLetter");
                break;
            case LETTER_OF_FINDINGS:
                footerItem.setValue("PCA_CI_Audit_Findings_Letter");
                break;
            case LETTER_OF_DEMAND:
                footerItem.setValue("PCA_CI_Letter_of_Demand");
                break;
            case NOTICE_OF_INTENT:
                footerItem.setValue("PCA_CI_Notice_of_Intent_Letter");
                break;
            case NOTIFICATION_OF_AUDIT_DESK:
                footerItem.setValue("PCA_CI_Notification_of_Audit_Letter");
                break;
            case NOTIFICATION_OF_AUDIT_FIELD:
                footerItem.setValue("PCA_CI_Notification_of_Audit_Letter");
                break;
            case REQUEST_FOR_DOCUMENTATION:
                footerItem.setValue("PCA_CS_Request_for_Relevant_Material_Letter");
                break;
            case FINALIZATION_OF_AUDIT:
                footerItem.setValue("PCA_CI_Finalisation_of_Audit_with_no_Adjustment_Letter");
                break;
            default:
                break;
        }
        footer.setItem(footerItem);
        return footer;
    }
}
