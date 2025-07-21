package com.cas.ws.io.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class TrxHistory {
    //ACCOUNT_NUMBER|TRX_AMOUNT|DESCRIPTION|TRX_DATE|TRX_TIME|CUSTOMER_ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private Long id;
    private String ACCOUNT_NUMBER;
    private String TRX_AMOUNT;
    private String DESCRIPTION;
    private String TRX_DATE;
    private String TRX_TIME;
    private String CUSTOMER_ID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getACCOUNT_NUMBER() {
        return ACCOUNT_NUMBER;
    }

    public void setACCOUNT_NUMBER(String ACCOUNT_NUMBER) {
        this.ACCOUNT_NUMBER = ACCOUNT_NUMBER;
    }

    public String getTRX_AMOUNT() {
        return TRX_AMOUNT;
    }

    public void setTRX_AMOUNT(String TRX_AMOUNT) {
        this.TRX_AMOUNT = TRX_AMOUNT;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getTRX_DATE() {
        return TRX_DATE;
    }

    public void setTRX_DATE(String TRX_DATE) {
        this.TRX_DATE = TRX_DATE;
    }

    public String getTRX_TIME() {
        return TRX_TIME;
    }

    public void setTRX_TIME(String TRX_TIME) {
        this.TRX_TIME = TRX_TIME;
    }

    public String getCUSTOMER_ID() {
        return CUSTOMER_ID;
    }

    public void setCUSTOMER_ID(String CUSTOMER_ID) {
        this.CUSTOMER_ID = CUSTOMER_ID;
    }
    /*
    @Id
    private String accountNo;
    private String txnAmount;
    private String description;
    private String txnDate;
    private String txnTime;
    private String customerId;


    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

 */
}
