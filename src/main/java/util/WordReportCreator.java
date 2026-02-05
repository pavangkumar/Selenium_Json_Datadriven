package util;


import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class WordReportCreator {

    public static void create() throws Exception {

        XWPFDocument doc = new XWPFDocument();

        XWPFParagraph titlePara = doc.createParagraph();
        titlePara.setSpacingAfter(200);

        XWPFRun title = titlePara.createRun();
        title.setText("Automation Execution Report");
        title.setBold(true);
        title.setFontSize(16);

        byte[] image = Files.readAllBytes(
                Path.of("report.png"));

        XWPFParagraph imgPara = doc.createParagraph();
        imgPara.setSpacingBefore(100);
        imgPara.setSpacingAfter(0);

        XWPFRun imgRun = imgPara.createRun();
        imgRun.addPicture(
                new ByteArrayInputStream(image),
                Document.PICTURE_TYPE_PNG,
                "report.png",
                Units.toEMU(450),
                Units.toEMU(300)
        );

        FileOutputStream out =
                new FileOutputStream("Automation_Report.docx");

        doc.write(out);
        doc.close();
        out.close();
    }

}
