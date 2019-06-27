/**
 * @author rahul.rathore 07-Aug-2016
 */
package com.citi.qa.helper.TextBox;

import com.citi.qa.genericHelper.GenericHelper;
import com.citi.qa.helper.logger.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author rahul.rathore 07-Aug-2016
 */
public class TextBoxHelper
    extends GenericHelper
{

    //private WebDriver driver;

    private Logger oLog = LoggerHelper.getLogger( TextBoxHelper.class );

    public TextBoxHelper( WebDriver driver )
    {
        super( driver );
        this.webDriver = driver;
        oLog.debug( "TextBoxHelper : " + this.webDriver.hashCode() );
    }

    public void sendKeys( By locator, String value )
    {
        oLog.info( "Locator : " + locator + " Value : " + value );
        getElement( locator ).sendKeys( value );
    }

    public void clear( By locator )
    {
        oLog.info( "Locator : " + locator );
        getElement( locator ).clear();
    }

    public String getText( By locator )
    {
        oLog.info( "Locator : " + locator );
        return getElement( locator ).getText();
    }

    public void clearAndSendKeys( By locator, String value )
    {
        WebElement element = getElement( locator );
        element.clear();
        element.sendKeys( value );
        oLog.info( "Locator : " + locator + " Value : " + value );
    }

}
