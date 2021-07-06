package com.example.Company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyCSVDAO {

    private Company company;
    private List<Company> Companies;
    public CompanyCSVDAO() {
        this.Companies= new ArrayList<Company>();
    }
    public List<Company> readCompaniesFromCsv(String file_path)
    {
        try {

            Scanner sc = new Scanner(new File(file_path));

            String line=sc.nextLine(); // Gets the first row which contains the headers of the fields.

            while (sc.hasNext())
            {
                line=sc.nextLine();
                this.Companies.add(this.createCompany(line.split(",")));
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CompanyCSVDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Companies;
    }

    public Company createCompany(String [] data)
    {
        String title = data[0];
        String name = data[1];
        String location = data[2];
        String type = data[3];
        String level = data[4];
        String yearsExp = data[5];
        String country = data[6];
        String skills = data[7];

        this.company=new Company(title, name, location, type,level, yearsExp, country, skills);
        return company;
    }

}

