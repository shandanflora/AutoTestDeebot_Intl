package com.ecovacs.ecosphere.intl.common;

import android.util.Log;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ecosqa on 17/2/6.
 *
 */

public class ParseExcel {
    private static ParseExcel parseExcel = null;

    private ParseExcel(){

    }

    public static ParseExcel getInstance(){
        if (parseExcel == null){
            parseExcel = new ParseExcel();
        }
        return parseExcel;
    }

    private String getCellValue(Cell cell){
        String strResult;
        CellType type = cell.getCellTypeEnum();
        if(type == CellType.BLANK){
            strResult = "";
        }else if(type == CellType.BOOLEAN){
            strResult = String.valueOf(cell.getBooleanCellValue());
        }else if(type == CellType.ERROR){
            strResult = null;
        }else if(type == CellType.FORMULA){
            strResult = cell.getCellFormula();
        }else if(type == CellType.NUMERIC){
            if (DateUtil.isCellDateFormatted(cell)) {
                Date theDate = cell.getDateCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat();
                strResult = sdf.format(theDate);
            } else {
                strResult = String.valueOf(cell.getNumericCellValue());
            }
        }else if(type == CellType.STRING){
            strResult = cell.getStringCellValue();
        }else {
            strResult = null;
        }
        return strResult;
    }

    public Map<String, String> readExcel(String strFile, String strLanguage){
        Map<String, String> tranMap = new HashMap<>();
        XSSFWorkbook workBook;
        try{
            Log.i("AutoTest", "get resource inputstream!!!");
            InputStream inputstream = this.getClass().getClassLoader().getResourceAsStream(strFile);
            Log.i("AutoTest", "get resource inputstream finished!!!");
            workBook = new XSSFWorkbook(inputstream);
            Log.i("AutoTest", "new workbook!!!");
            XSSFSheet sheet = workBook.getSheetAt(Integer.valueOf(PropertyData.getProperty(strLanguage)));

            int iRowSize = sheet.getLastRowNum();
            for(int i = 0; i < iRowSize; i++){
                if (sheet.getSheetName().equals("zh")){// parse sheet "zh"
                    if(getCellValue(sheet.getRow(i).getCell(0)) == null){
                        tranMap.put(getCellValue(sheet.getRow(i).getCell(0)), getCellValue(sheet.getRow(i).getCell(1)));
                    }
                }else {
                    tranMap.put(getCellValue(sheet.getRow(i).getCell(0)), getCellValue(sheet.getRow(i).getCell(2)));
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        Log.i("AutoTest", "The count of sheet" + String.valueOf(tranMap.size() ));
        return tranMap;
    }

}
