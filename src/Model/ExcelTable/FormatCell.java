package Model.ExcelTable;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 * Java class that format cell of excel file
 */
public class FormatCell {
    public static XSSFColor[] cellColor = {color(250,191,143), color(204, 192, 218), color(146, 205, 220),
                                           color(146, 208, 80), color(255,255,0), color(83, 141, 213), color(184, 204, 228)};

    /**
     * generate color index base on color code
     */
    private static XSSFColor color(int red, int green, int blue){
        return new XSSFColor(new java.awt.Color(red,green, blue));
    }

    /**
     * format cell
     * @requires wb not null /\ color not null
     */
    public static XSSFCellStyle format(XSSFWorkbook wb, XSSFColor color){
        XSSFCellStyle result = wb.createCellStyle();
        result.setAlignment(CellStyle.ALIGN_CENTER);

        XSSFFont font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        result.setFont(font);

        result.setBorderRight(CellStyle.BORDER_THIN);
        result.setRightBorderColor(IndexedColors.BLACK.getIndex());
        result.setBorderBottom(CellStyle.BORDER_THIN);
        result.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        result.setBorderLeft(CellStyle.BORDER_THIN);
        result.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        result.setBorderTop(CellStyle.BORDER_THIN);
        result.setTopBorderColor(IndexedColors.BLACK.getIndex());

        result.setFillForegroundColor(color);
        result.setFillPattern(CellStyle.SOLID_FOREGROUND);

        return result;
    }

    /**
     * format cell
     * @require wb not null
     */
    public static XSSFCellStyle format(XSSFWorkbook wb){
        XSSFCellStyle result = wb.createCellStyle();
        result.setAlignment(CellStyle.ALIGN_CENTER);
        result.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        XSSFFont font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        result.setFont(font);

        result.setBorderRight(CellStyle.BORDER_THIN);
        result.setRightBorderColor(IndexedColors.BLACK.getIndex());
        result.setBorderBottom(CellStyle.BORDER_THIN);
        result.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        result.setBorderLeft(CellStyle.BORDER_THIN);
        result.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        result.setBorderTop(CellStyle.BORDER_THIN);
        result.setTopBorderColor(IndexedColors.BLACK.getIndex());

        return result;
    }
}
