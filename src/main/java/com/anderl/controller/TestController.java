package com.anderl.controller;

import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by dasanderl on 07.09.14.
 */
@Component
@SessionScoped
public class TestController {

    private int field = 1;

    public int getField() {
        return field;
    }

    public void setField(int field) {
        this.field = field;
    }
}
