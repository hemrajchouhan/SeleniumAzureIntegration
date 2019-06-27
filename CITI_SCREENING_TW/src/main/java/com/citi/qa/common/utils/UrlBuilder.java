package com.citi.qa.common.utils;

import com.citi.qa.core.base.WebDriverHelper;
import com.citi.qa.util.Props;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlBuilder
{
    private static final Logger LOG = LoggerFactory.getLogger( UrlBuilder.class );

    private static final String RUN_CONFIG_PROPERTIES = "/environment.properties";

    private static URL basePath;

    static
    {
        try
        {
            Props.loadRunConfigProps( UrlBuilder.RUN_CONFIG_PROPERTIES );
            UrlBuilder.basePath = new URL( Props.getProp( "site.url" ) );

        }
        catch( MalformedURLException e )
        {
            UrlBuilder.LOG.error( e.getMessage() );
        }

    }

    public static void startAtHomePage()
    {
        WebDriverHelper.getWebDriver().navigate().to( UrlBuilder.basePath );
    }

}
