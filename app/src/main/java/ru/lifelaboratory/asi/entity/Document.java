package ru.lifelaboratory.asi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Document {

    Integer id;
    Integer[] idProjects;
    String url;
    String title;
    Integer type;
    String logo;
    String description;
}
