package com.meteor.extrabotany.common.core.version;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import net.minecraftforge.common.MinecraftForge;

public class ThreadVersionChecker extends Thread {

	public ThreadVersionChecker() {
		setName("ExtraBotany Version Checker Thread");
		setDaemon(true);
		start();
	}

	@Override
	public void run() {
		try {
			URL url = new URL("https://raw.githubusercontent.com/ExtraMeteorP/ExtraBotany/master/build/" + MinecraftForge.MC_VERSION + ".txt");
			BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
			VersionChecker.onlineVersion = r.readLine();
			r.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		VersionChecker.doneChecking = true;
	}
}
