package de.christianzunker.android.rundeck.utils;

import org.rundeck.api.RundeckClient;

public class RundeckClientFactory {

	RundeckClient rdClient = null;
	
	public RundeckClient getClient() {
		if (rdClient != null) return rdClient;
		// 10.0.2.2 => loopback on host
        // http://developer.android.com/tools/devices/emulator.html#networkaddresses
        rdClient = new RundeckClient("http://10.0.2.2:4440", "sndnvcdKu2Or2VdrPoc3OEUDv1sUKoV3");
        return rdClient;
	}
}
