package com.codecraft.javase.planewar;

import javax.swing.*;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Player {

	private AudioClip bg;
	private AudioClip dadada;
	private AudioClip award;
	private AudioClip boom;
	private String abc = "file:/C:\\Users\\BY\\Documents\\eclipse-workspace\\PlaneWar\\src\\com\\codecraft\\javase\\planewar\\music\\";
	private String[] url = new String[] {
			"game_music.wav",
			"se_dadada.wav",
			"se_award.wav",
			"se_boom.wav" };

	public Player() {
		URL bgUrl = null;
		URL dadadaUrl = null;
		URL awardUrl = null;
		URL boomUrl = null;
		bgUrl =Player.class.getResource("game_music.wav");
		dadadaUrl = Player.class.getResource("se_dadada.wav");
		awardUrl = Player.class.getResource("se_award.wav");
		boomUrl =Player.class.getResource("se_boom.wav");
		bg = JApplet.newAudioClip(bgUrl);
		dadada = JApplet.newAudioClip(dadadaUrl);
		award = JApplet.newAudioClip(awardUrl);
		boom = JApplet.newAudioClip(boomUrl);
	}

	public void playDaDaDa() {
		dadada.play();
	}

	public void stopDaDaDa() {
		dadada.stop();
	}

	public void playBg() {
		bg.loop();
	}

	public void stopBg() {
		bg.stop();
	}

	public void playAward() {
		award.play();
	}

	public void stopAward() {
		award.stop();
	}

	public void playBoom() {
		boom.play();
	}

	public void stopBoom() {
		boom.stop();
	}

}
