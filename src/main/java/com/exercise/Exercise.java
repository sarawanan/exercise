package com.exercise;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Exercise {
    @Id
    @CsvBindByName
    private String code;
    @CsvBindByName
    private String source;
    @CsvBindByName
    private String codeListCode;
    @CsvBindByName
    private String displayValue;
    @CsvBindByName
    private String longDescription;
    @CsvBindByName
    private String fromDate;
    @CsvBindByName
    private String toDate;
    @CsvBindByName
    private int sortingPriority;
}
