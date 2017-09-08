package utils.threadUtils;

import java.util.Timer;
import java.util.TimerTask;

import main.mainApplication.Constantes;

public abstract class TimerScheduler {
	private int when;
	Timer timer;

	public TimerScheduler(int when) {
		this.when = when;

	}

	public abstract void toDo();

	public void start() {
		TimerTask timerTask = new TimerTask() {
			public void run() {
				toDo();
			}
		};
		timer = new Timer();

		timer.scheduleAtFixedRate(timerTask, Constantes.startUpdater, when);

	}

	public void stop() {
		timer.cancel();
	}

}
