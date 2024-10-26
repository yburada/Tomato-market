package com.market.tomato.service;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DottedBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.market.tomato.model.FarmerBillingDetails;
import com.market.tomato.model.bidder.Bidder;
import com.market.tomato.model.save.Vehicle;
import com.market.tomato.repository.allotment.AllotmentRepository;
import com.market.tomato.repository.bidder.BidderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.market.tomato.utils.PDFUtils;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class PDFGenerator {

    private  final  float col_two = 285f;
    private  final  float col_one = col_two + 150f;
    private  final  float col_five = 150f;
    private  final  float col_four = col_five+285f;
    private  final  float[] col_width = {col_one, col_two};
    private  final  float[] reverse_width = {col_five, col_four};
    private  final  float col_three = 190f;
    private  final  float[] fullBorder = {col_three*3};

    @Autowired
    private BidderRepository bidderRepository;
    @Autowired
    private AllotmentRepository allotmentRepository;

    public PDFGenerator(){}

    public byte[] bidderPdfGenerator(Vehicle vehicle) {
        ByteArrayOutputStream out =new ByteArrayOutputStream();
        try{
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            Paragraph newLine = new Paragraph("\n");
            Table divider = generateHeader(col_width, vehicle, document, newLine, fullBorder);
            allotmentRepository.findByVehicle(LocalDate.now(), vehicle).ifPresent(allotments -> allotments.forEach(allotment -> {
                Bidder byLotNumber = bidderRepository.findByLotNumber(LocalDate.now(), allotment.getLotNumber()).orElse(null);
                if(byLotNumber != null){
                    document.add(newLine);
                    PDFUtils.generateLotDetails(document, newLine, reverse_width, allotment, divider, byLotNumber.getPrice());
                }
            }));
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public byte[] farmerPdfGenerator(FarmerBillingDetails farmerBillingDetails, Integer less) {
        ByteArrayOutputStream out =new ByteArrayOutputStream();
        try{
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);
            Paragraph newLine = new Paragraph("\n");
            generateHeader(col_width, farmerBillingDetails, document, newLine, fullBorder);
            Optional<Bidder> byLotNumber = bidderRepository.findByLotNumber(LocalDate.now(), farmerBillingDetails.getAllotment().getLotNumber());
            byLotNumber.ifPresent(bidder -> PDFUtils.generateFarmerDetails(document, reverse_width, farmerBillingDetails, less, bidder));
            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Table generateHeader(float[] col_width, Vehicle vehicle, Document document, Paragraph newLine, float[] fullBorder) {
        Table divider = PDFUtils.generateMainHeader(col_width, vehicle, document, newLine, fullBorder);

        Border greyDottedBorder = new DottedBorder(Color.GRAY, 1.5f);
        divider.setBorder(greyDottedBorder);

        Table lotHeader = new Table(col_width);
        lotHeader.addCell(PDFUtils.getLeftDetails("Bidding Details").setBold().setFontSize(15f));
        lotHeader.addCell(PDFUtils.getRightDetails(""));
        document.add(lotHeader);
        document.add(newLine);
        document.add(divider);
        return divider;
    }

    private static void generateHeader(float[] col_width, FarmerBillingDetails farmerBillingDetails, Document document, Paragraph newLine, float[] fullBorder) {
        Table divider = PDFUtils.generateMainHeader(col_width, farmerBillingDetails, document, newLine, fullBorder);
        Border greyDottedBorder = new DottedBorder(Color.GRAY, 1.5f);
        divider.setBorder(greyDottedBorder);

        Table lotHeader = new Table(col_width);
        lotHeader.addCell(PDFUtils.getLeftDetails("Farmer Billing Details").setBold().setFontSize(15f));
        lotHeader.addCell(PDFUtils.getRightDetails(""));
        document.add(lotHeader);
        document.add(newLine);
        document.add(divider);
    }


}
