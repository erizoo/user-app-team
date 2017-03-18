package by.javateam.pdf;

import by.javateam.model.User;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 01.03.2017.
 */
@Component
public class UserPdfList extends AbstractPdfView {

    private DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy");

    @Override
    @SuppressWarnings("unchecked")
    protected void buildPdfDocument(final Map model, final Document doc, final PdfWriter writer,
                                    final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
        List<User> users = (List<User>) model.get("users");
        final int documentNameFontSize = 25;
        final int documentDateFontSize = 12;

        resp.setHeader("Content-Disposition", "inline; filename=" + "users_" + dateTimeFormatter.print(new LocalDate()) + ".pdf");

        Paragraph documentName = new Paragraph("List of users", new Font(Font.TIMES_ROMAN, documentNameFontSize, Font.BOLD));
        Paragraph documentDate = new Paragraph(dateTimeFormatter.print(new LocalDate()), new Font(Font.TIMES_ROMAN, documentDateFontSize));

        documentName.setAlignment(Element.ALIGN_CENTER);
        documentDate.setAlignment(Element.ALIGN_CENTER);
        doc.add(documentName);
        doc.add(documentDate);

        com.lowagie.text.List pdfUserList = new com.lowagie.text.List(true, 20);
        for (User user: users) {
            pdfUserList.add(new ListItem(user.getFirstName() + " " + user.getLastName()));
        }

        doc.add(pdfUserList);
    }

    @Override
    protected void buildPdfMetadata(final Map<String, Object> model, final Document document, final HttpServletRequest request) {
        document.addTitle("users_" + dateTimeFormatter.print(new LocalDate()) + ".pdf");
    }
}
