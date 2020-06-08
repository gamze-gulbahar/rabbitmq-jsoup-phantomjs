import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class PhantomJSCrawler extends Thread{

    public static void takeSnapShot(WebDriver driver,String fileWithPath) throws Exception{

        TakesScreenshot scrShot =((TakesScreenshot)driver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile=new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public void run () {
        try{
            String fileWithPath = "screenshots";
            String msg = Consumer.message;
            String[] message = msg.split(" ");
            String url = message[0];

            File file = new File("/Users/gamze/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs");
            System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
            WebDriver driver = new PhantomJSDriver();
            driver.get(url);
            takeSnapShot(driver,fileWithPath+ "/" + currentThread().getId());

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
