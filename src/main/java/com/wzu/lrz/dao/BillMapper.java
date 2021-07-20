package com.wzu.lrz.dao;

import com.wzu.lrz.pojo.Bill;
import com.wzu.lrz.pojo.Provider;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BillMapper {

    List<Bill> getBillList();
    List<Bill> getBillListByCodeName(String key);
    void addBill(Bill bill);
    void updateBill(Bill bill);
    Bill getBillById(Integer id);
    void deleteById(Integer id);

}
