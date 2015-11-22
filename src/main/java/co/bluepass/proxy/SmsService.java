package co.bluepass.proxy;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import co.bluepass.domain.User;
import co.bluepass.web.rest.InstructorResource;

/**
 * The type Sms service.
 */
public class SmsService {

	private final static Logger log = LoggerFactory.getLogger(SmsService.class);

	private static final String url = "https://1950m.co.kr/index.php/api/v1/sms";
	//curl -L --data "sender=01033782902" https://1950m.co.kr/index.php/api/v1/sms:443

	/*
	 * message  : 받을 문자 내용 최대 2000바이트.
	 * username : directsend 발급 ID
	 * recipients : 발송 할 고객 번호 , 로 구분함. ex) 01012341234,0101555123,010303040123
	 * key : Directsend 발급 api key
	 *
	 * 각 번호가 유효하지 않을 경우에는 발송이 되지 않습니다.
	*/
	private static String sender = "01065920814";
	private static String username = "blupass";
	private static String recipients = "";
	private static String key = "jBLtUYCIePcZ0u4";

    /**
     * Send sms.
     *
     * @param message the message
     * @param users   the users
     */
    public static void sendSms(String message, List<User> users) {

		try {
			java.net.URL obj = new URL(url);
			javax.net.ssl.HttpsURLConnection con;
			con = (javax.net.ssl.HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");

			/* 여기서부터 수정해주시기 바랍니다. */

			StringBuffer recipientsSb = new StringBuffer();
			StringBuffer namesSb = new StringBuffer();

			int smsTargetSize = 0;
			for (User user : users) {
				if(StringUtils.isNotEmpty(user.getPhoneNumber())){
					recipientsSb.append(user.getPhoneNumber()).append(",");
					namesSb.append(user.getName()).append(",");
					smsTargetSize++;
				}
			}

			if(smsTargetSize <= 0){
				return;
			}

			recipients = recipientsSb.deleteCharAt(recipientsSb.lastIndexOf(",")).toString();
			//names = namesSb.deleteCharAt(namesSb.lastIndexOf(",")).toString();

			/* 여기까지만 수정해주시기 바랍니다. */

			/** 수정하지 마시기 바랍니다.
			 *  아래의 URL에서 apache commons-comdec jar 파일을 다운로드 한 후에 함께 컴파일 해주십시오.
			 *  http://commons.apache.org/proper/commons-codec/download_codec.cgi
			 * **/
			String urlParameters = "message=" + java.net.URLEncoder.encode(Base64.encodeBase64String(message.getBytes("euc-kr")), "EUC_KR")
					+ "&sender=" + java.net.URLEncoder.encode(sender, "EUC_KR")
					+ "&username=" + java.net.URLEncoder.encode(username, "EUC_KR")
					+ "&recipients=" + java.net.URLEncoder.encode(recipients, "EUC_KR")
					+ "&key=" + java.net.URLEncoder.encode(key, "EUC_KR");
			/** 수정하지 마시기 바랍니다. **/

			System.setProperty("jsse.enableSNIExtension", "false") ;
			con.setDoOutput(true);
			java.io.DataOutputStream wr = new java.io.DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			//System.out.println(responseCode);

			/*
			 * responseCode 가 200 이 아니면 내부에서 문제가 발생한 케이스입니다.
			 * Directsend 관리자에게 문의해주시기 바랍니다.
			 * */

			java.io.BufferedReader in = new java.io.BufferedReader(
			        new java.io.InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//System.out.println(response.toString());
			MDC.put("loggerFileName", "SMS.status");
			log.debug("responseCode = {}, response = {}", responseCode, response);

			/*
			 * response의 실패
			 *  {"status":101}
			 */

			/*
			 * response 성공
			 * {"status":0}
			 *  성공 코드번호.
			 */

			/*
			** status code
				0   : 정상발송
				100 : sender 검증 실패
				101 : key 검증 실패
				102 : username 값 존재 X
				103 : message 값 존재 X
				104 : sender 값 존재 X
				105 : recipients 값 존재 X
				106 : message validation 실패
				201 : user is null
				202 : 발송대상 0개
				203 : 2000bytes 초과
				204 : api key 일치하지 않음.
				205 : 잔액부족
				999 : Internal Error.
			**
			*/

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

    /**
     * Send sms.
     *
     * @param message the message
     * @param user    the user
     */
    public static void sendSms(String message, User user) {
		if(user != null){
			List<User> userList = new ArrayList<>();
			userList.add(user);
			sendSms(message, userList);
		}
	}

    /**
     * Send sms.
     *
     * @param message     the message
     * @param phoneNumber the phone number
     * @param name        the name
     */
    public static void sendSms(String message, String phoneNumber, String name) {
		try {
			java.net.URL obj = new URL(url);
			javax.net.ssl.HttpsURLConnection con;
			con = (javax.net.ssl.HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("POST");

			/** 수정하지 마시기 바랍니다.
			 *  아래의 URL에서 apache commons-comdec jar 파일을 다운로드 한 후에 함께 컴파일 해주십시오.
			 *  http://commons.apache.org/proper/commons-codec/download_codec.cgi
			 * **/
			String urlParameters = "message=" + java.net.URLEncoder.encode(Base64.encodeBase64String(message.getBytes("euc-kr")), "EUC_KR")
					+ "&sender=" + java.net.URLEncoder.encode(sender, "EUC_KR")
					+ "&username=" + java.net.URLEncoder.encode(username, "EUC_KR")
					+ "&recipients=" + java.net.URLEncoder.encode(recipients, "EUC_KR")
					+ "&key=" + java.net.URLEncoder.encode(key, "EUC_KR");
			/** 수정하지 마시기 바랍니다. **/

			System.setProperty("jsse.enableSNIExtension", "false") ;
			con.setDoOutput(true);
			java.io.DataOutputStream wr = new java.io.DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println(responseCode);

			/*
			 * responseCode 가 200 이 아니면 내부에서 문제가 발생한 케이스입니다.
			 * Directsend 관리자에게 문의해주시기 바랍니다.
			 * */

			java.io.BufferedReader in = new java.io.BufferedReader(
			        new java.io.InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
