package com.kredivation.aakhale.model;

import android.widget.ImageView;

import com.kredivation.aakhale.components.ASTEditText;

import java.io.File;

public class AddViewDynamically {
    private ASTEditText fullname;
    private ASTEditText email;
    private ASTEditText phoneNo;
    private ASTEditText addEditText;
    private ASTEditText degiganationEditText;
    private ImageView imageView;
    public ASTEditText getFullname() {
        return fullname;
    }

    public void setFullname(ASTEditText fullname) {
        this.fullname = fullname;
    }

    public ASTEditText getEmail() {
        return email;
    }

    public void setEmail(ASTEditText email) {
        this.email = email;
    }

    public ASTEditText getAddEditText() {
        return addEditText;
    }

    public void setAddEditText(ASTEditText addEditText) {
        this.addEditText = addEditText;
    }

    public ASTEditText getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(ASTEditText phoneNo) {
        this.phoneNo = phoneNo;
    }

    public ASTEditText getDegiganationEditText() {
        return degiganationEditText;
    }

    public void setDegiganationEditText(ASTEditText degiganationEditText) {
        this.degiganationEditText = degiganationEditText;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
    private File imageFile;

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}
