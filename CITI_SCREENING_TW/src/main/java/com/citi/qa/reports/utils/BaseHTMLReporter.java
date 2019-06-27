package com.citi.qa.reports.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.uncommons.reportng.HTMLReporter;

public class BaseHTMLReporter
    extends HTMLReporter
    implements ITestListener
{
    public static final String DRIVER_ATTRIBUTE = "driver";

    private static final String UTILS_KEY = "utils";

    private static final ReportUtils REPORT_UTILS = new ReportUtils();

    @Override
    protected VelocityContext createContext()
    {
        final VelocityContext context = super.createContext();
        context.put( BaseHTMLReporter.UTILS_KEY, BaseHTMLReporter.REPORT_UTILS );
        return context;
    }

    private void createScreenshot( final ITestResult result, final WebDriver driver )
    {
        final DateFormat timeFormat = new SimpleDateFormat( "MM.dd.yyyy HH-mm-ss" );
        final String fileName = result.getMethod().getMethodName() + "_" + timeFormat.format( new Date() );

        try
        {
            File scrFile;

            if( driver.getClass().equals( RemoteWebDriver.class ) )
            {
                scrFile = ( (TakesScreenshot) new Augmenter().augment( driver ) ).getScreenshotAs( OutputType.FILE );
            }
            else
            {
                scrFile = ( (TakesScreenshot) driver ).getScreenshotAs( OutputType.FILE );
            }

            String outputDir = result.getTestContext().getOutputDirectory();
            outputDir = outputDir.substring( 0, outputDir.lastIndexOf( '\\' ) ) + "\\html";

            final File saved = new File( outputDir, fileName + ".png" );
            FileUtils.copyFile( scrFile, saved );

            result.setAttribute( "screenshot", saved.getName() );
        }
        catch( IOException e )
        {
            result.setAttribute( "reportGeneratingException", e );
        }

        result.setAttribute( "screenshotURL", driver.getCurrentUrl() );
        result.removeAttribute( BaseHTMLReporter.DRIVER_ATTRIBUTE );
    }

    /*
     * (non-Javadoc)
     * @see org.testng.ITestListener#onTestStart(org.testng.ITestResult)
     */
    @Override
    public void onTestStart( ITestResult result )
    {

    }

    /*
     * (non-Javadoc)
     * @see org.testng.ITestListener#onTestSuccess(org.testng.ITestResult)
     */
    @Override
    public void onTestSuccess( ITestResult result )
    {
        final WebDriver driver = (WebDriver) result.getTestContext().getAttribute( BaseHTMLReporter.DRIVER_ATTRIBUTE );

        if( driver != null )
        {
            createScreenshot( result, driver );
            driver.quit();
        }

    }

    /*
     * (non-Javadoc)
     * @see org.testng.ITestListener#onTestFailure(org.testng.ITestResult)
     */
    @Override
    public void onTestFailure( ITestResult result )
    {
        final WebDriver driver = (WebDriver) result.getTestContext().getAttribute( BaseHTMLReporter.DRIVER_ATTRIBUTE );

        if( driver != null )
        {
            createScreenshot( result, driver );
            driver.quit();
        }

    }

    /*
     * (non-Javadoc)
     * @see org.testng.ITestListener#onTestSkipped(org.testng.ITestResult)
     */
    @Override
    public void onTestSkipped( ITestResult result )
    {

    }

    /*
     * (non-Javadoc)
     * @see org.testng.ITestListener#onTestFailedButWithinSuccessPercentage(org.testng.ITestResult)
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage( ITestResult result )
    {

    }

    /*
     * (non-Javadoc)
     * @see org.testng.ITestListener#onStart(org.testng.ITestContext)
     */
    @Override
    public void onStart( ITestContext context )
    {

    }

    /*
     * (non-Javadoc)
     * @see org.testng.ITestListener#onFinish(org.testng.ITestContext)
     */
    @Override
    public void onFinish( ITestContext context )
    {

    }

}
