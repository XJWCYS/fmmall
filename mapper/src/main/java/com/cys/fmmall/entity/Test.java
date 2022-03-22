package com.cys.fmmall.entity;

import javax.persistence.*;

public class Test {
    @Id
    private Integer id;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}