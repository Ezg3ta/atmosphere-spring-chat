package org.atmosphere.samples.chat.spring;

import java.io.IOException;
import java.util.Date;

import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResource.TRANSPORT;
import org.atmosphere.cpr.AtmosphereResourceEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "chat")
public class ChatController {
	@Autowired
	AtmosphereResourceEventListener listener;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public void onRequest(AtmosphereResource r) throws IOException {
		AtmosphereRequest req = r.getRequest();
		r.addEventListener(listener);
		
		if (req.getHeader("negotiating") == null) {
			r.resumeOnBroadcast(r.transport() == TRANSPORT.LONG_POLLING).suspend();
		} else {
			r.getResponse().getWriter().write("OK");
		}
	}
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public void post(AtmosphereResource r) throws IOException {
		AtmosphereRequest req = r.getRequest();
		r.addEventListener(listener);
		
		String body = req.getReader().readLine().trim();
	
		String author = body.substring(body.indexOf(":") + 2, body.indexOf(",") - 1);
		String message = body.substring(body.lastIndexOf(":") + 2, body.length() - 2);
	
		r.getBroadcaster().broadcast(new Data(author, message).toString());
	}

    private final static class Data {

        private final String text;
        private final String author;

        public Data(String author, String text) {
            this.author = author;
            this.text = text;
        }

        public String toString() {
            return "{ \"text\" : \"" + text + "\", \"author\" : \"" + author + "\" , \"time\" : " + new Date().getTime() + "}";
        }
    }
}
