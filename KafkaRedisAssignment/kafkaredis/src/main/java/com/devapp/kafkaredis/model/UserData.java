package com.devapp.kafkaredis.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserData implements Serializable {
    private String id;
    private String name;
    private String email;
}
