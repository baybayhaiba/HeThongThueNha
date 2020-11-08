package com.example.hethongthuenha.Model;

import java.util.List;

public class Image_Room {
    private List<String> imagesURL;

    public Image_Room() {
    }

    public Image_Room(List<String> imagesURL) {
        this.imagesURL = imagesURL;
    }


    public List<String> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(List<String> imagesURL) {
        this.imagesURL = imagesURL;
    }

    @Override
    public String toString() {
        return "Image_Room{" +
                "imagesURL=" + imagesURL +
                '}';
    }
}
