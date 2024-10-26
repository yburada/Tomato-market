package com.market.tomato.utils;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.market.tomato.model.FarmerBillingDetails;
import com.market.tomato.model.allotment.Allotment;
import com.market.tomato.model.bidder.Bidder;
import com.market.tomato.model.save.Vehicle;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PDFUtils {

    public static void generateLotDetails(Document document, Paragraph newLine, float[] reverse_width, Allotment allotment, Table divider, Integer price) {

        Table lotDetails = new Table(reverse_width);
        lotDetails.addCell(getLeftDetails("Lot Number :").setBold());
        lotDetails.addCell(getLeftDetails(String.valueOf(allotment.getLotNumber())));

        lotDetails.addCell(getLeftDetails("Farmer Name :").setBold());
        lotDetails.addCell(getLeftDetails(allotment.getFarmer().getName()));

        lotDetails.addCell(getLeftDetails("Farmer Phone Number :").setBold());
        lotDetails.addCell(getLeftDetails(String.valueOf(allotment.getFarmer().getContactNumber())));

        lotDetails.addCell(getLeftDetails("Farmer Address :").setBold());
        lotDetails.addCell(getLeftDetails(allotment.getFarmer().getAddress()));

        lotDetails.addCell(getLeftDetails("Number of Boxes :").setBold());
        lotDetails.addCell(getLeftDetails(String.valueOf(allotment.getNumberOfBoxes())));

        lotDetails.addCell(getLeftDetails("Price per Box :").setBold());
        lotDetails.addCell(getLeftDetails(String.valueOf(price)));

        lotDetails.addCell(getLeftDetails("Price :").setBold());
        lotDetails.addCell(getLeftDetails(String.valueOf(allotment.getNumberOfBoxes()*price)));

        document.add(lotDetails);
        document.add(newLine);

        document.add(divider);

    }

    public static void generateFarmerDetails(Document document, float[] reverse_width, FarmerBillingDetails farmerBillingDetails, Integer less, Bidder bidder) {

        Table farmerDetails = new Table(reverse_width);
        farmerDetails.addCell(getLeftDetails("Billing Number :").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(farmerBillingDetails.getBillingId())));

        farmerDetails.addCell(getLeftDetails("Lot Number :").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(farmerBillingDetails.getAllotment().getLotNumber())));

        farmerDetails.addCell(getLeftDetails("Farmer Name :").setBold());
        farmerDetails.addCell(getLeftDetails(farmerBillingDetails.getAllotment().getFarmer().getName()));

        farmerDetails.addCell(getLeftDetails("Number of Boxes :").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(farmerBillingDetails.getAllotment().getNumberOfBoxes())));

        farmerDetails.addCell(getLeftDetails("Less :").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(less)));

        Integer totalBoxes = farmerBillingDetails.getAllotment().getNumberOfBoxes() - less;
        farmerDetails.addCell(getLeftDetails("Total after less :").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(totalBoxes)));

        farmerDetails.addCell(getLeftDetails("Price per box:").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(bidder.getPrice())));

        Long Amount = ((long) bidder.getPrice() * (farmerBillingDetails.getAllotment().getNumberOfBoxes() - less));
        farmerDetails.addCell(getLeftDetails("Amount:").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(Amount)));

        farmerDetails.addCell(getLeftDetails("Deductions:").setBold());
        farmerDetails.addCell(getLeftDetails(""));

        Long transport =0l;
        farmerDetails.addCell(getLeftDetails("Transport:").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(transport)));

        Long others = Amount/10;
        farmerDetails.addCell(getLeftDetails("Others:").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(others)));

        Long boxRent = (totalBoxes * 3L);
        farmerDetails.addCell(getLeftDetails("Box Rent:").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(boxRent)));

        farmerDetails.addCell(getLeftDetails("Hamali:").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf(totalBoxes)));

        farmerDetails.addCell(getLeftDetails("Total Amount:").setBold());
        farmerDetails.addCell(getLeftDetails(String.valueOf((Amount - transport - others - totalBoxes - boxRent))));

        document.add(farmerDetails);
    }

    public static Cell getCell10fLeft(String textValue) {
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT).setFontSize(10f);
    }

    public static Cell getLeftDetails(String textValue) {
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    public static Cell getRightDetails(String textValue) {
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    public static Document pdfConfig(String fineName) throws FileNotFoundException {
        PdfWriter writer  = new PdfWriter(fineName);
        PdfDocument pdfdocument = new PdfDocument(writer);
        pdfdocument.setDefaultPageSize(PageSize.A4);
        return new Document(pdfdocument);
    }

    public static Table generateMainHeader(float[] col_width, Vehicle vehicle, Document document, Paragraph newLine, float[] fullBorder) {
        Table table = new Table(col_width);
        Table innerTable = new Table(new float[]{col_width[0]/2, col_width[0]/2});
        innerTable.addCell(new Cell().add("ENTITY_NAME").setBorder(Border.NO_BORDER).setBold().setFontSize(20f));
        innerTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        innerTable.addCell(new Cell().add("Entity Address").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(innerTable).setBorder(Border.NO_BORDER));
        Table table1 = new Table(new float[]{(float) 285.0 / 2, (float) 285.0 / 2});
        table1.addCell(new Cell().add("Transport Name").setBold().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(": "+ vehicle.getTransportName()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add("Vehicle Number").setBold().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(": "+ vehicle.getVehicleNumber()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add("Date & Time").setBold().setBorder(Border.NO_BORDER));
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        table1.addCell(new Cell().add(": "+dateTime.format(formatter)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(table1).setBorder(Border.NO_BORDER));

        document.add(table);
        document.add(newLine);

        Border greyBorder = new SolidBorder(Color.GRAY, 1.5f);
        Table divider = new Table(fullBorder);
        divider.setBorder(greyBorder);

        document.add(divider);
        document.add(newLine);
        return divider;
    }

    public static Table generateMainHeader(float[] col_width, FarmerBillingDetails farmerBillingDetails, Document document, Paragraph newLine, float[] fullBorder) {
        Table table = new Table(col_width);
        Table innerTable = new Table(new float[]{col_width[0]/2, col_width[0]/2});
        innerTable.addCell(new Cell().add("ENTITY_NAME").setBorder(Border.NO_BORDER).setBold().setFontSize(20f));
        innerTable.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        innerTable.addCell(new Cell().add("Entity Address").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(innerTable).setBorder(Border.NO_BORDER));
        Table table1 = new Table(new float[]{(float) 285.0 / 2, (float) 285.0 / 2});
        table1.addCell(new Cell().add("Transport Name").setBold().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(": "+ farmerBillingDetails.getAllotment().getVehicle().getTransportName()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add("Vehicle Number").setBold().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(": "+ farmerBillingDetails.getAllotment().getVehicle().getVehicleNumber()).setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add("Date & Time").setBold().setBorder(Border.NO_BORDER));
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        table1.addCell(new Cell().add(": "+dateTime.format(formatter)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(table1).setBorder(Border.NO_BORDER));

        document.add(table);
        document.add(newLine);

        Border greyBorder = new SolidBorder(Color.GRAY, 1.5f);
        Table divider = new Table(fullBorder);
        divider.setBorder(greyBorder);

        document.add(divider);
        document.add(newLine);
        return divider;
    }

}
