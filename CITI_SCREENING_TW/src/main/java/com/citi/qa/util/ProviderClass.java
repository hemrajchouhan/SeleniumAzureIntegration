package com.citi.qa.util;

import java.lang.reflect.Method;
import org.testng.annotations.DataProvider;

public class ProviderClass
{

    @DataProvider( name = "testDataProvider" )
    public static Object[][] getData( Method m )
        throws Exception
    {
        Xls_Reader xls = new Xls_Reader( Constants.ModuleExecution );
        return Utility.getData( m.getName(), xls );
    }
}
