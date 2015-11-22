package co.bluepass.web.view;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

import co.bluepass.domain.Reservation;

/**
 * The type Use history excel view.
 */
public class UseHistoryExcelView extends AbstractJExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			WritableWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String fileName = createFileName();
        setFileNameToResponse(request, response, fileName);

        List<Reservation> useHistory = (List<Reservation>) model.get("useHistory");

        WritableSheet sheet = workbook.createSheet("설문 목록", 0);

        /*
        id : Long
        reservationMethod : String
        reservationTime : DateTime
        startTime : DateTime
        endTime : DateTime
        canceled : Boolean
        used : Boolean
        user : User
        classSchedule : ClassSchedule
        club : Club
        ticket : CommonCode
        */

        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        String[] labels = {"사용자", "예약일시", "참석여부", "취소여부"};
        for (int i = 0; i < labels.length; i++) {
        	sheet.addCell(new jxl.write.Label(i, 0, labels[i]));
		}

        int row = 1;
        for (Iterator iterator = useHistory.iterator(); iterator.hasNext();) {
			Reservation reservation = (Reservation) iterator.next();
			sheet.addCell(new jxl.write.Label(0, row, reservation.getUser().getName() + "(" + reservation.getUser().getEmail() + ")"));
			sheet.addCell(new jxl.write.Label(1, row, dateFormat.print(reservation.getStartTime())));
			sheet.addCell(new jxl.write.Label(2, row, reservation.getUsed() ? "참석" : "불참"));
			sheet.addCell(new jxl.write.Label(3, row, reservation.getCanceled() ? "취소" : "-"));
			row++;
		}

	}

	private void setFileNameToResponse(HttpServletRequest request, HttpServletResponse response, String fileName) {
		try {
			fileName = new String(fileName.getBytes("euc-kr"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String userAgent = request.getHeader("User-Agent");
		if (userAgent.indexOf("MSIE 5.5") >= 0) {
			response.setContentType("doesn/matter");
			response.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");
		} else {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		}
	}

	private String createFileName() {
		SimpleDateFormat fileFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return new StringBuilder("사용내역").append("-")
				.append(fileFormat.format(new Date())).append(".xls").toString();
	}

}
