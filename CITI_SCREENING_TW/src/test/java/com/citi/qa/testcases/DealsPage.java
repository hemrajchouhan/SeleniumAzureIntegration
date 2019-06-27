package com.citi.qa.testcases;

import com.citi.qa.core.base.PageObject;
import com.citi.qa.helper.logger.LoggerHelper;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

public class DealsPage
    extends PageObject

{
    private Logger oLog = LoggerHelper.getLogger( DealsPage.class );

    @Test
    public void test()
    {
        oLog.info( "Hello" );
    }
}
