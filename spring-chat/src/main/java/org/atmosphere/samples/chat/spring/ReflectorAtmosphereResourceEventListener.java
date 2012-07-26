package org.atmosphere.samples.chat.spring;

import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class ReflectorAtmosphereResourceEventListener extends AtmosphereResourceEventListenerAdapter {

	@Override
	public void onBroadcast(AtmosphereResourceEvent e) {
		System.out.println("onBroadcast: " + e.getMessage());
	}

	@Override
	public void onDisconnect(AtmosphereResourceEvent e) {
		System.out.println("onDisconnect: " + e.getMessage());
	}

	@Override
	public void onResume(AtmosphereResourceEvent e) {
		System.out.println("onResume: " + e.getMessage());
	}

	@Override
	public void onSuspend(AtmosphereResourceEvent e) {
		System.out.println("onSuspend: " + e.getMessage());
	}

	@Override
	public void onThrowable(AtmosphereResourceEvent e) {
		System.out.println("onThrowable: " + e.getMessage());
	}
}
