package util;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportScreenshotUtil {

    public static void captureFullReportScreenshot(
            String reportPath,
            String outputFileName) throws Exception {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(reportPath);

        Thread.sleep(2000);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        long pageHeight = (long) js.executeScript(
                "return document.body.scrollHeight");

        int viewportHeight =
                driver.manage().window().getSize().getHeight();

        List<BufferedImage> images = new ArrayList<>();
        int scrollY = 0;

        while (scrollY < pageHeight) {

            js.executeScript("window.scrollTo(0, arguments[0])", scrollY);
            Thread.sleep(500);

            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            images.add(ImageIO.read(src));
            scrollY += viewportHeight;
        }

        BufferedImage finalImage = stitchImages(images);
        ImageIO.write(finalImage, "png",
                new File(outputFileName));

        driver.quit();
    }

    private static BufferedImage stitchImages(
            List<BufferedImage> images) {

        int width = images.get(0).getWidth();
        int totalHeight = 0;

        for (BufferedImage img : images) {
            totalHeight += img.getHeight();
        }

        BufferedImage stitchedImage =
                new BufferedImage(width, totalHeight,
                        BufferedImage.TYPE_INT_RGB);

        Graphics g = stitchedImage.getGraphics();
        int currentHeight = 0;

        for (BufferedImage img : images) {
            g.drawImage(img, 0, currentHeight, null);
            currentHeight += img.getHeight();
        }

        g.dispose();
        return stitchedImage;
    }
}
