package com.example.idin.projectmobileidin;

/**
 * Created by I'din na on 12/20/2017.
 */

public class showDataItem {
    private String Image_URL,Image_Title;  //put this name same as Database Fields

    public showDataItem(String image_URL, String image_title) {
        Image_URL = image_URL;
        Image_Title = image_title;
    }
    public showDataItem()
    {
        //Empty Constructor Needed
    }

    public String getImage_URL() {
        return Image_URL;
    }

    public void setImage_URL(String image_URL) {
        Image_URL = image_URL;
    }

    public String getImage_Title() {
        return Image_Title;
    }

    public void setTitle(String title) {
        Image_Title = title;

    }
}



