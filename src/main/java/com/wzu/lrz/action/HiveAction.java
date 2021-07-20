package com.wzu.lrz.action;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wzu.lrz.dao.HiveMapper;
import com.wzu.lrz.dao.ProviderMapper;
import com.wzu.lrz.dao.UserMapper;
import com.wzu.lrz.pojo.Product;
import com.wzu.lrz.pojo.Provider;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/hive")
public class HiveAction {

    @Autowired
    HiveMapper hiveMapper;
    private Integer now_page=1,detail_id=1;

    @RequiresPermissions({"ADMIN:EMP:INDEX"})
    @PutMapping("/edit")
    public String editPro(Product product){

        hiveMapper.updateProduct(product);
        System.out.println("成功修改为"+product.toString());
        return "index";
    }


    @RequiresPermissions({"ADMIN:EMP:INDEX"})
    @RequestMapping("/update/{id}")
    @ResponseBody
    public Product findProById(@PathVariable Integer id, Model model){
        System.out.println("点更新传来的ID" + id);
        Product product=hiveMapper.getProductById(id);
        System.out.println("商品信息"+product.toString());

        return product;
    }


    @RequestMapping("/buy/{id}")
    @ResponseBody
    public Product findProByIdBuy(@PathVariable Integer id, Model model){

        Product product=hiveMapper.getProductById(id);
        System.out.println("商品信息"+product.toString());

        return product;
    }

    @RequiresPermissions({"ADMIN:EMP:INDEX"})
    @RequestMapping("/delete/{ids}")
    @ResponseBody
    public String deleteProById(@PathVariable String ids, Model model){

        System.out.println(ids);
        String[] pro_ids=ids.split(">");
        for (String i:pro_ids
        ) {
            hiveMapper.deleteById(Integer.parseInt(i));
            System.out.println("成功删除数据id"+i);
        }
        return "index";
    }


    @RequiresPermissions({"ADMIN:EMP:INDEX"})
    @PostMapping("/add")
    public String addPro(Product product){

        System.out.println("新增商品信息"+product.toString());
        hiveMapper.addProduct(product);

        return "index";
    }





    @RequestMapping("/pros")
    public String findAll(Model model,
                          @RequestParam(value = "pn",
                                  defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5); //紧接着的查询会被分页

        now_page=pn;
        List<Product> products = hiveMapper.getProductList();

        PageInfo page = new PageInfo(products, 5);
        model.addAttribute("pageInfo", page);

        return "index";
    }


    @RequestMapping("/pro_go")
    public String findAll(Model model){
        PageHelper.startPage(now_page, 5); //紧接着的查询会被分页

        List<Product> products = hiveMapper.getProductList();

        PageInfo page = new PageInfo(products, 5);
        model.addAttribute("pageInfo", page);

        return "index";
    }


    @RequestMapping("/search/{key}")
    public String getProByCodeName(@PathVariable String key,Model model,@RequestParam(value = "pn",
            defaultValue = "1") Integer pn){
        PageHelper.startPage(pn, 5); //紧接着的查询会被分页
        now_page=pn;
        List<Product> products = hiveMapper.getProductListByName(key);
        PageInfo page = new PageInfo(products, 5);

        model.addAttribute("pageInfo", page);
        model.addAttribute("key",key);
        return "index_search";
    }

}
