package com.example.application.model.bean;

public class User {

    private Integer funcionario_id;
    private String user;
    private String pass;
    private String token;
    private Integer first_access;



    public Integer getFuncionario_id() {return funcionario_id;}

    public void setFuncionario_id(Integer funcionario_id) {this.funcionario_id = funcionario_id;}

    public Integer getFirst_access() {return first_access;}

    public void setFirst_access(Integer first_access) {this.first_access = first_access;}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}
