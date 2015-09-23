/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitterservice.repository;




import com.twitterservice.dao.ApiDomain;
import java.util.List;


import org.springframework.data.repository.CrudRepository;

public interface ApiRepository extends CrudRepository<ApiDomain, Long> {

    List<ApiDomain> findByAccountName(String accountName);
    List<ApiDomain> removeByAccountName(String accountName);
}