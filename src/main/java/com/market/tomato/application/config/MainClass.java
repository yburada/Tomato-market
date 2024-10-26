package com.market.tomato.application.config;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DottedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.market.tomato.model.allotment.Allotment;
import com.market.tomato.model.save.Farmer;
import com.market.tomato.model.save.Vehicle;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MainClass {

    public static void main(String[] args) {
        Vehicle vehicle = new Vehicle("ABCDEF", "ABC", 9123433L, "ABC");
        Farmer farmer = new Farmer("abc", 12345L,"CPP");
        Allotment allotment = new Allotment(vehicle, farmer, 12344,12344, LocalDateTime.of(LocalDate.of(1999,12,02), LocalTime.of(12,13)));
        String fileName = "bidder.pdf";
        float col_two = 285f;
        float col_one = col_two + 150f;
        float col_five = 150f;
        float col_four = col_five+285f;
        float[] col_width = {col_one, col_two};
        float[] reverse_width = {col_five, col_four};
        float col_three = 190f;
        float[] fullBorder = {col_three*3};
        try (Document document = pdfConfig(fileName)) {
            Paragraph newLine = new Paragraph("\n");
            Table divider = generateHeader(col_width, allotment, col_two, document, newLine, fullBorder);
            generateLotDetails(document, newLine, reverse_width, allotment, divider);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Table generateHeader(float[] col_width, Allotment allotment, float col_two, Document document, Paragraph newLine, float[] fullBorder) {
        Table table = new Table(col_width);
        table.addCell(new Cell().add(allotment.getVehicle().getVehicleNumber()).setBorder(Border.NO_BORDER).setBold().setFontSize(20f));
        Table table1 = new Table(new float[]{col_two / 2, col_two / 2});
        table1.addCell(new Cell().add("Transport Name").setBold().setBorder(Border.NO_BORDER));
        table1.addCell(new Cell().add(": "+ allotment.getVehicle().getTransportName()).setBorder(Border.NO_BORDER));
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

        Border greyDottedBorder = new DottedBorder(Color.GRAY, 1.5f);
        divider.setBorder(greyDottedBorder);

        Table lotHeader = new Table(col_width);
        lotHeader.addCell(getLeftDetails("Bidding Details").setBold().setFontSize(15f));
        lotHeader.addCell(getRightDetails(""));
        document.add(lotHeader);
        document.add(newLine);
        document.add(divider);
        return divider;
    }

    private static void generateLotDetails(Document document, Paragraph newLine, float[] reverse_width, Allotment allotment, Table divider) {
        document.add(newLine);

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

        document.add(lotDetails);
        document.add(newLine);

        document.add(divider);
        document.add(newLine);
    }

    private static Cell getCell10fLeft(String textValue) {
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT).setFontSize(10f);
    }

    private static Cell getLeftDetails(String textValue) {
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    private static Cell getRightDetails(String textValue) {
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    private static Document pdfConfig(String fineName) throws FileNotFoundException {
        PdfWriter writer  = new PdfWriter(fineName);
        PdfDocument pdfdocument = new PdfDocument(writer);
        pdfdocument.setDefaultPageSize(PageSize.A4);
        return new Document(pdfdocument);
    }
}
