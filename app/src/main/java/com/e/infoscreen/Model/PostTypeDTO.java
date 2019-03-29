package com.e.infoscreen.Model;

import java.util.Date;

public class PostTypeDTO {
    public String name;
    public Date created;

    public PostTypeDTO(String name, Date created){
        this.name = name;
        this.created = created;
    }
}
