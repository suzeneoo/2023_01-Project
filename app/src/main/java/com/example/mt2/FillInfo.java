package com.example.mt2;

public class FillInfo {

    private String itemName; // 이름
    private String ingredient; // 성분
    private String efcyQesitm; // 효능
    private String entpName; // 업체명
    private String formulation; // 제형
    private String frontText;   // 앞문자
    private String backText;    // 뒷문자
    private String shape;       // 모양
    private String color;

    public FillInfo(String newItemName, String newIngredient, String newEfcyQesitm, String newEntpName,
                    String newFormulation, String newFrontText, String newBackText, String newShape, String newColor) {
        this.itemName = newItemName;
        this.ingredient = newIngredient;
        this.efcyQesitm = newEfcyQesitm;
        this.entpName = newEntpName;
        this.formulation = newFormulation;
        this.frontText = newFrontText;
        this.backText = newBackText;
        this.shape = newShape;
        this.color = newColor;
    }

    public String getItemName() {
        return itemName;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getEfcyQesitm() {
        return efcyQesitm;
    }

    public String getEntpName() {
        return entpName;
    }

    public String getFormulation() {
        return formulation;
    }

    public String getFrontText(){
        return frontText;
    }

    public String getBackText(){
        return backText;
    }

    public String getShape(){
        return shape;
    }

    public String getColor(){
        return color;
    }

}
