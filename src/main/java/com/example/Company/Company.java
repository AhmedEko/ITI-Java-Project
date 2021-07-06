package com.example.Company;

public class Company {

    String title;
    String name;
    String location;
    String type;
    String level;
    String yearsExp;
    String country;
    String skills;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setCompany(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getYearsExp() {
        return yearsExp;
    }

    public void setYearsExp(String yearsExp) {
        this.yearsExp = yearsExp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Company(String title, String name, String location, String type, String level, String yearsExp, String country, String skills) {
        this.title = title;
        this.name = name;
        this.location = location;
        this.type = type;
        this.level = level;
        this.yearsExp = yearsExp;
        this.country = country;
        this.skills = skills;
    }


    @Override
    public String toString() {
        return "Company{" + "title=" + title + ", name=" + name + ", location=" + location + ", type=" + type + ", level=" + level + ", yearsExp=" + yearsExp + ", country=" + country + ", skills=" + skills + '}';
    }
}


