package com.cs.logprocessor.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class EventLogDTO {

	
	private String id;
	private String eventHost;
	private String eventType;
	private String state;
	@JsonIgnore
	private String eventAlert;
	private Long eventDuration;
	private Long timestamp;
	@JsonIgnore
	private String type;
	private String host;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventHost() {
		return eventHost;
	}
	public void setEventHost(String eventHost) {
		this.eventHost = eventHost;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getEventAlert() {
		return eventAlert;
	}
	public void setEventAlert(String eventAlert) {
		this.eventAlert = eventAlert;
	}
	public Long getEventDuration() {
		return eventDuration;
	}
	public void setEventDuration(Long eventDuration) {
		this.eventDuration = eventDuration;
	}
	@Override
	public String toString() {
		return "EventLog [id=" + id + ", eventHost=" + eventHost
				+ ", eventType=" + eventType + ", state=" + state
				+ ", eventAlert=" + eventAlert + ", eventDuration="
				+ eventDuration + ", timestamp=" + timestamp + ", type=" + type
				+ ", host=" + host + "]";
	}
	
}
