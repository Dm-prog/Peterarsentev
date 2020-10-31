package ru.job4j.bank;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BankService {
    private Map<User, List<Account>> users = new HashMap<>();
    public void addUser(User user) {
        users.putIfAbsent(user, new ArrayList<>());
    }

    public void addAccount(String passport, Account account) {
        User user = findByPassport(passport);
        if (user != null) {
            List<Account> list = users.get(user);
            if (!list.contains(account)) {
                users.get(user).add(account);
            }
        }
    }

    public User findByPassport(String passport) {
//        for (User user : users.keySet())
//            if (user.getPassport().equals(passport)) {
//                return user;
//            }

        return users.entrySet().stream()
                .filter(x-> x.getKey().getPassport().equals(passport))
                .map(x->x.getValue()).collect(Collectors.groupingBy(User::getPassport));
//        return null;
    }

    public Account findByRequisite(String passport, String requisite) {
        User user = findByPassport(passport);
        Account result = null;
//        if (user != null) {
//            for (Account account : users.get(user)) {
//                if (account.getRequisite().equals(requisite)) {
//                    result = account;
//                    break;
//                }
//            }
//        }
        users.entrySet().stream()
//                .flatMap(Stream::ofNullable)
                .flatMap(list -> list.getValue().stream())
                .filter(userRequisite -> userRequisite.getRequisite().equals(requisite));
        return result;
    }

    public boolean transferMoney(String srcPassport, String srcRequisite,
                                 String destPassport, String destRequisite, double amount) {
        boolean rsl = false;
        Account srcAccount = findByRequisite(srcPassport, srcRequisite);
        Account destAccount = findByRequisite(destPassport, destRequisite);
        if (srcAccount != null && srcAccount.getBalance() >= amount && destAccount != null) {
            srcAccount.setBalance(srcAccount.getBalance() - amount);
            destAccount.setBalance(destAccount.getBalance() + amount);
            rsl = true;
        }
        return rsl;
    }
}
