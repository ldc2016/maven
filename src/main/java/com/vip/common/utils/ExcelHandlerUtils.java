package com.vip.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * Created by dacheng.liu on 2017/10/17.
 */
public class ExcelHandlerUtils {
    public static void main(String[] args) throws IOException {
        String dstFile = "D:\\2017-10-18迁移用户.xlsx";
        String txtFilePath = "D:\\2017-10-18迁移用户.txt";

        OutputStream os = new FileOutputStream(dstFile);
        final BufferedReader bufferedReader = (BufferedReader) TxtFileHandlerUtils.getReader(txtFilePath);

        //定义内存中存储的行数
        int memoryRowAccess = 100;//内存中缓存记录行数
        //生成3个SHEET
        int sheetNum=1;

        XSSFWorkbook dstWorkBook = new XSSFWorkbook();
        SXSSFWorkbook workBook = new SXSSFWorkbook(dstWorkBook,memoryRowAccess);

        for(int i=0;i <sheetNum;i++){
            boolean alreadyExport = false;
            SXSSFSheet sh = workBook.createSheet();
            CellStyle cellStyle = workBook.createCellStyle();
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            //每个SHEET有60000行
            String lineDataMeta = "";
            for(int rownum = 0; rownum < 60000; rownum++) {
                // 设置表头
                if (setCellTitle(sh, cellStyle, rownum)) continue;

                lineDataMeta = bufferedReader.readLine();
                if(StringUtils.isBlank(lineDataMeta)){
                    alreadyExport = true;
                    sh.flushRows();
                    break;
                }

                makeExcelFileValue(sh, cellStyle, lineDataMeta, rownum);

                //每当行数达到设置的值就刷新数据到硬盘,以清理内存
                if(rownum != 0 && (rownum % memoryRowAccess == 0)){
                    if(!alreadyExport){
                        sh.flushRows();
                    }
                }
            }
        }

        /*写数据到文件中*/
        workBook.write(os);
        os.close();

        System.out.println("导出excel文件完毕！");

    }

    private static void makeExcelFileValue(SXSSFSheet sh, CellStyle cellStyle, String lineDataMeta, int rownum) {
        SXSSFRow row = sh.createRow(rownum);
        row.setRowStyle(cellStyle);

        String[] sourceData = lineDataMeta.split(",");
        for(int j = 0; j< sourceData.length;j++){
            SXSSFCell cell = (SXSSFCell) row.createCell(j);
            cell.setCellValue(sourceData[j]);
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        }
    }

    private static boolean setCellTitle(SXSSFSheet sh, CellStyle cellStyle, int rownum) {
        if(rownum == 0){
            SXSSFRow row = sh.createRow(rownum);
            SXSSFCell titlecell = row.createCell(0);
//            titlecell.setCellValue("user_id");
//            titlecell.setCellStyle(cellStyle);

            titlecell = row.createCell(1);
            titlecell.setCellValue("uid");
            titlecell.setCellStyle(cellStyle);
            return true;
        }
        return false;
    }

}
