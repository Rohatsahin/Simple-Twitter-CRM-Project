/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitterservice.repository;

import com.twitterservice.dao.TwitterDomain;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TwitterRepository extends CrudRepository<TwitterDomain, Long> {

    List<TwitterDomain> findByTwittertext(String twittertext);
}