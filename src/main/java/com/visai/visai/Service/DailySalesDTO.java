package com.visai.visai.Service;
public class DailySalesDTO {
    private int day;
    private double totalSales;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public DailySalesDTO(int day, double totalSales) {
        this.day = day;
        this.totalSales = totalSales;
    }

    // Getters and Setters
}
