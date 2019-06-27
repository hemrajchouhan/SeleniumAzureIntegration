/**
 * @author Chouhan_h
 */
package com.citi.qa.checkboxHelper;

import com.citi.qa.core.base.PageObject;
import com.citi.qa.helper.logger.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Chouhan_h
 */
public class CheckBoxOrRadioButtonHelper
    extends PageObject
{

    //private WebDriver driver;

    private Logger oLog = LoggerHelper.getLogger( CheckBoxOrRadioButtonHelper.class );

    public CheckBoxOrRadioButtonHelper( WebDriver driver )
    {
        this.webDriver = driver;
        oLog.debug( "CheckBoxOrRadioButtonHelper : " + this.webDriver.hashCode() );
    }

    public void selectCheckBox( By locator )
    {
        oLog.info( locator );
        selectCheckBox( webDriver.findElement( locator ) );
    }

    public void unSelectCheckBox( By locator )
    {
        oLog.info( locator );
        unSelectCheckBox( webDriver.findElement( locator ) );
    }

    public boolean isIselected( By locator )
    {
        oLog.info( locator );
        return isIselected( webDriver.findElement( locator ) );
    }

    public boolean isIselected( WebElement element )
    {
        boolean flag = element.isSelected();
        oLog.info( flag );
        return flag;
    }

    public void selectCheckBox( WebElement element )
    {
        if( !isIselected( element ) )
        {
            element.click();
        }
        oLog.info( element );
    }

    public void unSelectCheckBox( WebElement element )
    {
        if( isIselected( element ) )
        {
            element.click();
        }
        oLog.info( element );
    }
}
