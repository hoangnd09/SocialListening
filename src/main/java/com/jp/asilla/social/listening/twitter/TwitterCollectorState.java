package com.jp.asilla.social.listening.twitter;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

/**
 * statuses are retrieved by pagination.
 * <ul>
 * <li>if pagination is in-progress, UntilDate is stored and SinceDate is cleared.</li>
 * <li>if pagination is completed, UntilDate is cleared, SinceDate is stored.</li>
 * </ul>
 * 
 */
public class TwitterCollectorState  {

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	private State state;
	
	public TwitterCollectorState(String state) {
		if (state != null) {
			try {
				this.state = new Gson().fromJson(state, State.class);
			} catch (JsonSyntaxException e) {
				this.state = new State();
			}
		} else {
			this.state = new State();
		}
	}
	
	public String getState() {
		if (this.state != null) {
			return new Gson().toJson(this.state);
		} else {
			return null;
		}
	}
	
	public Date getSinceDate() {
		return state.sinceDate;
	}

	public String getSinceDateString() {
		if (state.sinceDate == null) {
			return null;
		}
		return formatter.format(state.sinceDate);
	}
	
	public void setSinceDate(Date sinceDate) {
		this.state.sinceDate = sinceDate;
	}
	
	public Date getUntilDate() {
		return state.untilDate;
	}
	
	public String getUntilDateString() {
		if (state.untilDate == null) {
			return null;
		}
		return formatter.format(state.untilDate);
	}

	public void setUntilDate(Date untilDate) {
		this.state.untilDate = untilDate;
	}
	public Date getTmpUntilDate() {
		return this.state.tmpUntilDate;
	}
	public void setTmpUntilDate(Date untilDate) {
		this.state.tmpUntilDate = untilDate;
	}
	
	
	class State {
		@SerializedName("since_date")
		private Date sinceDate;
		
		@SerializedName("until_date")
		private Date untilDate;
		
		@SerializedName("tmp_until_date")
		private Date tmpUntilDate;
	}
}
