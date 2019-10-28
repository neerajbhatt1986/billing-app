package com.billing.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CategoryRequest implements Serializable {

    @NotBlank
    String name;
    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
