package com.example.Company;

import org.apache.spark.sql.*;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.types.StructType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.apache.spark.sql.functions.*;

@SpringBootApplication
@RestController
public class CompanyApplication {
    Dataset<Row> rows ;
      Dataset<Row> noNullAndNoDuplicate;
    CompanyApplication(){
         SparkSession ss = SparkSession.builder()
                .appName("Wuzzuf Jobs")
                .config("spark.eventLog.enabled", "false")
                .master("local[1]")
                .getOrCreate();
        ss.sparkContext().setLogLevel("ERROR");
        rows = ss.read().option("header", true).csv("Wuzzuf_Jobs.csv");
        rows = rows.select(
                col("Title"),
                col("Company"),
                col("Location"),
                col("Type"),
                col("Level"),
                col("YearsExp"),
                col("Country"),
                col("Skills")
        );

    }

	public static void main(String[] args) {SpringApplication.run(CompanyApplication.class, args); }
 @Profile("!test")
  @Bean
    @GetMapping("/Yahooo")

    public String runSparkML(){

return "Project running";



    }




    @GetMapping("/data")
    public String showdata(){

        return rows.showString(20,20,false);

    }


    @GetMapping("/structure")
    public StructType structure(){

        return rows.schema();
    }





    @GetMapping("/summary")
    public String summary(){


       return rows.summary().showString(20,20,false);
    }

    @GetMapping("/cleandata")
public  String cleandata(){

        String colNames[] = rows.columns();
        Dataset<Row> summary = null;
        for(int i = 0; i < colNames.length; i++){
            String thisColName = colNames[i];
            Dataset<Row> numNullinCol = rows.filter(col(thisColName).isNull()).select(count("*").as("NullOf"+thisColName));
            /*
            To reader: Please suggest better way to do counting of null values in each columns using Spark Java API
             */
            if(summary==null){
                summary=numNullinCol;
                continue;
            }
            summary = summary.join(numNullinCol);
        }




        //Data cleansing - Remove Null Values and duplication
        Column dropCondition = null;
        for(int i = 0; i < colNames.length; i++){
            Column filterCol = col(colNames[i]).isNotNull();
            if(dropCondition==null) {
                dropCondition = filterCol;
                continue;
            }
            dropCondition = dropCondition.and(filterCol);
        }
        System.out.println("Filter condition: " + dropCondition);
        Dataset<Row> nonNullCompany = rows.filter(dropCondition);
        noNullAndNoDuplicate = nonNullCompany.dropDuplicates();
        System.out.println("Remain number of non-null or Duplicate Company: " + noNullAndNoDuplicate.count());
       // noNullAndNoDuplicate.show();
        return noNullAndNoDuplicate.showString(20,20,false);


    }
    @GetMapping("/mostdemandingjobs")
public String mostdemandingjobs(){
     if(noNullAndNoDuplicate==null){
         cleandata();
     }


    System.out.println("==========Get the highest demanding Jobs==========");
   // noNullAndNoDuplicate.select("Title","Company").groupBy("Company","Title").count().sort(desc("count")).show();
    // return nonNullCompany.showString(20,10,false) ;//(3)
       noNullAndNoDuplicate.select("Title","Company").groupBy("Company","Title").count().sort(desc("count")).show();
    return  noNullAndNoDuplicate.select("Title","Company").groupBy("Company","Title").count().sort(desc("count")).showString(1000, 10, false);


}









    @GetMapping("/popularjob")
    public String popularjob(){
        if(noNullAndNoDuplicate==null){
            cleandata();
        }



        return  noNullAndNoDuplicate.select("Title").groupBy("Title").count().sort(desc("count")).showString(1000, 10, false);


    }


    @GetMapping("/locations")
    public String locations(){
        if(noNullAndNoDuplicate==null){
            cleandata();
        }



        return  noNullAndNoDuplicate.select("Location").groupBy("Location").count().sort(desc("count")).showString(1000, 10, false);


    }



    @GetMapping("/skills")
    public String skills(){
        if(noNullAndNoDuplicate==null){
            cleandata();
        }



        return  noNullAndNoDuplicate.select("Skills").groupBy("Skills").count().sort(desc("count")).showString(20, 10, false);


    }

    @GetMapping("/AreaChart")
    public String AreaChart()  {


        return " https://cdn.discordapp.com/attachments/532104294183206933/862066866355830814/12b01753-fbcc-479f-9ee1-fc26955b2301.png";
    }
    @GetMapping("/Companies")
    public String Companies()  {


        return "https://cdn.discordapp.com/attachments/532104294183206933/862067868349759541/0489042f-6d63-4848-a0a5-bba4404a7adf.png";
    }
    @GetMapping("/Demandingjobs")
    public String Demandingjobs()  {


        return "https://cdn.discordapp.com/attachments/532104294183206933/862072866072821770/8d5cea47-04a9-458e-b683-8f2762d7412a.png";
    }

}
