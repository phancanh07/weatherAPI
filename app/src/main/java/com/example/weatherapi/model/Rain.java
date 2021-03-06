
package com.example.weatherapi.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Rain {

    @SerializedName("3h")
    @Expose
    private String _3h;

    public String get3h() {
        return _3h;
    }

    public void set3h(String _3h) {
        this._3h = _3h;
    }

    public Rain with3h(String _3h) {
        this._3h = _3h;
        return this;
    }

}
