/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitterservice.service;

import com.twitterservice.repository.TwitterRepository;
import com.twitterservice.dao.TwitterDomain;
import com.twitterservice.repository.ApiRepository;
import java.io.File;
import java.util.Date;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwitterRestService {


    @Autowired(required = true)
    TwitterRepository TwitterRepo;
    ApiRepository ApiRepo;

    @RequestMapping(value = "/rest/twit/list", method = RequestMethod.GET)
    public Iterable<TwitterDomain> listAllTwit() {
        Iterable<TwitterDomain> customerLst = TwitterRepo.findAll();
        
        return customerLst;
    }

    @RequestMapping(value = "/rest/twit/list/{id}/{status}", method = RequestMethod.GET)
    public TwitterDomain replaceTwit(@PathVariable("id") Long id, @PathVariable("status") String status) {
        TwitterDomain customer = TwitterRepo.findOne(id);

        String hibernatePropsFilePath = "C:\\Users\\rohat\\Documents\\NetBeansProjects\\TwitterService\\src\\main\\resources\\hibernate.cfg.xml";
        File hibernatePropsFile = new File(hibernatePropsFilePath);
        Configuration configuration = new Configuration();
        configuration.configure(hibernatePropsFile);
        StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
        SessionFactory sessionfactory = configuration.buildSessionFactory(serviceRegistry);
        Session session = sessionfactory.openSession();
        session.beginTransaction();

        if ("".equals(customer.getStatus())) {

            customer.setStatus(status);
            TwitterRepo.save(customer);
            session.save(customer);
            session.getTransaction().commit();
            session.close();

        } else {

            Criteria criteria = session.createCriteria(TwitterDomain.class);
            criteria.add(Restrictions.eq("twittertext", customer.getTwittertext()));

            TwitterDomain twitter = (TwitterDomain) criteria.uniqueResult();
            twitter.setStatus(status);
            session.update(twitter);
            session.getTransaction().commit();
            session.close();

        }
        return customer;
    }

    @RequestMapping(value = "/rest/time", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity serverTime() {
        return new ResponseEntity("ServerTime : " + new Date(), HttpStatus.OK);
    }

}
