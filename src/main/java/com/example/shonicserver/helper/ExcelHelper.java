package com.example.shonicserver.helper;

import com.example.shonicserver.dto.ProductDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "No", "Nama", "Foto", "Harga","Diskon (%)","Brand","Kategori","Sub kategori","Stock (pcs)","Berat (gram)","Deskripsi" };

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
    public static List<ProductDto> excelProduct(InputStream is,String SHEET) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<ProductDto> products = new ArrayList<ProductDto>();
            int rowNumber = 0;
            while (rows.hasNext()) {

                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                ProductDto product = new ProductDto();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 1:
                            if(currentCell.getStringCellValue().isEmpty())
                                return products;
                            product.setName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            product.setImageUrl(currentCell.getStringCellValue());
                            break;
                        case 3:
                            String str = currentCell.getStringCellValue();
                            int price = Integer.parseInt(str.replace(".", ""));
                            product.setPrice(price);
                            break;
                        case 4:
                            product.setDiscount((int)currentCell.getNumericCellValue());
                            break;
                        case 5:
                            product.setBrand(currentCell.getStringCellValue());
                            break;
                        case 6:
                            product.setCategoryParent(currentCell.getStringCellValue());
                            break;
                        case 7:
                            product.setCategory(currentCell.getStringCellValue());
                            break;
                        case 8:
                            product.setQty((int)currentCell.getNumericCellValue());
                            break;
                        case 9:
                            product.setWeight((float) currentCell.getNumericCellValue());
                            break;
                        case 10:
                            product.setDescription(currentCell.getStringCellValue());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                products.add(product);
                    rowNumber++;
            }
            workbook.close();
            return products;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
