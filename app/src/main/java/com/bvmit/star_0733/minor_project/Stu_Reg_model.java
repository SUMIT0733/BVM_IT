package com.bvmit.star_0733.minor_project;

public class Stu_Reg_model {
    String name,email,pass,batch,mobile,enroll;

    public Stu_Reg_model() { }

    public Stu_Reg_model(String name, String email, String pass, String batch, String mobile, String enroll) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.batch = batch;
        this.mobile = mobile;
        this.enroll = enroll;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getBatch() {
        return batch;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEnroll() {
        return enroll;
    }
}
