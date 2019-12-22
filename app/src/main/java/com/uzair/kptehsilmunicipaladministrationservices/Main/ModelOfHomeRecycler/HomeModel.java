package com.uzair.kptehsilmunicipaladministrationservices.Main.ModelOfHomeRecycler;

public class HomeModel
{
    private String nameOfService;
    private int imageOfService;

    public HomeModel(String nameOfService, int imageOfService) {
        this.nameOfService = nameOfService;
        this.imageOfService = imageOfService;
    }

    public String getNameOfService() {
        return nameOfService;
    }

    public void setNameOfService(String nameOfService) {
        this.nameOfService = nameOfService;
    }

    public int getImageOfService() {
        return imageOfService;
    }

    public void setImageOfService(int imageOfService) {
        this.imageOfService = imageOfService;
    }
}
