package co.bluepass.web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import co.bluepass.web.websocket.dto.TestDTO;

/**
 * The type Test service.
 */
//@Service
public class TestService {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	private int counter = 0;

    /**
     * Test send.
     */
    @Scheduled(fixedDelay = 5000)
	public void testSend()
	{
		counter++;
		TestDTO testDTO = new TestDTO();
		testDTO.setMessage("Message #" + counter);
		messagingTemplate.convertAndSend("/topic/updates", testDTO);
	}
}
