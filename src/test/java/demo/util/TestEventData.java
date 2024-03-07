package demo.util;

import demo.event.DemoInboundEvent;

public class TestEventData {

    public static String INBOUND_DATA = "event data";

    public static DemoInboundEvent buildDemoInboundEvent(String id) {
        return new DemoInboundEvent(id, INBOUND_DATA);
    }
}
