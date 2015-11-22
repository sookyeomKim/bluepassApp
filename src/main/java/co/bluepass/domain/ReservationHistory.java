package co.bluepass.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import co.bluepass.domain.util.CustomDateTimeDeserializer;
import co.bluepass.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Reservation history.
 */
@Entity
@Table(name = "RESERVATION_HISTORY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ReservationHistory implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "reservation_id")
	private Long reservationId;

	@Column(name = "reservation_method")
	private String reservationMethod;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	@Column(name = "reservation_time")
	private DateTime reservationTime;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	@Column(name = "start_time")
	private DateTime startTime;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	@JsonDeserialize(using = CustomDateTimeDeserializer.class)
	@Column(name = "end_time")
	private DateTime endTime;

	@Column(name = "used")
	private Boolean used;

	@Column(name = "canceled")
	private Boolean canceled = false;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "user_email")
	private String userEmail;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "user_phone_number")
	private String userPhoneNumber;

	@Column(name = "user_gender")
	private String userGender;

	@Column(name = "register_status")
	private String registerStatus;

	@Column(name = "action_schedule_id")
	private Long actionScheduleId;

	@Column(name = "action_schedule_name")
	private String actionScheduleName;

	@Column(name = "day")
	private String day;

	@Column(name = "schedule_type")
	private String scheduleType;

	@Column(name = "attendee_limit")
	private Integer attendeeLimit;

	@Column(name = "class_schedule_id")
	private Long classScheduleId;

	@Column(name = "class_schedule_etc")
	private String classScheduleEtc;

	@Column(name = "club_id")
	private Long clubId;

	@Column(name = "club_name")
	private String clubName;

	@Column(name = "club_zipcode")
	private String clubZipcode;

	@Column(name = "club_address1")
	private String clubAddress1;

	@Column(name = "club_address2")
	private String clubAddress2;

	@Column(name = "club_address_simple")
	private String clubAddressSimple;

	@Column(name = "club_phone_number")
	private String clubPhoneNumber;

	@Column(name = "only_femail")
	private Boolean onlyFemail;

	@Column(name = "club_manager_mobile")
	private String clubManagerMobile;

	@Column(name = "notification_type")
	private String notificationType;

	@Column(name = "reservation_close")
	private String reservationClose;

	@Column(name = "action_id")
	private Long actionId;

	@Column(name = "action_title")
	private String actionTitle;

	@Column(name = "action_description")
	private String actionDescription;

	@Column(name = "action_use_limit_type")
	private String actionUseLimitType;

	@Column(name = "action_use_limit_value")
	private Integer actionUseLimitValue;

	@Column(name = "instructor_id")
	private Long instructorId;

	@Column(name = "instructor_name")
	private String instructorName;

	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "category_name")
	private String categoryName;

	@Column(name = "ticket_id")
	private Long ticketId;

	@Column(name = "ticket_name")
	private String ticketName;

    /**
     * Instantiates a new Reservation history.
     */
    public ReservationHistory() {
	}

    /**
     * Instantiates a new Reservation history.
     *
     * @param r the r
     */
    public ReservationHistory(Reservation r) {
		if (r != null) {
			this.reservationId = r.getId();
			this.reservationMethod = r.getReservationMethod();
			this.reservationTime = r.getReservationTime();
			this.startTime = r.getStartTime();
			this.endTime = r.getEndTime();
			this.used = r.getUsed();
			this.canceled = r.getCanceled();

			if (r.getUser() != null) {
				this.userId = r.getUser().getId();
				this.userEmail = r.getUser().getEmail();
				this.userName = r.getUser().getName();
				this.userPhoneNumber = r.getUser().getPhoneNumber();
				this.userGender = r.getUser().getGender();
				this.registerStatus = r.getUser().getRegisterStatus().name();
			}

			if (r.getClassSchedule() != null) {
				if (r.getClassSchedule().getActionSchedule() != null) {
					this.actionScheduleId = r.getClassSchedule().getActionSchedule().getId();
					this.day = r.getClassSchedule().getActionSchedule().getDay();
					this.scheduleType = r.getClassSchedule().getActionSchedule().getScheduleType();
					this.attendeeLimit = r.getClassSchedule().getActionSchedule().getAttendeeLimit();
				}
				this.classScheduleId = r.getClassSchedule().getId();
				this.classScheduleEtc = r.getClassSchedule().getEtc();
				this.instructorId = r.getClassSchedule().getInstructor().getId();
				this.instructorName = r.getClassSchedule().getInstructor().getName();
				this.categoryId = r.getClassSchedule().getCategory().getId();
				this.categoryName = r.getClassSchedule().getCategory().getName();
			}

			if (r.getClub() != null) {
				this.clubId = r.getClub().getId();
				this.clubName = r.getClub().getName();
				this.clubZipcode = r.getClub().getZipcode();
				this.clubAddress1 = r.getClub().getAddress1();
				this.clubAddress2 = r.getClub().getAddress2();
				this.clubAddressSimple = r.getClub().getAddressSimple();
				this.clubPhoneNumber = r.getClub().getPhoneNumber();
				this.onlyFemail = r.getClub().getOnlyFemale();
				this.clubManagerMobile = r.getClub().getManagerMobile();
				this.notificationType = r.getClub().getNotificationType();
				this.reservationClose = r.getClub().getReservationClose();
			}

			if (r.getAction() != null) {
				this.actionId = r.getAction().getId();
				this.actionTitle = r.getAction().getTitle();
				this.actionDescription = r.getAction().getDescription();
				this.actionUseLimitType = r.getAction().getUseLimitType();
				this.actionUseLimitValue = r.getAction().getUseLimitValue();
			}

			if (r.getTicket() != null) {
				this.ticketId = r.getTicket().getId();
				this.ticketName = r.getTicket().getName();
			}

		}
	}

    /**
     * Update.
     *
     * @param r the r
     */
    public void update(Reservation r) {
		if (r != null) {
			this.reservationId = r.getId();
			this.reservationMethod = r.getReservationMethod();
			this.reservationTime = r.getReservationTime();
			this.startTime = r.getStartTime();
			this.endTime = r.getEndTime();
			this.used = r.getUsed();
			this.canceled = r.getCanceled();

			if (r.getUser() != null) {
				this.userId = r.getUser().getId();
				this.userEmail = r.getUser().getEmail();
				this.userName = r.getUser().getName();
				this.userPhoneNumber = r.getUser().getPhoneNumber();
				this.userGender = r.getUser().getGender();
				this.registerStatus = r.getUser().getRegisterStatus().name();
			}

			if (r.getClassSchedule() != null) {
				if (r.getClassSchedule().getActionSchedule() != null) {
					this.actionScheduleId = r.getClassSchedule().getActionSchedule().getId();
					this.day = r.getClassSchedule().getActionSchedule().getDay();
					this.scheduleType = r.getClassSchedule().getActionSchedule().getScheduleType();
					this.attendeeLimit = r.getClassSchedule().getActionSchedule().getAttendeeLimit();
				}
				this.classScheduleId = r.getClassSchedule().getId();
				this.classScheduleEtc = r.getClassSchedule().getEtc();
				this.instructorId = r.getClassSchedule().getInstructor().getId();
				this.instructorName = r.getClassSchedule().getInstructor().getName();
				this.categoryId = r.getClassSchedule().getCategory().getId();
				this.categoryName = r.getClassSchedule().getCategory().getName();
			}

			if (r.getClub() != null) {
				this.clubId = r.getClub().getId();
				this.clubName = r.getClub().getName();
				this.clubZipcode = r.getClub().getZipcode();
				this.clubAddress1 = r.getClub().getAddress1();
				this.clubAddress2 = r.getClub().getAddress2();
				this.clubAddressSimple = r.getClub().getAddressSimple();
				this.clubPhoneNumber = r.getClub().getPhoneNumber();
				this.onlyFemail = r.getClub().getOnlyFemale();
				this.clubManagerMobile = r.getClub().getManagerMobile();
				this.notificationType = r.getClub().getNotificationType();
				this.reservationClose = r.getClub().getReservationClose();
			}

			if (r.getAction() != null) {
				this.actionId = r.getAction().getId();
				this.actionTitle = r.getAction().getTitle();
				this.actionDescription = r.getAction().getDescription();
				this.actionUseLimitType = r.getAction().getUseLimitType();
				this.actionUseLimitValue = r.getAction().getUseLimitValue();
			}

			if (r.getTicket() != null) {
				this.ticketId = r.getTicket().getId();
				this.ticketName = r.getTicket().getName();
			}

		}
	}

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
		return id;
	}

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
		this.id = id;
	}

    /**
     * Gets reservation id.
     *
     * @return the reservation id
     */
    public Long getReservationId() {
		return reservationId;
	}

    /**
     * Sets reservation id.
     *
     * @param reservationId the reservation id
     */
    public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

    /**
     * Gets reservation method.
     *
     * @return the reservation method
     */
    public String getReservationMethod() {
		return reservationMethod;
	}

    /**
     * Sets reservation method.
     *
     * @param reservationMethod the reservation method
     */
    public void setReservationMethod(String reservationMethod) {
		this.reservationMethod = reservationMethod;
	}

    /**
     * Gets reservation time.
     *
     * @return the reservation time
     */
    public DateTime getReservationTime() {
		return reservationTime;
	}

    /**
     * Sets reservation time.
     *
     * @param reservationTime the reservation time
     */
    public void setReservationTime(DateTime reservationTime) {
		this.reservationTime = reservationTime;
	}

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public DateTime getStartTime() {
		return startTime;
	}

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public DateTime getEndTime() {
		return endTime;
	}

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

    /**
     * Gets used.
     *
     * @return the used
     */
    public Boolean getUsed() {
		return used;
	}

    /**
     * Sets used.
     *
     * @param used the used
     */
    public void setUsed(Boolean used) {
		this.used = used;
	}

    /**
     * Gets canceled.
     *
     * @return the canceled
     */
    public Boolean getCanceled() {
		return canceled;
	}

    /**
     * Sets canceled.
     *
     * @param canceled the canceled
     */
    public void setCanceled(Boolean canceled) {
		this.canceled = canceled;
	}

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
		return userId;
	}

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Long userId) {
		this.userId = userId;
	}

    /**
     * Gets user email.
     *
     * @return the user email
     */
    public String getUserEmail() {
		return userEmail;
	}

    /**
     * Sets user email.
     *
     * @param userEmail the user email
     */
    public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
		return userName;
	}

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
		this.userName = userName;
	}

    /**
     * Gets user phone number.
     *
     * @return the user phone number
     */
    public String getUserPhoneNumber() {
		return userPhoneNumber;
	}

    /**
     * Sets user phone number.
     *
     * @param userPhoneNumber the user phone number
     */
    public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}

    /**
     * Gets user gender.
     *
     * @return the user gender
     */
    public String getUserGender() {
		return userGender;
	}

    /**
     * Sets user gender.
     *
     * @param userGender the user gender
     */
    public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

    /**
     * Gets register status.
     *
     * @return the register status
     */
    public String getRegisterStatus() {
		return registerStatus;
	}

    /**
     * Sets register status.
     *
     * @param registerStatus the register status
     */
    public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

    /**
     * Gets action schedule id.
     *
     * @return the action schedule id
     */
    public Long getActionScheduleId() {
		return actionScheduleId;
	}

    /**
     * Sets action schedule id.
     *
     * @param actionScheduleId the action schedule id
     */
    public void setActionScheduleId(Long actionScheduleId) {
		this.actionScheduleId = actionScheduleId;
	}

    /**
     * Gets action schedule name.
     *
     * @return the action schedule name
     */
    public String getActionScheduleName() {
		return actionScheduleName;
	}

    /**
     * Sets action schedule name.
     *
     * @param actionScheduleName the action schedule name
     */
    public void setActionScheduleName(String actionScheduleName) {
		this.actionScheduleName = actionScheduleName;
	}

    /**
     * Gets day.
     *
     * @return the day
     */
    public String getDay() {
		return day;
	}

    /**
     * Sets day.
     *
     * @param day the day
     */
    public void setDay(String day) {
		this.day = day;
	}

    /**
     * Gets schedule type.
     *
     * @return the schedule type
     */
    public String getScheduleType() {
		return scheduleType;
	}

    /**
     * Sets schedule type.
     *
     * @param scheduleType the schedule type
     */
    public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

    /**
     * Gets attendee limit.
     *
     * @return the attendee limit
     */
    public Integer getAttendeeLimit() {
		return attendeeLimit;
	}

    /**
     * Sets attendee limit.
     *
     * @param attendeeLimit the attendee limit
     */
    public void setAttendeeLimit(Integer attendeeLimit) {
		this.attendeeLimit = attendeeLimit;
	}

    /**
     * Gets class schedule id.
     *
     * @return the class schedule id
     */
    public Long getClassScheduleId() {
		return classScheduleId;
	}

    /**
     * Sets class schedule id.
     *
     * @param classScheduleId the class schedule id
     */
    public void setClassScheduleId(Long classScheduleId) {
		this.classScheduleId = classScheduleId;
	}

    /**
     * Gets class schedule etc.
     *
     * @return the class schedule etc
     */
    public String getClassScheduleEtc() {
		return classScheduleEtc;
	}

    /**
     * Sets class schedule etc.
     *
     * @param classScheduleEtc the class schedule etc
     */
    public void setClassScheduleEtc(String classScheduleEtc) {
		this.classScheduleEtc = classScheduleEtc;
	}

    /**
     * Gets club id.
     *
     * @return the club id
     */
    public Long getClubId() {
		return clubId;
	}

    /**
     * Sets club id.
     *
     * @param clubId the club id
     */
    public void setClubId(Long clubId) {
		this.clubId = clubId;
	}

    /**
     * Gets club name.
     *
     * @return the club name
     */
    public String getClubName() {
		return clubName;
	}

    /**
     * Sets club name.
     *
     * @param clubName the club name
     */
    public void setClubName(String clubName) {
		this.clubName = clubName;
	}

    /**
     * Gets club zipcode.
     *
     * @return the club zipcode
     */
    public String getClubZipcode() {
		return clubZipcode;
	}

    /**
     * Sets club zipcode.
     *
     * @param clubZipcode the club zipcode
     */
    public void setClubZipcode(String clubZipcode) {
		this.clubZipcode = clubZipcode;
	}

    /**
     * Gets club address 1.
     *
     * @return the club address 1
     */
    public String getClubAddress1() {
		return clubAddress1;
	}

    /**
     * Sets club address 1.
     *
     * @param clubAddress1 the club address 1
     */
    public void setClubAddress1(String clubAddress1) {
		this.clubAddress1 = clubAddress1;
	}

    /**
     * Gets club address 2.
     *
     * @return the club address 2
     */
    public String getClubAddress2() {
		return clubAddress2;
	}

    /**
     * Sets club address 2.
     *
     * @param clubAddress2 the club address 2
     */
    public void setClubAddress2(String clubAddress2) {
		this.clubAddress2 = clubAddress2;
	}

    /**
     * Gets club address simple.
     *
     * @return the club address simple
     */
    public String getClubAddressSimple() {
		return clubAddressSimple;
	}

    /**
     * Sets club address simple.
     *
     * @param clubAddressSimple the club address simple
     */
    public void setClubAddressSimple(String clubAddressSimple) {
		this.clubAddressSimple = clubAddressSimple;
	}

    /**
     * Gets club phone number.
     *
     * @return the club phone number
     */
    public String getClubPhoneNumber() {
		return clubPhoneNumber;
	}

    /**
     * Sets club phone number.
     *
     * @param clubPhoneNumber the club phone number
     */
    public void setClubPhoneNumber(String clubPhoneNumber) {
		this.clubPhoneNumber = clubPhoneNumber;
	}

    /**
     * Gets only femail.
     *
     * @return the only femail
     */
    public Boolean getOnlyFemail() {
		return onlyFemail;
	}

    /**
     * Sets only femail.
     *
     * @param onlyFemail the only femail
     */
    public void setOnlyFemail(Boolean onlyFemail) {
		this.onlyFemail = onlyFemail;
	}

    /**
     * Gets club manager mobile.
     *
     * @return the club manager mobile
     */
    public String getClubManagerMobile() {
		return clubManagerMobile;
	}

    /**
     * Sets club manager mobile.
     *
     * @param clubManagerMobile the club manager mobile
     */
    public void setClubManagerMobile(String clubManagerMobile) {
		this.clubManagerMobile = clubManagerMobile;
	}

    /**
     * Gets notification type.
     *
     * @return the notification type
     */
    public String getNotificationType() {
		return notificationType;
	}

    /**
     * Sets notification type.
     *
     * @param notificationType the notification type
     */
    public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

    /**
     * Gets reservation close.
     *
     * @return the reservation close
     */
    public String getReservationClose() {
		return reservationClose;
	}

    /**
     * Sets reservation close.
     *
     * @param reservationClose the reservation close
     */
    public void setReservationClose(String reservationClose) {
		this.reservationClose = reservationClose;
	}

    /**
     * Gets action id.
     *
     * @return the action id
     */
    public Long getActionId() {
		return actionId;
	}

    /**
     * Sets action id.
     *
     * @param actionId the action id
     */
    public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

    /**
     * Gets action title.
     *
     * @return the action title
     */
    public String getActionTitle() {
		return actionTitle;
	}

    /**
     * Sets action title.
     *
     * @param actionTitle the action title
     */
    public void setActionTitle(String actionTitle) {
		this.actionTitle = actionTitle;
	}

    /**
     * Gets action description.
     *
     * @return the action description
     */
    public String getActionDescription() {
		return actionDescription;
	}

    /**
     * Sets action description.
     *
     * @param actionDescription the action description
     */
    public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}

    /**
     * Gets action use limit type.
     *
     * @return the action use limit type
     */
    public String getActionUseLimitType() {
		return actionUseLimitType;
	}

    /**
     * Sets action use limit type.
     *
     * @param actionUseLimitType the action use limit type
     */
    public void setActionUseLimitType(String actionUseLimitType) {
		this.actionUseLimitType = actionUseLimitType;
	}

    /**
     * Gets action use limit value.
     *
     * @return the action use limit value
     */
    public Integer getActionUseLimitValue() {
		return actionUseLimitValue;
	}

    /**
     * Sets action use limit value.
     *
     * @param actionUseLimitValue the action use limit value
     */
    public void setActionUseLimitValue(Integer actionUseLimitValue) {
		this.actionUseLimitValue = actionUseLimitValue;
	}

    /**
     * Gets instructor id.
     *
     * @return the instructor id
     */
    public Long getInstructorId() {
		return instructorId;
	}

    /**
     * Sets instructor id.
     *
     * @param instructorId the instructor id
     */
    public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}

    /**
     * Gets instructor name.
     *
     * @return the instructor name
     */
    public String getInstructorName() {
		return instructorName;
	}

    /**
     * Sets instructor name.
     *
     * @param instructorName the instructor name
     */
    public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

    /**
     * Gets category id.
     *
     * @return the category id
     */
    public Long getCategoryId() {
		return categoryId;
	}

    /**
     * Sets category id.
     *
     * @param categoryId the category id
     */
    public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

    /**
     * Gets category name.
     *
     * @return the category name
     */
    public String getCategoryName() {
		return categoryName;
	}

    /**
     * Sets category name.
     *
     * @param categoryName the category name
     */
    public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

    /**
     * Gets ticket id.
     *
     * @return the ticket id
     */
    public Long getTicketId() {
		return ticketId;
	}

    /**
     * Sets ticket id.
     *
     * @param ticketId the ticket id
     */
    public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

    /**
     * Gets ticket name.
     *
     * @return the ticket name
     */
    public String getTicketName() {
		return ticketName;
	}

    /**
     * Sets ticket name.
     *
     * @param ticketName the ticket name
     */
    public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ReservationHistory reservationHistory = (ReservationHistory) o;

        return Objects.equals(id, reservationHistory.id);

    }

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "ReservationHistory{" + "id=" + id + ", reservationId='" + reservationId + "'" + ", reservationMethod='"
				+ reservationMethod + "'" + ", reservationTime='" + reservationTime + "'" + ", startTime='" + startTime
				+ "'" + ", endTime='" + endTime + "'" + ", used='" + used + "'" + ",canceled='" + canceled + "'"
				+ ", userId='" + userId + "'" + ", userEmail='" + userEmail + "'" + ", userName='" + userName + "'"
				+ ", userPhoneNumber='" + userPhoneNumber + "'" + ", userGender='" + userGender + "'"
				+ ", registerStatus='" + registerStatus + "'" + ", actionScheduleId='" + actionScheduleId + "'"
				+ ", actionScheduleName='" + actionScheduleName + "'" + ", day='" + day + "'" + ", scheduleType='"
				+ scheduleType + "'" + ", attendeeLimit='" + attendeeLimit + "'" + ", classScheduleId='"
				+ classScheduleId + "'" + ", classScheduleEtc='" + classScheduleEtc + "'" + ", clubId='" + clubId + "'"
				+ ", clubName='" + clubName + "'" + ", clubZipcode='" + clubZipcode + "'" + ", clubAddress1='"
				+ clubAddress1 + "'" + ", clubAddress2='" + clubAddress2 + "'" + ", clubAddressSimple='"
				+ clubAddressSimple + "'" + ", clubPhoneNumber='" + clubPhoneNumber + "'" + ", onlyFemail='"
				+ onlyFemail + "'" + ", clubManagerMobile='" + clubManagerMobile + "'" + ", notificationType='"
				+ notificationType + "'" + ", reservationClose='" + reservationClose + "'" + ", actionId='" + actionId
				+ "'" + ", actionTitle='" + actionTitle + "'" + ", actionDescription='" + actionDescription + "'"
				+ ", actionUseLimitType='" + actionUseLimitType + "'" + ", actionUseLimitValue='" + actionUseLimitValue
				+ "'" + ", instructorId='" + instructorId + "'" + ", instructorName='" + instructorName + "'"
				+ ", categoryId='" + categoryId + "'" + ", categoryName='" + categoryName + "'" + ", ticketId='"
				+ ticketId + "'" + ", ticketName='" + ticketName + "'" + '}';
	}
}
