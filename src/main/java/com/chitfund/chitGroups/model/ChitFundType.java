package com.chitfund.chitGroups.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chit_fund_type")
public class ChitFundType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fund_type_id")
    private Long fundTypeId;
    @Column(name = "fund_type_name", unique = true, nullable = false, length = 500)
    private String fundTypeName;
    @Column(name = "description", length = 500)
    private String description;

}
