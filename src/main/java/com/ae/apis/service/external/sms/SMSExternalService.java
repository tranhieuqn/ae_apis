package com.ae.apis.service.external.sms;

import com.ae.apis.service.external.AbstractExternalService;
import com.ae.apis.service.external.sms.common.SMSProperties;
import com.ae.apis.service.external.sms.dto.SMSRequest;
import com.ae.apis.service.external.sms.dto.SMSResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSExternalService extends AbstractExternalService<SMSRequest, SMSResponse> {
    protected static final Logger logger = LoggerFactory.getLogger(SMSExternalService.class);

    @Autowired
    private SMSProperties properties;

    public SMSResponse sendSMS(String phoneNumber, String content) {
        String areaCode = phoneNumber.substring(0, 3);
        logger.info("areaCode : " + areaCode);

        if ("+84".equals(areaCode)) {
            try {
                return sendESMS(phoneNumber, content);
            } catch (Exception e) {
                logger.error("SMS sending failed.");
                return null;
            }
        }

        logger.warn("Phone numbers must be prefixed with +84");
        logger.warn("SMS was not sent.");
        return null;
    }

    public SMSResponse sendESMS(String phone, String content) throws Exception {
        SMSRequest request = new SMSRequest();
        request.setApiKey(properties.getApiKey());
        request.setSecretKey(properties.getSecretKey());
        request.setBrandname(properties.getBrandName());
        request.setPhone(phone);
        request.setContent(content);
        request.setSmsType("2");
        request.setIsUnicode("1");

        logger.info("Start send sms to phone: {}", phone);
        return callPOST(request);
    }

    @Override
    protected String toError(SMSResponse smsResponse) throws Exception {
        if (smsResponse != null && !"100".equals(smsResponse.getCodeResult())) {
            String message = properties.getError().stream().filter(
                    error -> error.getCode().equals(smsResponse.getCodeResult())
            ).findFirst().orElse(new SMSProperties.Error()).getCode();

            logger.error("Response Code: {}, {}", smsResponse.getCodeResult(), message != null ? message : smsResponse.getErrorMessage());
            return String.format("Response code: %s", smsResponse.getCodeResult());
        }
        return null;
    }

    @Override
    protected String getHostUrl() {
        return properties.getUrl();
    }

    @Override
    protected Class<SMSResponse> getResponseClazz() {
        return SMSResponse.class;
    }
}
