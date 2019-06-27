package com.citi.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class Contains the basic excel Manipulation functions.
 * 
 * @author Hemraj Chouhan
 */

public class Xls_Reader
{
    public String path = "";

    private Workbook workbook = null;

    private Sheet sheet = null;

    public Xls_Reader( String filePath )
    {
        path = filePath;
        workbook = getWorkBook( path );
        sheet = workbook.getSheetAt( 0 );
    }

    /**
     * This function returns workbook object of excel file
     * 
     * @param path
     * @return
     */
    public Workbook getWorkBook( String path )
    {
        Workbook workbook = null;
        File file = new File( path );
        if( file.exists() )
        {
            try
            {
                FileInputStream input = new FileInputStream( path );
                String extension = FilenameUtils.getExtension( path );
                workbook = extension.equalsIgnoreCase( "xls" ) ? new HSSFWorkbook( input ) : new XSSFWorkbook( input );
                input.close();
            }
            catch( IOException e )
            {
                System.out.println( "Exception cought " + e );
            }
        }
        else
        {
            System.out.println( "File not exists " );
        }

        return workbook;
    }

    /**
     * Depending upon sheet name it returns the sheet object
     */
    public Sheet getSheet( String sheetName )
        throws Exception
    {
        if( workbook.getSheetName( workbook.getSheetIndex( sheet ) ).equals( sheetName ) )
        {
            return sheet;
        }
        if( sheetName == null || sheetName.isEmpty() )
        {

        }
        else
        {
            int index = workbook.getSheetIndex( sheetName );
            if( index == -1 )
            {
                //throw new Exception(String.format("Sheet(%s) is not found in Excel Workbook(%s)",sheetName,path));
            }
            else
            {
                sheet = workbook.getSheetAt( index );
            }
        }
        return sheet;
    }

    /**
     * Depending upon index it returns the sheet object
     * 
     * @param index
     * @return
     * @throws Exception
     */
    public Sheet getSheetAt( int index )
        throws Exception
    {
        if( index < 0 )
        {
            //throw new Exception(String.format("Sheet is not found @ index = %s", index));
        }
        else
        {
            sheet = workbook.getSheetAt( index );
        }
        return sheet;
    }

    /**
     * This function returns cell contents as string
     * 
     * @param sheetName
     * @param rowNumber
     * @param columnNumber
     * @return
     * @throws Exception
     */
    public String getCellData( String sheetName, int rowNumber, int columnNumber )
        throws Exception
    {
        String celldata = "";
        if( columnNumber >= 0 || rowNumber >= 0 )
        {
            try
            {
                sheet = getSheet( sheetName );
                Row row = sheet.getRow( rowNumber );
                Cell cell = row.getCell( columnNumber );
                celldata = getCellContentAsString( cell );
            }
            catch( NullPointerException e )
            {
                //System.out.println("Geting NullPointerException while reading cell => Sheet_Name="+sheetName+" column="+columnNumber+"  rownumber="+rowNumber);
                return "";
            }
            catch( Exception ex )
            {
                //System.out.println("Geting exception while reading cell => Sheet_Name="+sheetName+" column="+columnNumber+"  rownumber="+rowNumber);
                return "";
            }

        }
        else
        {
            //throw new Exception("Invalid index..!! rowIndex= " + rowNumber +"  columnIndex="+columnNumber);
        }
        return celldata;
    }

    /**
     * This function returns cell contents as string
     * 
     * @param sheetName
     * @param columnName
     * @param rowNumber
     * @return returns cell contents as string
     * @throws Exception
     */
    public String getCellData( String sheetName, int rowNumber, String columnName )
        throws Exception
    {
        String celldata = "";
        sheet = getSheet( sheetName );
        int columnNumber = getColumnNumber( 0, columnName );
        if( columnNumber >= 0 || rowNumber >= 0 )
        {
            try
            {
                Row row = sheet.getRow( rowNumber );
                Cell cell = row.getCell( columnNumber );
                celldata = getCellContentAsString( cell );
            }
            catch( NullPointerException e )
            {
                //System.out.println("Geting NullPointerException while reading cell => Sheet_Name="+sheetName+" column="+columnNumber+"  rownumber="+rowNumber);
                return "";
            }
            catch( Exception ex )
            {
                //This Log Error Should be here. Sometimes we get exception while reading the cell data if the cell is blank.
                //System.out.println("Geting exception while reading cell => Sheet_Name="+sheetName+" column="+columnNumber+"  rownumber="+rowNumber);
                return "";
            }
        }
        else
        {
            //throw new Exception("Invalid index..!! rowIndex= " + rowNumber +"  columnIndex="+columnNumber);
        }
        return celldata;
    }

