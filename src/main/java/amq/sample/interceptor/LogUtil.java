package amq.sample.interceptor;

import org.apache.activemq.artemis.utils.StringEscapeUtils;
import org.jboss.logging.Logger;

final class LogUtil {

    private static final String MSG_FORMAT = "\"type\":\"%s\", \"message-id\":\"%s\", \"correlation-id\":\"%s\", \"destination\":\"%s\", \"payload\":\"%s\", \"clientID\":\"%s\", \"remoteAddress\":\"%s\", \"protocolName\":\"%s\", \"amqRouteTo\":\"%s\", \"properties\": \"%s\", \"amqpHeaders\": \"%s\" ";

    private LogUtil() {

    }

    static void toLog(final Logger log, final String... data) {
        String _payload = StringEscapeUtils.escapeString(data[4]).trim();
        String msg = String.format(MSG_FORMAT, data[0], data[1], data[2], data[3], _payload, data[5], data[6], data[7], data[8], data[9], data[10]);
        log.info(msg);
        System.out.println(msg);
    }

}
