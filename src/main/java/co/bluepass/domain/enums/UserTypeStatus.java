package co.bluepass.domain.enums;

/**
 * The enum User type status.
 */
public enum UserTypeStatus {

    /**
     * Common user type status.
     */
    COMMON

	, /**
     * Request register user type status.
     */
    REQUEST_REGISTER

	, /**
     * Request vendor user type status.
     */
    REQUEST_VENDOR

	, /**
     * Reject request register user type status.
     */
    REJECT_REQUEST_REGISTER

	, /**
     * Reject request vendor user type status.
     */
    REJECT_REQUEST_VENDOR

	, /**
     * Permit request register user type status.
     */
    PERMIT_REQUEST_REGISTER

	, /**
     * Permit request vendor user type status.
     */
    PERMIT_REQUEST_VENDOR

}
