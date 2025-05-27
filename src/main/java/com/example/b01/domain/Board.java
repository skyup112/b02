package com.example.b01.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(length =500, nullable = false)
    private String title;

    @Column(length =200, nullable = false)
    private String content;

    @Column(length =50, nullable = false)
    private String writer;

//    Update 기능
    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
