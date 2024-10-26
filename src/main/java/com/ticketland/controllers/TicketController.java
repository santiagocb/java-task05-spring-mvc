package com.ticketland.controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.ticketland.entities.Ticket;
import com.ticketland.entities.UserAccount;
import com.ticketland.facades.BookingFacade;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
public class TicketController {

    private final BookingFacade bookingFacade;

    public TicketController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    // Display the form to get booked tickets for a user
    @GetMapping("/tickets/search")
    public String showBookedTicketsForm() {
        return "searchTickets";
    }

    // Handle the search for booked tickets
    @GetMapping("/tickets/search/user")
    public String getBookedTickets(@RequestParam(value = "userId") String userId, Model model) {
        if (userId != null ) {
            UserAccount user;
            try {
                user = bookingFacade.getUserAccount(userId);
            } catch (EntityNotFoundException e) {
                model.addAttribute("message", "User doesn't exists.");
                return "searchTickets";
            }
            List<Ticket> tickets = bookingFacade.getTicketsByUserAccountId(userId);

            model.addAttribute("userId", userId);
            model.addAttribute("tickets", tickets);
            model.addAttribute("userName", user.getUser().getName());
            return "searchTickets";
        } else {
            model.addAttribute("tickets", null);
            model.addAttribute("userName", "");
            return "searchTickets";
        }
    }


    @GetMapping(value = "/tickets/search/user", params = "downloadPdf=true")
    public ResponseEntity<?> downloadTickets(@RequestParam(value = "userId") String userId) {
        UserAccount user = bookingFacade.getUserAccount(userId);
        List<Ticket> tickets = bookingFacade.getBookedTickets(user, 100, 1);;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=tickets.pdf");
        return new ResponseEntity<>(generateTicketsPdf(tickets, user), headers, HttpStatus.OK);
    }

    private byte[] generateTicketsPdf(List<Ticket> tickets, UserAccount user) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Booked Tickets for: " + user.getUser().getName())
                    .setBold()
                    .setFontSize(18)
                    .setMarginBottom(10));

            Table table = new Table(4);
            table.addCell(new Cell().add(new Paragraph("Event ID")).setBold());
            table.addCell(new Cell().add(new Paragraph("Event Name")).setBold());
            table.addCell(new Cell().add(new Paragraph("Place")).setBold());
            table.addCell(new Cell().add(new Paragraph("Date")).setBold());

            for (Ticket ticket : tickets) {
                table.addCell(new Cell().add(new Paragraph(ticket.getEvent().getId())));
                table.addCell(new Cell().add(new Paragraph(ticket.getEvent().getName())));
                table.addCell(new Cell().add(new Paragraph(ticket.getEvent().getPlace())));
                table.addCell(new Cell().add(new Paragraph(ticket.getEvent().getDate().toString())));
            }

            document.add(table);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputStream.toByteArray();
    }
}
