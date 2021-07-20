package com.wzu.lrz.dao;

import com.wzu.lrz.pojo.Provider;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProviderMapper {
    List<Provider> getProviderList();
    List<Provider> getProviderListByCodeName(String key);
    void addProvider(Provider provider);
    void updateProvider(Provider provider);
    Provider getProviderById(Integer id);
    void deleteById(Integer id);


}
