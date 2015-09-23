/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twitterservice.dao;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name="twitter")
public class TwitterDomain implements Serializable {

    @Id
    @GenericGenerator(name="generator", strategy="increment")
    @GeneratedValue(generator="generator")
    @Column(name = "ID")
    private long id;
    @Column(name = "twitname")
    private String twitteruser;
    @Column(name = "twittext")
    private String twittertext;
    @Column(name = "status")
    private String status;

    public String getTwitteruser() {
        return twitteruser;
    }

    public void setTwitteruser(String twitteruser) {
        this.twitteruser = twitteruser;
    }

    public String getTwittertext() {
        return twittertext;
    }

    public void setTwittertext(String twittertext) {
        this.twittertext = twittertext;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public TwitterDomain(String twitteruser, String twittertext, String status) {
        this.twitteruser = twitteruser;
        this.twittertext = twittertext;
        this.status = status;
    }

    public TwitterDomain() {
    }
    
    

}
