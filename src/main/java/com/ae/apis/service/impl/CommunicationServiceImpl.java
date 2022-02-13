package com.ae.apis.service.impl;

import com.ae.apis.service.CommunicationService;
import com.ae.apis.service.external.SMSExternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunicationServiceImpl implements CommunicationService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SMSExternalService smsService;

    @Override
    public void sendSMS(String phoneNumber, String content) {
        smsService.sendSMS(phoneNumber, content);
    }

}
