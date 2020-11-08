package com.example.hethongthuenha.Model;

import java.util.List;

public class Room {
    private int room_id;
    private Description_Room stage1;
    private LivingExpenses_Room stage2;
    private Image_Room stage3;
    private Utilities_Room stage4;

    public Room(int room_id, Description_Room stage1, LivingExpenses_Room stage2, Image_Room stage3, Utilities_Room stage4) {
        this.room_id = room_id;
        this.stage1 = stage1;
        this.stage2 = stage2;
        this.stage3 = stage3;
        this.stage4 = stage4;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public Description_Room getStage1() {
        return stage1;
    }

    public void setStage1(Description_Room stage1) {
        this.stage1 = stage1;
    }

    public LivingExpenses_Room getStage2() {
        return stage2;
    }

    public void setStage2(LivingExpenses_Room stage2) {
        this.stage2 = stage2;
    }

    public Image_Room getStage3() {
        return stage3;
    }

    public void setStage3(Image_Room stage3) {
        this.stage3 = stage3;
    }

    public Utilities_Room getStage4() {
        return stage4;
    }

    public void setStage4(Utilities_Room stage4) {
        this.stage4 = stage4;
    }
}