    public boolean setCellData( String sheetName, int rowNum, String colName, int headerColumnNumber, String data )
        throws Exception
    {
        int columnNumber = getColumnNumber( headerColumnNumber, colName );
        boolean result = setCellData( sheetName, rowNum, columnNumber, headerColumnNumber, data );
        return result;
    }

    public boolean setCellData( String sheetName, int rowNum, int columnNum, int headerColumnNumber, String data )
        throws Exception
    {
        try
        {
            sheet = getSheet( sheetName );
            Row row = sheet.getRow( rowNum );
            if( row == null )
            {
                row = sheet.createRow( rowNum );
            }

            sheet.autoSizeColumn( columnNum );
            Cell cell = row.getCell( columnNum );

            if( cell == null )
            {
                cell = row.createCell( columnNum );
            }

            cell.setCellValue( data );
            Xls_Reader.writeToExcel( workbook, path );
        }
        catch( Exception e )
        {
            //throw new Exception("Problem While setting data @rowNum="+rowNum+ " ColumnNum= "+columnNum,e);
        }
        return true;
    }

    /**
     * @param rowNum
     * @param columnName
     * @return
     */
    public int getColumnNumber( int rowNum, String columnName )
    {
        int colNum = -1;
        if( rowNum >= 0 && !columnName.isEmpty() )
        {
            Row row = sheet.getRow( rowNum );
            for( int i = 0; i < row.getLastCellNum(); i++ )
            {
                Cell cell = null;
                try
                {
                    cell = row.getCell( i );
                }
                catch( NullPointerException e )
                {
                    System.out.println( String.format( "Cell number %s is not defined @rowNum = %s", i, rowNum ) );
                }

                if( cell != null && cell.getStringCellValue().trim().equalsIgnoreCase( columnName ) )
                {
                    colNum = i;
                    break;
                }
            }
        }
        if( colNum == -1 )
        {
            //System.out.println("Enable to find column " + columnName + " at row number "+ rowNum);
        }
        return colNum;
    }

    /**
     * This function returns the total number of column exist in sheet. This function consider the first row of the
     * sheet as the column row
     * 
     * @param sheetName is the name of the sheet
     * @return returns the column count
     * @throws Exception
     */
    public int getColumnCount( String sheetName )
        throws Exception
    {
        sheet = getSheet( sheetName );
        Row row = sheet.getRow( 0 );
        if( row == null )
        {
            return -1;
        }
        return row.getLastCellNum();
    }

    /**
     * This function returns the total number of columns depending upon columnEndKeyWord. E.g It will increase the
     * counter for the columns until it find the columnEndKeyWord in the passed rowNumber. MaxColumn count is 200 to
     * avoid the infinite loop
     * 
     * @param sheetName is the name of the sheet
     * @return returns the column count
     * @throws Exception
     */
    public int getColumnCount( String sheetName, int rowNumber, String columnEndKeyWord )
        throws Exception
    {
        int MaxColumnCount = 200;
        int columnCount = 0;
        int currentColumn = 1;
        while( currentColumn <= MaxColumnCount )
        {
            if( getCellData( sheetName, rowNumber, currentColumn ).equalsIgnoreCase( columnEndKeyWord ) )
            {
                break;
            }
            else
            {
                columnCount = columnCount + 1;
                currentColumn = currentColumn + 1;
            }
        }
        return columnCount;
    }

    /**
     * @param sheetName
     * @return
     * @throws Exception
     */
    public int getRowCount( String sheetName )
        throws Exception
    {
        sheet = getSheet( sheetName );
        int number = sheet.getLastRowNum();
        return number + 1;
    }

    /**
     * @param cell
     * @return
     */
    private String getCellContentAsString( Cell cell )
        throws Exception
    {
        String celldata = "";
        switch( cell.getCellType() )
        {

        case Cell.CELL_TYPE_BLANK:
            celldata = "";
            break;

        case Cell.CELL_TYPE_STRING:
            celldata = cell.getStringCellValue();
            break;

        case Cell.CELL_TYPE_NUMERIC:
            DataFormatter df = new DataFormatter();
            celldata = df.formatCellValue( cell );
            break;

        case Cell.CELL_TYPE_FORMULA:
            celldata = String.valueOf( cell.getNumericCellValue() );
            break;

        case Cell.CELL_TYPE_BOOLEAN:
            celldata = String.valueOf( cell.getBooleanCellValue() );
            break;

        default:
            celldata = cell.getStringCellValue();
            break;
        }
        return celldata;
    }

    public synchronized static void writeToExcel( Workbook workbook, String filePath )
        throws Exception
    {
        FileOutputStream fileOut;
        try
        {
            fileOut = new FileOutputStream( filePath );
            workbook.write( fileOut );
            fileOut.close();
        }
        catch( IOException e )
        {
            //throw new Exception(String.format("Problem while writting into the file(%s)",filePath),e);

        }

    }
}
