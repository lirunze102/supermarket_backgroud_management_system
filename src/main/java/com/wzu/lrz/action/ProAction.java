package com.wzu.lrz.action;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzu.lrz.dao.ProviderMapper;
import com.wzu.lrz.dao.UserMapper;
import com.wzu.lrz.pojo.Provider;
import com.wzu.lrz.pojo.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/pro")
public class ProAction {
    @Autowired
    ProviderMapper providerMapper;
    @Autowired
    UserMapper userMapper;
    private Integer now_page=1,detail_id=1;

    @RequiresPermissions({"ADMIN:UPDATE"})
    @PutMapping("/edit")
    public String editPro(Provider provider){

        providerMapper.updateProvider(provider);
        System.out.println("成功修改为"+provider.toString());
        return "provider";
    }


    @RequiresPermissions({"ADMIN:UPDATE"})
    @RequestMapping("/update/{id}")
    @ResponseBody
    public Provider findProById(@PathVariable Integer id, Model model){
        System.out.println("点更新传来的ID" + id);
        Provider provider=providerMapper.getProviderById(id);
        System.out.println("员工信息"+provider.toString());

        return provider;
    }



    @RequiresPermissions({"ADMIN:DELETE"})
    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String deleteEmpById(@PathVariable String ids, Model model){

        System.out.println(ids);
        String[] emp_ids=ids.split(">");
        for (String i:emp_ids
        ) {
            providerMapper.deleteById(Integer.parseInt(i));
            System.out.println("成功删除数据id"+i);
        }
        return "provider";
    }


    @RequiresPermissions({"ADMIN:EMP:INSERT"})
    @PostMapping("/add")
    public String addPro(Provider provider){

        provider.setCreationDate(new Date());
        System.out.println("新增供应商信息"+provider.toString());
        providerMapper.addProvider(provider);

        return "provider";
    }

    @RequiresPermissions({"ADMIN:EMP:LOOK"})
    @RequestMapping("/detail/{id}")
    public String findProDetail(@PathVariable String id, Model model){
        System.out.println("查看供应商信息"+id);
        detail_id=Integer.parseInt(id);
        Provider provider=providerMapper.getProviderById(detail_id);
        provider.setCreatedByer(userMapper.getUserById(provider.getCreatedBy()).getUserName());
        model.addAttribute("provider",provider);
        return "provider_detail";
    }

    @RequiresPermissions({"ADMIN:EMP:LOOK"})
    @RequestMapping("/detail_go")
    public String findDetailUpdateGone(Model model){
        Provider provider=providerMapper.getProviderById(detail_id);
        model.addAttribute("provider",provider);
        return "provider_detail";
    }

    @RequiresPermissions({"ADMIN:EMP:LOOK"})
    @RequestMapping("/pros")
    public String findAll(Model model,
                          @RequestParam(value = "pn",
                                  defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5); //紧接着的查询会被分页

        now_page=pn;
        List<Provider> providers = providerMapper.getProviderList();



        PageInfo page = new PageInfo(providers, 5);

        model.addAttribute("pageInfo", page);

        return "provider";
    }

    @RequiresPermissions({"ADMIN:EMP:LOOK"})
    @RequestMapping("/pro_go")
    public String findAll(Model model){
        PageHelper.startPage(now_page, 5); //紧接着的查询会被分页

        List<Provider> providers = providerMapper.getProviderList();

        PageInfo page = new PageInfo(providers, 5);

        model.addAttribute("pageInfo", page);

        return "provider";
    }

    @RequiresPermissions({"ADMIN:EMP:LOOK"})
    @RequestMapping("/search/{key}")
    public String getProByCodeName(@PathVariable String key,Model model,@RequestParam(value = "pn",
            defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5); //紧接着的查询会被分页
        now_page=pn;
        List<Provider> providers = providerMapper.getProviderListByCodeName(key);
        PageInfo page = new PageInfo(providers, 5);

        model.addAttribute("pageInfo", page);
        model.addAttribute("key",key);
        return "provider_search";
    }

}
