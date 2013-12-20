package jsprit.core.algorithm.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RouteEventListeners {

	private Map<Class<?>, List<?>> map = new HashMap<Class<?>, List<?>>();
	
	/**
	 * Sends routeEvents to the according listeners.
	 * 
	 * @param eventSourceId
	 * @param event
	 */
	public <T extends RouteEvent> void sendRouteEvent(String eventSourceId, T event){
		@SuppressWarnings("unchecked")
		List<RouteEventListener<T>> list = (List<RouteEventListener<T>>) map.get(event.getType());
		if(list == null) return;
		for(RouteEventListener<T> l : list){
			l.sendRouteEvent(eventSourceId, event);
		}
	}
	
	/**
	 * Adds RouteChangedEventListener.
	 * 
	 * <p>null allowed but has no effect. However, the eventType in RouteChangedEventListener<T> must be specified (thus null is 
	 * not allowed). 
	 * @param l
	 */
	public <T extends RouteEvent> void addRouteEventListener(RouteEventListener<T> l){
		if(l == null) return;
		if(l.getEventType() == null) throw new IllegalStateException("eventType in listener is unspecified. this must not be.");
		@SuppressWarnings("unchecked")
		List<RouteEventListener<T>> list = (List<RouteEventListener<T>>) map.get(l.getEventType());
		if(list == null){
			list = new ArrayList<RouteEventListener<T>>();
			map.put(l.getEventType(), list);
		}
		list.add(l);
	}
	
	/**
	 * Removes RouteChangedEventListener.
	 * 
	 * <p>null allowed but has no effect. l.getEventType() must be specified, i.e. null is not allowed.
	 * 
	 * @param l
	 */
	public <T extends RouteEvent> void removeRouteEventListener(RouteEventListener<T> l){
		if(l == null) return;
		if(l.getEventType() == null) throw new IllegalStateException("eventType in listener is unspecified. this must not be.");
		@SuppressWarnings("unchecked")
		List<RouteEventListener<T>> list = (List<RouteEventListener<T>>) map.get(l.getEventType());
		if(list != null){
			list.remove(l);
		}
	}
	
	/**
	 * Returns listeners for the specified eventType.
	 * 
	 * <p>null returns an empty list.
	 * @param eventType
	 * @return
	 */
	public <T extends RouteEvent> Collection<RouteEventListener<T>> getRouteEventListeners(Class<T> eventType){
		if(eventType == null) return Collections.emptyList();
		@SuppressWarnings("unchecked")
		List<RouteEventListener<T>> list = (List<RouteEventListener<T>>) map.get(eventType);
		if(list == null) {
			list = Collections.emptyList();
		}
		return list;
	}
	
}