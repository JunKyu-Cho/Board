package com.board.basic.domain;

import lombok.Data;

@Data
public class Member {
    private String id;
    private String password;
    private String name;
    private String birthDate;
}
