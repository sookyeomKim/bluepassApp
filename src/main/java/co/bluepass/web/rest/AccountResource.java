package co.bluepass.web.rest;

import com.codahale.metrics.annotation.Timed;

import co.bluepass.domain.Authority;
import co.bluepass.domain.Club;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.PartnerRequest;
import co.bluepass.domain.RegisterStatus;
import co.bluepass.domain.TicketHistory;
import co.bluepass.domain.User;
import co.bluepass.domain.Zip;
import co.bluepass.proxy.SmsService;
import co.bluepass.repository.AuthorityRepository;
import co.bluepass.repository.CommonCodeRepository;
import co.bluepass.repository.PartnerRequestRepository;
import co.bluepass.repository.TicketHistoryRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.repository.ZipRepository;
import co.bluepass.security.AuthoritiesConstants;
import co.bluepass.security.SecurityUtils;
import co.bluepass.service.MailService;
import co.bluepass.service.UserService;
import co.bluepass.web.rest.dto.PartnerRequestDTO;
import co.bluepass.web.rest.dto.RegisterUserDTO;
import co.bluepass.web.rest.dto.UserDTO;
import co.bluepass.web.rest.dto.UserTypeChangeForm;
import co.bluepass.web.rest.util.PaginationUtil;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.*;

/**
 * The type Account resource.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private UserService userService;

    @Inject
    private MailService mailService;

    @Inject
    private PartnerRequestRepository partnerRequestRepository;

    @Inject
    private CommonCodeRepository commonCodeRepository;

    @Inject
    private ZipRepository zipRepository;

    @Inject
    private TicketHistoryRepository ticketHistoryRepository;

    /**
     * Register account response entity.
     *
     * @param userDTO the user dto
     * @param request the request
     * @return the response entity
     */
    @RequestMapping(value = "/register",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> registerAccount(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        User user = userRepository.findOneByEmail(userDTO.getEmail());
        if (user != null) {
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("이미 존재하는 이메일입니다.");
        } else {

            CommonCode ticket = null;
            CommonCode favorCategory = null;
            CommonCode jacketSize = null;
            CommonCode pantsSize = null;
            Zip favorSite = null;

            if (userDTO.getTicketId() != null) {
                ticket = commonCodeRepository.findOne(userDTO.getTicketId());
            }

            if (userDTO.getFavorSiteId() != null) {
                favorSite = zipRepository.findOne(userDTO.getFavorSiteId());
            }

            if (userDTO.getFavorCategoryId() != null && !userDTO.getFavorCategoryId().isEmpty()) {
                favorCategory = commonCodeRepository.findOne(userDTO.getFavorCategoryId().get(0));
            }

            if (userDTO.getJacketSizeId() != null) {
                jacketSize = commonCodeRepository.findOne(userDTO.getJacketSizeId());
            }

            if (userDTO.getPantsSizeId() != null) {
                pantsSize = commonCodeRepository.findOne(userDTO.getPantsSizeId());
            }

            user = userService.createUserInformation(userDTO.getEmail()
                    .toLowerCase(), userDTO.getPassword(), userDTO.getName(),
                userDTO.getLangKey(), userDTO.getZipcode(), userDTO.getAddress1(), userDTO.getAddress2(),
                userDTO.getAge(), userDTO.getGender(),
                ticket, favorSite, favorCategory, jacketSize, pantsSize);

            String baseUrl = request.getScheme() + // "http"
                "://" +                            // "://"
                request.getServerName() +          // "myhost"
                ":" +                              // ":"
                request.getServerPort();           // "80"

            mailService.sendActivationEmail(user, baseUrl);

            SmsService.sendSms("Bluepass 회원으로 가입하신 것을 환영합니다.", user);

            //회원권 종류 구분
            if (ticket != null) {
                TicketHistory ticketHistory = new TicketHistory(user, ticket, DateTime.now());
                ticketHistoryRepository.save(ticketHistory);
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * Add register info response entity.
     *
     * @param userDTO the user dto
     * @param request the request
     * @return the response entity
     */
    @RequestMapping(value = "/register/add",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> addRegisterInfo(@Valid @RequestBody RegisterUserDTO userDTO, HttpServletRequest request) {
        User user = userRepository.findOne(userDTO.getId());
        if (user == null) {
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("존재하지 않는 사용자입니다.");
        } else {
            CommonCode ticket = null;
            CommonCode favorCategory = null;
            CommonCode jacketSize = null;
            CommonCode pantsSize = null;
            Zip favorSite = null;

            if (userDTO.getTicketId() != null) {
                ticket = commonCodeRepository.findOne(userDTO.getTicketId());
            }

            if (userDTO.getFavorSiteId() != null) {
                favorSite = zipRepository.findOne(userDTO.getFavorSiteId());
            }

            if (userDTO.getFavorCategoryId() != null && userDTO.getFavorCategoryId().length > 0) {
                favorCategory = commonCodeRepository.findOne(userDTO.getFavorCategoryId()[0]);
            }

            if (userDTO.getJacketSizeId() != null) {
                jacketSize = commonCodeRepository.findOne(userDTO.getJacketSizeId());
            }

            if (userDTO.getPantsSizeId() != null) {
                pantsSize = commonCodeRepository.findOne(userDTO.getPantsSizeId());
            }

            RegisterStatus registerStatus = null;
            if (ticket == null) {
                registerStatus = RegisterStatus.미등록;
            } else {
                registerStatus = RegisterStatus.등록요청;
            }

            user.update(userDTO.getName(), userDTO.getPhoneNumber(), userDTO.getZipcode(), userDTO.getAddress1(), userDTO.getAddress2(),
                userDTO.getAge(), userDTO.getGender(), favorSite, favorCategory,
                jacketSize, pantsSize, userDTO.getExersizeCount(), registerStatus);

            userRepository.save(user);

            //회원권 종류 구분
            if (ticket != null) {
                TicketHistory ticketHistory = new TicketHistory(user, ticket, DateTime.now());
                ticketHistoryRepository.save(ticketHistory);
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * Activate account response entity.
     *
     * @param key the key
     * @return the response entity
     */
    @RequestMapping(value = "/activate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") String key) {
        User user = userService.activateRegistration(key);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    /**
     * Is authenticated string.
     *
     * @param request the request
     * @return the string
     */
    @RequestMapping(value = "/authenticate",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * Gets account.
     *
     * @return the account
     */
    @RequestMapping(value = "/account",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        User user = userService.getUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<String> roles = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            roles.add(authority.getName());
        }

        TicketHistory ticketHistory = ticketHistoryRepository.findTop1ByUserAndActivatedAndClosed(user, true, false);
        if (ticketHistory == null) {
            ticketHistory = ticketHistoryRepository.findTop1ByUserAndActivatedAndClosed(user, false, false);
        }
        CommonCode ticket = null;
        if (ticketHistory != null) {
            ticket = ticketHistory.getTicket();
        }

        List<Long> favorCategories = null;
        if (user.getFavorCategory() != null) {
            favorCategories = new ArrayList<Long>();
            favorCategories.add(user.getFavorCategory().getId());
        }

        return new ResponseEntity<>(
            new UserDTO(user.getId()
                , null
                , user.getName()
                , user.getEmail()
                , user.getLangKey()
                , roles
                , user.getRegisterStatus()
                , user.getZipcode()
                , user.getAddress1()
                , user.getAddress2()
                , user.getAge()
                , user.getGender()
                , user.getPhoneNumber()
                , user.getExerciseCount()
                , (user.getFavorSite() == null ? null : user.getFavorSite().getId())
                , favorCategories
                , (ticket == null ? null : ticket.getId())
            ),
            HttpStatus.OK);
    }

    /**
     * Save account response entity.
     *
     * @param userDTO the user dto
     * @param request the request
     * @return the response entity
     */
    @RequestMapping(value = "/account",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> saveAccount(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        User userHavingThisLogin = userRepository.findOneByEmail(userDTO.getEmail());
        if (userHavingThisLogin != null && !userHavingThisLogin.getEmail().equals(SecurityUtils.getCurrentLogin())) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = userRepository.findOne(userDTO.getId());

        if (user == null) {
            return ResponseEntity.badRequest().contentType(MediaType.TEXT_PLAIN).body("존재하지 않는 사용자입니다.");
        } else {
            CommonCode favorCategory = null;
            CommonCode jacketSize = null;
            CommonCode pantsSize = null;
            Zip favorSite = null;

            if (userDTO.getFavorSiteId() != null) {
                favorSite = zipRepository.findOne(userDTO.getFavorSiteId());
            }

            if (userDTO.getFavorCategoryId() != null && !userDTO.getFavorCategoryId().isEmpty()) {
                // TODO 선호운동종목을 하나만 저장하게 되어있음 -> 다중저장으로 변경해야 함

    			/*for (Long favorCategoryId : userDTO.getFavorCategoryId()) {
                    favorCategory = commonCodeRepository.findOne(userDTO.getFavorCategoryId());

				}*/
                favorCategory = commonCodeRepository.findOne(userDTO.getFavorCategoryId().get(0));
            }

            if (userDTO.getJacketSizeId() != null) {
                jacketSize = commonCodeRepository.findOne(userDTO.getJacketSizeId());
            }

            if (userDTO.getPantsSizeId() != null) {
                pantsSize = commonCodeRepository.findOne(userDTO.getPantsSizeId());
            }

            user.setEmail(userDTO.getEmail());

            user.update(userDTO.getName(), userDTO.getPhoneNumber(), userDTO.getZipcode(), userDTO.getAddress1(), userDTO.getAddress2(),
                userDTO.getAge(), userDTO.getGender(), favorSite, favorCategory,
                jacketSize, pantsSize, userDTO.getExersizeCount(), user.getRegisterStatus());

            userRepository.save(user);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * Change password response entity.
     *
     * @param password the password
     * @return the response entity
     */
    @RequestMapping(value = "/account/change_password",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody String password) {
        if (StringUtils.isEmpty(password) || password.length() < 5 || password.length() > 50) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Request password reset response entity.
     *
     * @param mail    the mail
     * @param request the request
     * @return the response entity
     */
    @RequestMapping(value = "/account/reset_password/init",
        method = RequestMethod.POST,
        produces = MediaType.TEXT_PLAIN_VALUE)
    @Timed
    public ResponseEntity<?> requestPasswordReset(@RequestBody String mail, HttpServletRequest request) {

        User user = userService.requestPasswordReset(mail);

        if (user != null) {
            String baseUrl = request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort();
            mailService.sendPasswordResetMail(user, baseUrl);
            return new ResponseEntity<>("e-mail was sent", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("e-mail address not registered", HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Finish password reset response entity.
     *
     * @param key         the key
     * @param newPassword the new password
     * @return the response entity
     */
    @RequestMapping(value = "/account/reset_password/finish",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestParam(value = "key") String key, @RequestParam(value = "newPassword") String newPassword) {
        User user = userService.completePasswordReset(newPassword, key);
        if (user != null) {
            return new ResponseEntity<String>(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Change type response entity.
     *
     * @param form the form
     * @return the response entity
     */
    @RequestMapping(value = "/account/change_type",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> changeType(
        @Valid @RequestBody UserTypeChangeForm form) {
        if (StringUtils.isEmpty(form.getRequestType())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (authorityRepository.countByName(form.getRequestType()) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());

        if (user == null) {
            return ResponseEntity.badRequest().header("Failure", "인증된 사용자가 아닙니다.").build();
        }

        Authority authority = authorityRepository.findOne(form.getRequestType());
        User targetUser = userRepository.findOne(form.getUserId());

        if (form.getRemove() != null && form.getRemove()) {
            targetUser.getAuthorities().remove(authority);
        } else {
            targetUser.getAuthorities().add(authority);
        }

        if (AuthoritiesConstants.REGISTER.equals(authority.getName())) {
            if (form.getRemove() != null && form.getRemove()) {
                targetUser.setRegisterStatus(RegisterStatus.미등록);
            } else {
                targetUser.setRegisterStatus(RegisterStatus.등록);
            }
        }

        userRepository.saveAndFlush(targetUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Request partner response entity.
     *
     * @param partnerRequest the partner request
     * @return the response entity
     * @throws URISyntaxException the uri syntax exception
     */
    @RequestMapping(value = "/account/request",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> requestPartner(@Valid @RequestBody PartnerRequest partnerRequest) throws URISyntaxException {
        log.debug("REST request to save PartnerRequest : {}", partnerRequest);
        if (partnerRequest.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new partner request cannot already have an ID").build();
        }
        partnerRequestRepository.save(partnerRequest);
        return ResponseEntity.created(new URI("/api/account/request" + partnerRequest.getId())).build();
    }

}
