package com.kk.service;

import com.kk.pojo.Account;

import java.util.List;

public interface AccountService {
    /**
     * 添加账户
     */
    public abstract int addAccount(Account account);

    /**
     * 删除账户
     */
    public abstract int deleteAccount(Integer aid);

    /**
     * 修改账户
     */
    public abstract int alterAccount(Account account);

    /**
     * 查询全部账户
     */
    public abstract List<Account> findAllAccounts();
}
