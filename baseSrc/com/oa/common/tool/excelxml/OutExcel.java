package com.oa.common.tool.excelxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class OutExcel {

	/**
	 * 
	 * @param realPath
	 *            文件的绝对路径
	 * @param sheetname
	 *            工作区名
	 * @param title
	 *            第一行标题 用，
	 * @param list
	 *            数据
	 * @return 03版的xls
	 */ 
	@SuppressWarnings("unchecked")
	public static void outputExcel2003(String realPath, String sheetname,
			String[] title, List list) {
		FileOutputStream fos;
		try {
			File file = new File(realPath);
			fos = new FileOutputStream(file, true);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			wb.setSheetName(0, sheetname);
			int columnSize = title.length;
			int rowsSize = 1;
			try {
				rowsSize = list.size();
			} catch (Exception ex) {
			}
			if (rowsSize == 0) {
				rowsSize = 1;
			}
			HSSFRow[] rows = new HSSFRow[rowsSize];
			HSSFCell[][] cells = new HSSFCell[rowsSize][columnSize];

			rows[0] = s.createRow(0);
			for (int j = 0; j < columnSize; j++) {
				cells[0][j] = rows[0].createCell(j);
				cells[0][j].setCellValue(title[j]);
			}
			for (int i = 1; i < rowsSize; i++) { // 相当于excel表格中的总行数
				rows[i] = s.createRow(i);
				List obj = (List) list.get(i);
				for (int j = 0; j < columnSize; j++) {
					cells[i][j] = rows[i].createCell(j);
					String value = "";
					if (obj.get(j) != null) {
						value = obj.get(j).toString();
					}
					cells[i][j].setCellValue(value);
				}
			}
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param realPath
	 *            文件的绝对路径
	 * @param sheetname
	 *            工作区名
	 * @param title
	 *            第一行标题 用，
	 * @param list
	 *            数据
	 * @return 07版的xlsx
	 * int allrowsNum=0;//包括线索数，对象数，虚拟身份数的总和
	 */
	@SuppressWarnings("unchecked")
	public static void outputClueObjNumExcel2007(String realPath, String sheetname,String[] titleClue,String[] titleObj,String[] titleNum,
			List listReportResult) {

		//realPath="/temp/sxssf.xlsx"
		try {
			 SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
			 wb.setCompressTempFiles(true); // temp files will be gzipped
		     Sheet sh = wb.createSheet();
			wb.setSheetName(0, sheetname);
			Map<String, CellStyle> styles = createStyles(wb);
			//int columnSize = titleClue.length+titleObj.length+titleNum.length;

			{//行０
				Row row = sh.createRow(0);//创建行
				for (int j = 0; j < titleClue.length; j++) {
					 Cell cell = row.createCell(j);//创列
		              //String address = new CellReference(cell).formatAsString();
		                 cell.setCellValue(titleClue[j]);//放列数据
		                 cell.setCellStyle(styles.get("header"));
				}
				int clueCellNum=titleClue.length;
				for (int j = 0; j < titleObj.length; j++) {
						Cell cell = row.createCell(clueCellNum+j);//创列对象的列
		                 cell.setCellValue(titleObj[j]);//放列数据
		                 cell.setCellStyle(styles.get("header2"));
				}
				int clueObjCellNum=titleClue.length+titleObj.length;
				for (int j = 0; j < titleNum.length; j++) {
						Cell cell = row.createCell(clueObjCellNum+j);//创列虚拟身份的列
		                 cell.setCellValue(titleNum[j]);//放列数据
		                 cell.setCellStyle(styles.get("header3"));
				}
			}
			{//行2
				int rowi=1;// 计算excel表格中的总行数

				for (Object rowClue : listReportResult) {
					Row row = sh.createRow(rowi);//
					List clueList=(List)rowClue;
					for (int j = 0; j < titleClue.length; j++) {//线索列数
						Cell cellClue = row.createCell(j);//列数
						Object oClueResult=clueList.get(j);
						if (oClueResult != null) {
								String value = oClueResult.toString();
								cellClue.setCellValue(new HSSFRichTextString(value));//放列线索数据
						}
			            //String address = new CellReference(cell).formatAsString();
			            
					}
					int clueCellNum=titleClue.length;
					for (int j = 0; j < titleObj.length; j++) {
							Cell cellObj = row.createCell(clueCellNum+j);//创列对象的列
							Object oClueResult=clueList.get(clueCellNum+j);
							if (oClueResult != null) {
									String value = oClueResult.toString();
									cellObj.setCellValue(new HSSFRichTextString(value));//放列线索数据
							}
							cellObj.setCellStyle(styles.get("cell"));
					}
					int clueObjCellNum=titleClue.length+titleObj.length;
					for (int j = 0; j < titleNum.length; j++) {
							Cell cell = row.createCell(clueObjCellNum+j);//创列虚拟身份的列
							Object oClueResult=clueList.get(clueObjCellNum+j);
							if (oClueResult != null) {
									String value = oClueResult.toString();
									cell.setCellValue(new HSSFRichTextString(value));//放列线索数据
							}
							cell.setCellStyle(styles.get("cell"));
					}
					  // manually control how rows are flushed to disk 
					if (rowi % 100 == 0) {
						((SXSSFSheet) sh).flushRows(100); // retain 100 last
															// rows and flush
															// all others
					}
					rowi=rowi+1;
				}

			}
			File file = new File(realPath);
			FileOutputStream out = new FileOutputStream(file);
	        wb.write(out);
	        out.close();
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param objList
	 * @param titleObj
	 * @param rowi 当前行数
	 * @param sh　当前sheet
	 * @param rowClue 当前行
	 * @param rowObjNumI　当前行对象的数值
	 * @param clueCellNum　当前线索的列的列数
	 * @return 反回当前第几行的行数
	 */
	@SuppressWarnings("unchecked")
	private static int writeClueAndObj(List objList,String[] titleObj,int rowi,Sheet sh,Row rowClue,int rowObjNumI,int clueCellNum,String[] titleNum){
		int clueCountAndObjCount=0;
		for (Object rowObject : objList) {
			List objectList=(List)rowObject;
			Row rowObj=null;
			if(rowObjNumI!=0){
				clueCountAndObjCount=rowi+rowObjNumI;
				rowObj=sh.createRow(clueCountAndObjCount);//
			}
			for (int i = 0; i < titleObj.length; i++) {
				Cell cellObj =null;
				if(rowObjNumI==0){
					cellObj=rowClue.createCell(clueCellNum+i);//列数
				}else{
					cellObj=rowObj.createCell(clueCellNum+i);//列数
				}
				 
				Object oObjResult=objectList.get(i);
				if (oObjResult != null) {
					if(oObjResult instanceof ArrayList){//对象的虚拟身份数据
						List numObjList =(List)oObjResult;
						int rowNumI=0;
						clueCountAndObjCount=writeNum(numObjList, titleNum, rowi, sh, rowClue, rowNumI, clueCellNum+titleObj.length);
					}else{
						String value = oObjResult.toString();
						cellObj.setCellValue(new HSSFRichTextString(value));//放列线索数据
					}
					
				}
			}
			rowObjNumI++;
		}
		return clueCountAndObjCount;
	}
	
	/**
	 * @param numObjList 虚拟身份的LIst对象
	 * @param titleNum
	 * @param rowi 当前行数
	 * @param sh
	 * @param rowClue 对象数据的第一列
	 * @param rowNumI 当前行虚拟身份的行数值
	 * @param clueObjCellNum　当前线索的列＋对象的列的总列数
	 * @return
	 */
	private static int writeNum(List numObjList,String[] titleNum,int rowi,Sheet sh,Row rowClue,int rowNumI,int clueObjCellNum){
		int clueCountAndObjCount=0;
		for (Object rowNumData : numObjList) {
			List numList=(List)rowNumData;
			Row rowNum=null;
			if(rowNumI!=0){
				clueCountAndObjCount=rowi+rowNumI;
				rowNum=sh.createRow(clueCountAndObjCount);//
			}
			for (int i = 0; i < titleNum.length; i++) {
				Cell cellObj =null;
				if(rowNumI==0){
					cellObj=rowClue.createCell(clueObjCellNum+i);//列数
				}else{
					cellObj=rowNum.createCell(clueObjCellNum+i);//列数
				}
				 
				Object oNumResult=numList.get(i);
				if (oNumResult != null) {
						String value = oNumResult.toString();
						cellObj.setCellValue(new HSSFRichTextString(value));//放列线索虚拟身份数据
				}
			}
			rowNumI++;
		}
		return clueCountAndObjCount;
	}
	
	/**
	 * 
	 * @param realPath
	 *            文件的绝对路径
	 * @param sheetname
	 *            工作区名
	 * @param title
	 *            第一行标题 用，
	 * @param list
	 *            数据
	 * @return 07版的xlsx
	 */
	@SuppressWarnings("unchecked")
	public static void outputClueExcel2007(String realPath, String sheetname,String[] titleClue,String[] titleObj,String[] titleNum,
			List listClue,List listObj,List listNum) {

		//realPath="/temp/sxssf.xlsx"
		try {
			 SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
			 wb.setCompressTempFiles(true); // temp files will be gzipped
		     Sheet sheetClue = wb.createSheet();
			 wb.setSheetName(0, "线索数据");
			 Sheet sheetObj = wb.createSheet();
			 wb.setSheetName(1, "对象或关系人");
			 Sheet sheetObjNum = wb.createSheet();
			 wb.setSheetName(2, "虚拟身份");
			Map<String, CellStyle> styles = createStyles(wb);

			{//行０标题
				Row rowClue = sheetClue.createRow(0);//创建行
				for (int j = 0; j < titleClue.length; j++) {
					 Cell cell = rowClue.createCell(j);//创列
		              //String address = new CellReference(cell).formatAsString();
					 cell.setCellValue(titleClue[j]);//放列数据
					 cell.setCellStyle(styles.get("header"));
         		}
				Row rowObj = sheetObj.createRow(0);//创建行
				for (int j = 0; j < titleObj.length; j++) {
					 Cell cell = rowObj.createCell(j);//创列
		              //String address = new CellReference(cell).formatAsString();
					 cell.setCellValue(titleObj[j]);//放列数据
					 cell.setCellStyle(styles.get("header"));
				}
				Row rowObjNum = sheetObjNum.createRow(0);//创建行
				for (int j = 0; j < titleNum.length; j++) {
					 Cell cell = rowObjNum.createCell(j);//创列
		              //String address = new CellReference(cell).formatAsString();
					 cell.setCellValue(titleNum[j]);//放列数据
					 cell.setCellStyle(styles.get("header"));
				}
			}
			{//行2
				//1.线索
				for (int i = 0; i < listClue.size(); i++) { // 相当于excel表格中的总行数
					Row row = sheetClue.createRow(i+1);//
					List objClue = (List) listClue.get(i);
					for (int j = 0; j < titleClue.length; j++) {
						Cell cell = row.createCell(j);
						String value = "";
						Object oResult=objClue.get(j);
						if (oResult != null) {
								value = objClue.get(j).toString();
						}
			            //String address = new CellReference(cell).formatAsString();
			            cell.setCellValue(new HSSFRichTextString(value));//放列数据
					}
					  // manually control how rows are flushed to disk 
			           if(i % 100 == 0) {
			                ((SXSSFSheet)sheetClue).flushRows(100); // retain 100 last rows and flush all others
			           }
				}
				//2.对象/关系人
				for (int i = 0; i < listObj.size(); i++) { // 相当于excel表格中的总行数
					Row row = sheetObj.createRow(i+1);//
					List obj = (List) listObj.get(i);
					for (int j = 0; j < titleObj.length; j++) {
						Cell cell = row.createCell(j);
						String value = "";
						Object oResult=obj.get(j);
						if (oResult != null) {
								value = obj.get(j).toString();
						}
			            //String address = new CellReference(cell).formatAsString();
			            cell.setCellValue(new HSSFRichTextString(value));//放列数据
					}
					  // manually control how rows are flushed to disk 
			           if(i % 100 == 0) {
			                ((SXSSFSheet)sheetObj).flushRows(100); // retain 100 last rows and flush all others
			           }
				}
				//3.虚拟身份
				for (int i = 0; i < listNum.size(); i++) { // 相当于excel表格中的总行数
					Row row = sheetObjNum.createRow(i+1);//
					List objNum = (List) listNum.get(i);
					for (int j = 0; j < titleNum.length; j++) {
						Cell cell = row.createCell(j);
						String value = "";
						Object oResult=objNum.get(j);
						if (oResult != null) {
								value = objNum.get(j).toString();
						}
			            //String address = new CellReference(cell).formatAsString();
			            cell.setCellValue(new HSSFRichTextString(value));//放列数据
					}
					  // manually control how rows are flushed to disk 
			           if(i % 100 == 0) {
			                ((SXSSFSheet)sheetObjNum).flushRows(100); // retain 100 last rows and flush all others
			           }
				}
			}
			File file = new File(realPath);
			FileOutputStream out = new FileOutputStream(file);
	        wb.write(out);
	        out.close();
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param realPath
	 *            文件的绝对路径
	 * @param sheetname
	 *            工作区名
	 * @param title
	 *            第一行标题 用，
	 * @param list
	 *            数据
	 * @return 07版的xlsx
	 */
	@SuppressWarnings("unchecked")
	public static void outputExcel2007(String realPath, String sheetname,String[] title, List list) {
		//realPath="/temp/sxssf.xlsx"
		try {
			 SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
			 wb.setCompressTempFiles(true); // temp files will be gzipped
		     Sheet sh = wb.createSheet();
			wb.setSheetName(0, sheetname);
			Map<String, CellStyle> styles = createStyles(wb);
			int columnSize = title.length;
			int rowsSize = 1;
			try {
				rowsSize = list.size();
			} catch (Exception ex) {
			}
			if (rowsSize == 0) {
				rowsSize = 1;
			}

			{//行０
				Row row = sh.createRow(0);//创建行
				for (int j = 0; j < columnSize; j++) {
					 Cell cell = row.createCell(j);//创列
		              //String address = new CellReference(cell).formatAsString();
		              cell.setCellValue(title[j]);//放列数据
		              cell.setCellStyle(styles.get("header"));
				}
			}
			for (int i = 1; i <= rowsSize; i++) { // 相当于excel表格中的总行数
				Row row = sh.createRow(i);//
				List obj = (List) list.get(i-1);
				for (int j = 0; j < columnSize; j++) {
					Cell cell = row.createCell(j);
					String value = "";
					Object oResult=obj.get(j);
					if (oResult != null) {
							value = obj.get(j).toString();
					}
		            //String address = new CellReference(cell).formatAsString();
		            cell.setCellValue(value);//放列数据
				}
				
				  // manually control how rows are flushed to disk 
		           if(i % 100 == 0) {
		                ((SXSSFSheet)sh).flushRows(100); // retain 100 last rows and flush all others

		                // ((SXSSFSheet)sh).flushRows() is a shortcut for ((SXSSFSheet)sh).flushRows(0),
		                // this method flushes all rows
		           }

			}
			File file = new File(realPath);
			FileOutputStream out = new FileOutputStream(file);
	        wb.write(out);
	        out.close();
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	// 将字CLOB转成STRING类型
    public static String ClobToString(CLOB clob){
	try{
	        String reString = "";
	        Reader is = clob.getCharacterStream();// 得到流
	        BufferedReader br = new BufferedReader(is);
	        String s = br.readLine();
	        StringBuffer sb = new StringBuffer();
	        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
	            sb.append(s);
	            s = br.readLine();
	        }
	        reString = sb.toString();
	        return reString;
	}catch(Exception ex){
		ex.printStackTrace();
		return "";
	}
    }
	/**
     * Create a library of cell styles
     */
    private static Map<String, CellStyle> createStyles(Workbook wb){
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short)18);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short)11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header2", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header3", style);
        
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);

        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);

        return styles;
    }

	public static void outputExcel2007Test() throws Exception{
	    SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
	    wb.setCompressTempFiles(true); // temp files will be gzipped
        Sheet sh = wb.createSheet();
        for(int rownum = 0; rownum < 1050; rownum++){
            Row row = sh.createRow(rownum);
            for(int cellnum = 0; cellnum < 10; cellnum++){
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
            }

           // manually control how rows are flushed to disk 
           if(rownum % 100 == 0) {
                ((SXSSFSheet)sh).flushRows(100); // retain 100 last rows and flush all others

                // ((SXSSFSheet)sh).flushRows() is a shortcut for ((SXSSFSheet)sh).flushRows(0),
                // this method flushes all rows
           }

        }

        FileOutputStream out = new FileOutputStream("c:/sxssf1.xlsx");
        wb.write(out);
        out.close();

	}
	public static void main(String[] args) throws Exception {
		outputExcel2007Test();
		System.out.println("-----");
	}

}
