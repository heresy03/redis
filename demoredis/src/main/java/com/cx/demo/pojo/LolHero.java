package com.cx.demo.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LolHero implements Serializable {
    private Integer id;
    private String ukName;
    private String nickname;
    private String tinyImg;
    private Integer attackPower;
    private Integer handHard;
}
