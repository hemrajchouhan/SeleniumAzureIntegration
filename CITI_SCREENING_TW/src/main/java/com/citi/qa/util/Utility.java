package com.citi.qa.util;

import java.util.HashMap;

/**
 * Utility to fetch Data from Excel in HashMap And Pass to DataProvider
 *
 * @author Hemraj_Chouhan
 */
public class Utility

{
    //reading test data
    public static Object[][] getData( String testName, Xls_Reader xls )
        throws Exception
    {
        //int rows=xls.getRowCount(Constants.DATA_SHEET);
        int rows = xls.getRowCount( Constants.TESTCASE_SHEET );
        int dataRows = xls.getRowCount( Constants.DATA_SHEET );

        int testCaseRowNum = 0, dataRowNum = 0;
        for( testCaseRowNum = 0; testCaseRowNum < rows; testCaseRowNum++ )
        {
//			String testNameXls=xls.getCellData(Constants.DATA_SHEET, testCaseRowNum, 0);
            String testNameXls = xls.getCellData( Constants.TESTCASE_SHEET, testCaseRowNum, 0 );
            if( testNameXls.equalsIgnoreCase( testName ) )
            {
                /*
                 * if( xls.getCellData( CITIBase.prop.getProperty( "url" ), testCaseRowNum, 1 ).equals( Constants.YES )
                 * ) { for( dataRowNum = 0; dataRowNum < dataRows; dataRowNum++ ) { String dataXls = xls.getCellData(
                 * Constants.DATA_SHEET, dataRowNum, 0 ); if( dataXls.equals( testNameXls ) ) { break; } } } break;
                 */
            }
        }
        //System.out.println("Test case starts from the row number "+ testCaseRowNum);

        int rowDataStartRowNum = dataRowNum + 2;
        int colDataStartRowNum = dataRowNum + 1;

        //rows of data
        int testRows = 0;
        while( !xls.getCellData( Constants.DATA_SHEET, rowDataStartRowNum + testRows, 0 ).equals( "" ) )
        {
            testRows++;
        }
        //System.out.println("Total rows of data "+testRows);

        //cols of data
        int testCols = 0;
        while( !xls.getCellData( Constants.DATA_SHEET, rowDataStartRowNum, testCols ).equals( "" ) )
        {
            //System.out.println(xls.getCellData(Constants.DATA_SHEET, rowDataStartRowNum, testCols));
            testCols++;
        }
        //System.out.println("Total Cols are "+testCols);

        Object[][] data = new Object[testRows][1];
        int r = 0;
        int rodDataSet = rowDataStartRowNum + testRows;
        for( int rNum = rowDataStartRowNum; rNum < rodDataSet; rNum++, rowDataStartRowNum++ )
        {
            HashMap<String, String> table = new HashMap<String, String>();
            for( int cNum = 0; cNum < testCols; cNum++ )
            {
                table.put( xls.getCellData( Constants.DATA_SHEET, colDataStartRowNum, cNum ),
                        xls.getCellData( Constants.DATA_SHEET, rowDataStartRowNum, cNum ) );
            }
            data[r][0] = table;
            r++;
        }
        return data;
    }
}
