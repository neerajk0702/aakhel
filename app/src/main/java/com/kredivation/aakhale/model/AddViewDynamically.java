package com.kredivation.aakhale.model;

import com.kredivation.aakhale.components.ASTEditText;

public class AddViewDynamically {
    private ASTEditText fullname;
    private ASTEditText email;
    private ASTEditText phoneNo;

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

    public ASTEditText getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(ASTEditText phoneNo) {
        this.phoneNo = phoneNo;
    }
}
