/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitterservice.service;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.twitterservice.repository.ApiRepository;
import com.twitterservice.dao.ApiDomain;
import com.twitterservice.dao.TwitterDomain;
import com.twitterservice.repository.TwitterRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@RestController
public class ApiRestService {

    private static final Logger logger = LoggerFactory.getLogger(ApiRestService.class);

    @Autowired(required = true)
    ApiRepository ApiRepo;
    @Autowired(required = true)
    TwitterRepository TwitRepo;

    @RequestMapping(value = "/rest/twitterapi", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<ApiDomain> saveApiKeys(@RequestBody ApiDomain request) {
        ApiRepo.save(request);
        logger.info("AccontName: " + request.getAccountName());
        return new ResponseEntity<ApiDomain>(request, HttpStatus.OK);

    }
    
    @RequestMapping(value = "/rest/update", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<ApiDomain> updateApiKeys(@RequestBody ApiDomain request) {
        logger.info("Deleted Profile"+ApiRepo.findByAccountName(request.getAccountName()));
        ApiRepo.removeByAccountName(request.getAccountName());
        ApiRepo.save(request);
        logger.info("New Profile"+request);
        return new ResponseEntity<ApiDomain>(request, HttpStatus.OK);

    }

    @RequestMapping(value = "/rest/api/list", method = RequestMethod.GET)
    public Iterable<ApiDomain> listAllApi() {
        Iterable<ApiDomain> Lst = ApiRepo.findAll();

        return Lst;
    }
    

    @RequestMapping(value = "/rest/twitter/{name}", method = RequestMethod.GET)
    @SuppressWarnings("empty-statement")
    public Iterable<TwitterDomain> getAll(@PathVariable("name") String name) throws TwitterException {
//        ApiDomain apikeys = ApiRepo.findByAccessTokenSecret(name);
        ConfigurationBuilder xb = new ConfigurationBuilder();
        for (ApiDomain apikeys : ApiRepo.findByAccountName(name)) {
            xb.setDebugEnabled(true)
                    .setOAuthConsumerKey(apikeys.getConsumerKey())
                    .setOAuthConsumerSecret(apikeys.getConsumerSecret())
                    .setOAuthAccessToken(apikeys.getAccessToken())
                    .setOAuthAccessTokenSecret(apikeys.getAccessTokenSecret());
        }
        TwitterFactory tf = new TwitterFactory(xb.build());
        twitter4j.Twitter tw = tf.getInstance();
        List<Status> statuses = tw.getHomeTimeline();
        for (Status a : statuses) {

            TwitRepo.save(new TwitterDomain(a.getUser().getName(), a.getText(), ""));

        }

        Iterable<TwitterDomain> lst = TwitRepo.findAll();

        return lst;
    }
}
