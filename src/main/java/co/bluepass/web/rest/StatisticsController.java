package co.bluepass.web.rest;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

import co.bluepass.domain.Club;
import co.bluepass.domain.Reservation;
import co.bluepass.repository.ClubRepository;
import co.bluepass.repository.ReservationRepository;
import co.bluepass.web.view.UseHistoryExcelView;

/**
 * The type Statistics controller.
 */
@Controller
public class StatisticsController {

    @Inject
    private ReservationRepository reservationRepository;

	@Inject
	private ClubRepository clubRepository;

    /**
     * Download by club view.
     *
     * @param clubId the club id
     * @param model  the model
     * @return the view
     */
    @RequestMapping(value = "/statistics/{clubId}/download", method = RequestMethod.GET)
	public View downloadByClub(@PathVariable Long clubId, ModelMap model) {

		Club club = clubRepository.findOne(clubId);

		if(club == null) {
			return null;
		}

		List<Reservation> reservations = reservationRepository.findByClub(club);
		model.addAttribute("useHistory", reservations);
		return new UseHistoryExcelView();
	}

}
