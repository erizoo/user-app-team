package by.javateam.pdf;

import by.javateam.model.Email;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by Valera.
 */
@Component
public class FeedbackPdfList extends AbstractPdfView {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy");

    @Override
    @SuppressWarnings("unchecked")
    protected void buildPdfDocument(final Map model, final Document document, final PdfWriter pdfWriter,
                                    final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        List<Email> emails = (List<Email>) model.get("listEmails");
        final int documentNameFontSize = 25;
        final int documentDateFontSize = 12;

        response.setHeader("Content-Disposition", "inline; filename=" + "feedback_" + dateTimeFormatter.print(new LocalDate()) + ".pdf");

        Paragraph documentName = new Paragraph("List of feedback", new Font(Font.TIMES_ROMAN, documentNameFontSize, Font.BOLD));
        Paragraph documentDate = new Paragraph(dateTimeFormatter.print(new LocalDate()), new Font(Font.TIMES_ROMAN, documentDateFontSize));

        documentName.setAlignment(Element.ALIGN_CENTER);
        documentDate.setAlignment(Element.ALIGN_CENTER);
        document.add(documentName);
        document.add(documentDate);

        Table table = new Table(5);
        table.setBorderWidth(1);
        table.setBorderColor(new Color(0, 0, 0));
        table.setPadding(5);
        table.setWidth(100);
        String[] headers = {"#", "Subject", "Body", "E-mail", "Date"};
        for (String header: headers) {
            Cell cell = new Cell(header);
            cell.setHeader(true);
            table.addCell(cell);
        }
        table.endHeaders();

        ListIterator<Email> iterator = emails.listIterator();

        while (iterator.hasNext()) {
            Email email = iterator.next();
            table.addCell(Integer.toString(iterator.nextIndex()));
            table.addCell(email.getSubject());
            table.addCell(email.getBody());
            table.addCell(email.getFrom());
            LocalDateTime date = new LocalDateTime(email.getCreatedTimestamp());
            table.addCell(dateTimeFormatter.print(date));
        }
        document.add(table);
    }

    @Override
    protected void buildPdfMetadata(final Map<String, Object> model, final Document document, final HttpServletRequest request) {
        document.addTitle("feedback_" + dateTimeFormatter.print(new LocalDate()) + ".pdf");
    }
}
