package com.example.foodrecipes.model.postImage;

import java.util.ArrayList;
import java.util.List;

public class ImageList {
    List<Image> Array_image = new ArrayList<Image>();

    public void setArray_image(List<Image> array_image) {
        Array_image = array_image;
    }

    public List<Image> getArray_image() {
        return Array_image;
    }

}
