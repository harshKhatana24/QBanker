package com.QuestionBankProject.QBanker.Services.Impl;

import com.QuestionBankProject.QBanker.Entities.Question;
import com.QuestionBankProject.QBanker.Entities.QuestionBank;
import com.QuestionBankProject.QBanker.Repository.QuestionBankRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

public class ExcelUploadService {

    public static boolean isValidExcelFile(MultipartFile file){
        return Objects.equals(file.getContentType(),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }
    public static List<Question> getQuestionsDataFromExcel(InputStream inputStream, QuestionBank questionBank){
        List<Question> questions=new ArrayList<>();

        try{
            XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
            XSSFSheet sheet=workbook.getSheet("questions");
            int rowIndex=0;
            for (Row row:sheet){
                if (rowIndex==0){
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator= row.iterator();
                int cellIndex=0;
                Question question=new Question();
                while (cellIterator.hasNext()){
                    Cell cell=cellIterator.next();
                    switch (cellIndex){
                        case 0 -> question.setQuestionId(UUID.randomUUID().toString());
                        case 1 -> question.setQuestionText(cell.getStringCellValue());
                        case 2 -> question.setMarks(String.valueOf(cell.getNumericCellValue()));
                        case 3 -> question.setMCQ(cell.getBooleanCellValue());
                        case 4 -> question.setOption1(cell.getStringCellValue());
                        case 5 -> question.setOption2(cell.getStringCellValue());
                        case 6 -> question.setOption3(cell.getStringCellValue());
                        case 7 -> question.setOption4(cell.getStringCellValue());
                        case 8 -> question.setCorrectMCQAns(cell.getStringCellValue());
                        case 9 -> question.setTopic(cell.getStringCellValue());
                        case 10 -> question.setSolution(cell.getStringCellValue());
                        default -> {

                        }
                    }
                    cellIndex++;
                }
                question.setQuestionBank(questionBank);

                questions.add(question);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return questions;
    }
}
