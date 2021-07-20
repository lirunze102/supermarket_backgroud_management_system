package com.wzu.lrz.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzu.lrz.dao.BillMapper;
import com.wzu.lrz.dao.ProviderMapper;
import com.wzu.lrz.dao.UserMapper;
import com.wzu.lrz.pojo.Bill;
import com.wzu.lrz.pojo.Provider;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillAction {

    @Autowired
    BillMapper billMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProviderMapper providerMapper;
    private Integer now_page=1,detail_id=1;


    @RequiresPermissions({"ADMIN:UPDATE"})
    @PutMapping("/edit")
    public String editBill(Bill bill){
        billMapper.updateBill(bill);
        System.out.println("成功修改为"+bill.toString());
        return "bill";
    }

    @RequiresPermissions({"ADMIN:EMP:LOOK"})
    @RequestMapping("/detail/{id}")
    public String findBillDetail(@PathVariable String id, Model model){
        System.out.println("查看供应商信息"+id);
        detail_id=Integer.parseInt(id);
        Bill bill=billMapper.getBillById(detail_id);

        bill.setProviderName(providerMapper.getProviderById(bill.getProviderId()).getProName());
        bill.setCreatedByer(userMapper.getUserById(bill.getCreatedBy()).getUserName());
        if(bill.getIsPayment()==1){
            bill.setIsPayment_s("未支付");
        }else {
            bill.setIsPayment_s("已支付");
        }

        List<Provider> providers=providerMapper.getProviderList();
        model.addAttribute("pros",providers);
        model.addAttribute("bill",bill);
        return "bill_detail";
    }

    @RequiresPermissions({"ADMIN:EMP:LOOK"})
    @RequestMapping("/detail_go")
    public String findDetailUpdateGone(Model model){
        Bill bill=billMapper.getBillById(detail_id);
        bill.setProviderName(providerMapper.getProviderById(bill.getProviderId()).getProName());
        bill.setCreatedByer(userMapper.getUserById(bill.getCreatedBy()).getUserName());
        if(bill.getIsPayment()==1){
            bill.setIsPayment_s("未支付");
        }else {
            bill.setIsPayment_s("已支付");
        }

        List<Provider> providers=providerMapper.getProviderList();
        model.addAttribute("pros",providers);
        model.addAttribute("bill",bill);
        return "bill_detail";
    }


    @RequiresPermissions({"ADMIN:UPDATE"})
    @RequestMapping("/update/{id}")
    @ResponseBody
    public Bill findBillById(@PathVariable Integer id, Model model){
        System.out.println("点更新传来的ID" + id);
        Bill bill=billMapper.getBillById(id);
        System.out.println("员工信息"+bill.toString());

        return bill;
    }


    @RequiresPermissions({"ADMIN:DELETE"})
    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String deleteBillById(@PathVariable String ids, Model model){
        System.out.println(ids);
        String[] bill_ids=ids.split(">");
        for (String i:bill_ids
        ) {
            billMapper.deleteById(Integer.parseInt(i));
            System.out.println("成功删除数据id"+i);
        }
        return "bill";
    }


    @RequiresPermissions({"ADMIN:EMP:INSERT"})
    @PostMapping("/add")
    public String addBill(Bill bill){
        bill.setCreationDate(new Date());
        System.out.println("新增账单信息"+bill.toString());

        billMapper.addBill(bill);


        return "bill";
    }


    @RequiresPermissions({"ADMIN:EMP:LOOK"})
    @RequestMapping("/bills")
    public String findAll(Model model,
                          @RequestParam(value = "pn",
                                  defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5); //紧接着的查询会被分页
        now_page=pn;
        List<Bill> bills = billMapper.getBillList();

        PageInfo page = new PageInfo(bills, 5);

        for (Bill bill:bills
             ) {
            bill.setProviderName(providerMapper.getProviderById(bill.getProviderId()).getProName());
            bill.setCreatedByer(userMapper.getUserById(bill.getCreatedBy()).getUserName());
            if(bill.getIsPayment()==1){
                bill.setIsPayment_s("未支付");
            }else {
                bill.setIsPayment_s("已支付");
            }
        }
        List<Provider> providers=providerMapper.getProviderList();
        model.addAttribute("pros",providers);
        model.addAttribute("pageInfo", page);

        return "bill";
    }

    @RequiresPermissions({"ADMIN:EMP:LOOK"})
    @RequestMapping("/bill_go")
    public String findAllGo(Model model){
        PageHelper.startPage(now_page, 5); //紧接着的查询会被分页

        List<Bill> bills = billMapper.getBillList();

        PageInfo page = new PageInfo(bills, 5);
        for (Bill bill:bills
        ) {
            bill.setProviderName(providerMapper.getProviderById(bill.getProviderId()).getProName());
            bill.setCreatedByer(userMapper.getUserById(bill.getCreatedBy()).getUserName());
            if(bill.getIsPayment()==1){
                bill.setIsPayment_s("未支付");
            }else {
                bill.setIsPayment_s("已支付");
            }
        }
        List<Provider> providers=providerMapper.getProviderList();
        model.addAttribute("pros",providers);
        model.addAttribute("pageInfo", page);

        return "bill";
    }


    @RequiresPermissions({"ADMIN:EMP:SEARCH"})
    @RequestMapping("/search/{key}")
    public String getBillByCodeName(@PathVariable String key,Model model,@RequestParam(value = "pn",
            defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5); //紧接着的查询会被分页
        now_page=pn;
        List<Bill> bills = billMapper.getBillListByCodeName(key);
        PageInfo page = new PageInfo(bills, 5);
        for (Bill bill:bills
        ) {
            bill.setProviderName(providerMapper.getProviderById(bill.getProviderId()).getProName());
            bill.setCreatedByer(userMapper.getUserById(bill.getCreatedBy()).getUserName());
            if(bill.getIsPayment()==1){
                bill.setIsPayment_s("未支付");
            }else {
                bill.setIsPayment_s("已支付");
            }
        }
        List<Provider> providers=providerMapper.getProviderList();
        model.addAttribute("pros",providers);
        model.addAttribute("pageInfo", page);
        model.addAttribute("key",key);
        return "bill_search";
    }

}
