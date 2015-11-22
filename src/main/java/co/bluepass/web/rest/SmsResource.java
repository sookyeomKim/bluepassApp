package co.bluepass.web.rest;

import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.bluepass.proxy.SmsService;

import com.codahale.metrics.annotation.Timed;

/**
 * The type Sms resource.
 */
@RestController
@RequestMapping("/api")
public class SmsResource {

    private final Logger log = LoggerFactory.getLogger(SmsResource.class);

    /**
     * Gets all.
     *
     * @param message     the message
     * @param phoneNumber the phone number
     * @param name        the name
     * @return the all
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/sms",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> getAll(@RequestParam(value = "message" , required = true) String message,
                                  @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
                                  @RequestParam(value = "name", required = true) String name
                                  )
        throws URISyntaxException {

    	SmsService.sendSms(message, phoneNumber, name);

    	return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
