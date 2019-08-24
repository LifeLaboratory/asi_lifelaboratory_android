package ru.lifelaboratory.asi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterForDocument {

    Integer idUser;
    Integer idProject;
    Integer idDocument;
}
