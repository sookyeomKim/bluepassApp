package co.bluepass.service;

import co.bluepass.domain.Authority;
import co.bluepass.domain.CommonCode;
import co.bluepass.domain.RegisterStatus;
import co.bluepass.domain.User;
import co.bluepass.domain.Zip;
import co.bluepass.repository.AuthorityRepository;
import co.bluepass.repository.UserRepository;
import co.bluepass.security.SecurityUtils;
import co.bluepass.service.util.RandomUtil;
import co.bluepass.web.rest.dto.UserDTO;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The type User service.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    /**
     * Activate registration user.
     *
     * @param key the key
     * @return the user
     */
    public  User activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        User user = userRepository.findOneByActivationKey(key);
        // activate given user for the registration key.
        if (user != null) {
            user.setActivated(true);
            user.setActivationKey(null);
            userRepository.save(user);
            log.debug("Activated user: {}", user);
        }
        return user;
    }

    /**
     * Complete password reset user.
     *
     * @param newPassword the new password
     * @param key         the key
     * @return the user
     */
    public User completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);
        User user = userRepository.findOneByResetKey(key);
        DateTime oneDayAgo = DateTime.now().minusHours(24);
        if (user != null) {
            if (user.getResetDate().isAfter(oneDayAgo.toInstant().getMillis())) {
                user.setActivated(true);
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                userRepository.save(user);
                return user;
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * Request password reset user.
     *
     * @param mail the mail
     * @return the user
     */
    public User requestPasswordReset(String mail) {
        User user = userRepository.findOneByEmail(mail);
            if (user != null) {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(DateTime.now());
                userRepository.save(user);
                return user;
            }
        return user;
    }

    /**
     * Create user information user.
     *
     * @param email         the email
     * @param password      the password
     * @param name          the name
     * @param langKey       the lang key
     * @param zipcode       the zipcode
     * @param address1      the address 1
     * @param address2      the address 2
     * @param age           the age
     * @param gender        the gender
     * @param ticket        the ticket
     * @param favorSite     the favor site
     * @param favorCategory the favor category
     * @param jacketSize    the jacket size
     * @param pantsSize     the pants size
     * @return the user
     */
    public User createUserInformation(String email, String password, String name, String langKey, String zipcode,
			String address1, String address2, int age, String gender, CommonCode ticket, Zip favorSite,
			CommonCode favorCategory, CommonCode jacketSize, CommonCode pantsSize) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne("ROLE_USER");
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setEmail(email);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setName(name);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);

        newUser.setZipcode(zipcode);
        newUser.setAddress1(address1);
        newUser.setAddress2(address2);
        newUser.setAge(age);
        newUser.setGender(gender);

        if(ticket == null) {
        	newUser.setRegisterStatus(RegisterStatus.미등록);
        } else {
        	newUser.setRegisterStatus(RegisterStatus.등록요청 );
        }

        newUser.setFavorSite(favorSite);
        newUser.setFavorCategory(favorCategory);
        newUser.setJacketSize(jacketSize);
        newUser.setPantsSize(pantsSize);

        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    /**
     * Update user information.
     *
     * @param name    the name
     * @param email   the email
     * @param langKey the lang key
     */
    public void updateUserInformation(String name, String email, String langKey) {
        User currentUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
        currentUser.setName(name);
        currentUser.setEmail(email);
        currentUser.setLangKey(langKey);
        userRepository.save(currentUser);
        log.debug("Changed Information for User: {}", currentUser);
    }

    /**
     * Change password.
     *
     * @param password the password
     */
    public void changePassword(String password) {
        User currentUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
        String encryptedPassword = passwordEncoder.encode(password);
        currentUser.setPassword(encryptedPassword);
        userRepository.save(currentUser);
        log.debug("Changed password for User: {}", currentUser);
    }

    /**
     * Gets user with authorities.
     *
     * @return the user with authorities
     */
    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        User currentUser = userRepository.findOneByEmail(SecurityUtils.getCurrentLogin());
        currentUser.getAuthorities().size(); // eagerly load the association
        return currentUser;
    }

    /**
     * Remove not activated users.
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        DateTime now = new DateTime();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getEmail());
            userRepository.delete(user);
        }
    }

	/*public void update(UserDTO userDTO) {
		User user = userRepository.findOne(userDTO.getId());

		user.update(
				  StringUtils.isNotEmpty(userDTO.getName()) ? userDTO.getName() : user.getName()
				, StringUtils.isNotEmpty(userDTO.getPhoneNumber()) ? userDTO.getPhoneNumber() : user.getPhoneNumber()
				, StringUtils.isNotEmpty(userDTO.getZipcode()) ? userDTO.getZipcode() : user.getZipcode()
				, StringUtils.isNotEmpty(userDTO.getAddress1()) ? userDTO.getAddress1() : user.getAddress1()
				, StringUtils.isNotEmpty(userDTO.getAddress2()) ? userDTO.getAddress2() : user.getAddress2()
				, userDTO.getAge() > 0 ? userDTO.getAge() : user.getAge()
				, StringUtils.isNotEmpty(userDTO.getGender()) ? userDTO.getGender() : user.getGender()
				, userDTO.getFavorSiteId() != null
				, userDTO.getFavorCategoryId() !=
				, userDTO.getjacketSize
				, userDTO.getpantsSize
				, userDTO.getexerciseCount
				, userDTO.getregisterStatus
				);
	}*/
}
