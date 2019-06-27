package com.citi.qa.helper.logger;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LoggerHelper
{

    private static boolean root = false;

    public static Logger getLogger( Class clas )
    {
        if( LoggerHelper.root )
        {
            return Logger.getLogger( clas );
        }

        PropertyConfigurator.configure( ResourceHelper.getResourcePath( "configfile/log4j.properties" ) );
        LoggerHelper.root = true;
        return Logger.getLogger( clas );
    }

}
