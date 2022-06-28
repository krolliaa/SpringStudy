package com.kk.service.impl;

import com.kk.mapper.AccountMapper;
import com.kk.pojo.Account;
import com.kk.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public int addAccount(Account account) {
        return accountMapper.insert(account);
    }

    @Override
    public int deleteAccount(Integer aid) {
        return accountMapper.deleteByPrimaryKey(aid);
    }

    @Override
    public int alterAccount(Account account) {
        return accountMapper.updateByExample(account, null);
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountMapper.selectByExample(null);
    }
}
