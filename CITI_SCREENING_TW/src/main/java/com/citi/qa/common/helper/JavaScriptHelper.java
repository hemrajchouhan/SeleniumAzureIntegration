/**
 * rsr Aug 6, 2016
 */
package com.citi.qa.common.helper;

import com.citi.qa.core.base.PageObject;
import com.citi.qa.helper.logger.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author rsr Aug 6, 2016
 */
public class JavaScriptHelper
    extends PageObject
{

    // private WebDriver driver;

    private Logger oLog = LoggerHelper.getLogger( JavaScriptHelper.class );

    public JavaScriptHelper( WebDriver driver )
    {
        this.webDriver = driver;
        oLog.debug( "JavaScriptHelper : " + this.webDriver.hashCode() );
    }

    public Object executeScript( String script )
    {
        JavascriptExecutor exe = (JavascriptExecutor) webDriver;
        oLog.info( script );
        return exe.executeScript( script );
    }

    public Object executeScript( String script, Object... args )
    {
        JavascriptExecutor exe = (JavascriptExecutor) webDriver;
        oLog.info( script );
        return exe.executeScript( script, args );
    }

    public void scrollToElemet( WebElement element )
    {
        executeScript( "window.scrollTo(arguments[0],arguments[1])", element.getLocation().x, element.getLocation().y );
        oLog.info( element );
    }

    public void scrollToElemet( By locator )
    {
        scrollToElemet( webDriver.findElement( locator ) );
        oLog.info( locator );
    }

    public void scrollToElemetAndClick( By locator )
    {
        WebElement element = webDriver.findElement( locator );
        scrollToElemet( element );
        element.click();
        oLog.info( locator );
    }

    public void scrollToElemetAndClick( WebElement element )
    {
        scrollToElemet( element );
        element.click();
        oLog.info( element );
    }

    public void scrollIntoView( WebElement element )
    {
        executeScript( "arguments[0].scrollIntoView()", element );
        oLog.info( element );
    }

    public void scrollIntoView( By locator )
    {
        scrollIntoView( webDriver.findElement( locator ) );
        oLog.info( locator );
    }

    public void scrollIntoViewAndClick( By locator )
    {
        WebElement element = webDriver.findElement( locator );
        scrollIntoView( element );
        element.click();
        oLog.info( locator );
    }

    public void scrollIntoViewAndClick( WebElement element )
    {
        scrollIntoView( element );
        element.click();
        oLog.info( element );
    }

}
