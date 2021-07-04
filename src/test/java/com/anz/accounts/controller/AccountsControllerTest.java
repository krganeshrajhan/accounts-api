package com.anz.accounts.controller;

import com.anz.accounts.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void shouldReturnErrorResponseWhenGetAccountsCallWithInvalidUserId() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/accounts/2")).andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.message").value("Invalid User ID: 2")).andReturn();
    }

    @Test
    @Sql("/create_accounts.sql")
    public void shouldReturnResponseForValidRequestId() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/accounts/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.meta.totalPages").value(1))
                .andExpect(jsonPath("$.meta.totalItems").value(1))
                .andExpect(jsonPath("$.data.[0].accountNumber").value("123456"))
                .andExpect(jsonPath("$.data.[0].accountName").value("SGDEBITAC"))
                .andExpect(jsonPath("$.data.[0].accountType").value("SAVINGS"))
                .andExpect(jsonPath("$.data.[0].balanceDate").value("04/07/2021"))
                .andExpect(jsonPath("$.data.[0].currency").value("SGD"))
                .andExpect(jsonPath("$.data.[0].openingAvailableBalance").value(4234.50))
                .andReturn();
    }

    @Test
    @Sql("/create_transactions.sql")
    public void shouldReturnPagedResponse() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/accounts/1?pageStart=0&pageSize=1")).andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.totalPages").value(3))
                .andExpect(jsonPath("$.meta.totalItems").value(3))
                .andReturn();
    }

    @Test
    public void givenInvalidAccountIdThrowErrorResponse() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/transactions/1")).andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.message").value("Invalid Account Number: 1"))
                .andExpect(jsonPath("$.meta.code").value("400")).andReturn();
    }

    @Test
    @Sql("/create_transactions.sql")
    public void givenValidAccountIdReturnTransactions() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/transactions/134567")).andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.message").value("success"))
                .andExpect(jsonPath("$.meta.code").value("200"))
                .andExpect(jsonPath("$.meta.totalPages").value("1"))
                .andExpect(jsonPath("$.meta.totalItems").value("2"))
                .andExpect(jsonPath("$.data.[0].accountNumber").value("134567"))
                .andExpect(jsonPath("$.data.[0].accountName").value("SGDEBITAC"))
                .andExpect(jsonPath("$.data.[0].valueDate").value("Jul, 04, 2021"))
                .andExpect(jsonPath("$.data.[0].currency").value("SGD"))
                .andExpect(jsonPath("$.data.[0].debitAmount").value(0))
                .andExpect(jsonPath("$.data.[0].creditAmount").value(20.56))
                .andExpect(jsonPath("$.data.[0].type").value("Credit"))
                .andExpect(jsonPath("$.data.[0].narrative").value(""))
                .andReturn();
    }

    @Test
    @Sql("/create_transactions.sql")
    public void givenValidAccountReturnPagedResponse() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/transactions/134567?pageStart=0&pageSize=1")).andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.meta.message").value("success"))
                .andExpect(jsonPath("$.meta.code").value("200"))
                .andExpect(jsonPath("$.meta.totalPages").value("2"))
                .andExpect(jsonPath("$.meta.totalItems").value("2"))
                .andReturn();
    }

}
