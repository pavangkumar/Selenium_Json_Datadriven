package tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import util.HtmlReportScreenshot;
import util.WordReportCreator;

public class TestScreenShot {

    @AfterSuite
    public void AfterSuite() throws Exception {
        HtmlReportScreenshot.capture();
        WordReportCreator.create();
    }
}
