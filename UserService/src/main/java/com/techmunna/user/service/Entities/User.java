package com.techmunna.user.service.Entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name="micro_users")
    @Builder
    public class User {

        @Id
        @Column(name = "ID")
        private String userId;

        @Column(name = "NAME", length = 20)
        private  String name;

        @Column(name = "EMAIL")
        private String email;

        @Column(name = "ABOUT")
        private String about;

        //other user  properties that you want

        @Transient
        private List<Rating> ratings=new ArrayList<>();
    }
