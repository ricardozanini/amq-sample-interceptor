package amq.sample.interceptor;

import java.util.List;
import javax.security.auth.Subject;
import org.apache.activemq.artemis.api.core.ActiveMQBuffer;
import org.apache.activemq.artemis.api.core.ActiveMQException;
import org.apache.activemq.artemis.api.core.ICoreMessage;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.core.remoting.CloseListener;
import org.apache.activemq.artemis.core.remoting.FailureListener;
import org.apache.activemq.artemis.protocol.amqp.broker.AMQPMessage;
import org.apache.activemq.artemis.protocol.amqp.broker.AmqpInterceptor;
import org.apache.activemq.artemis.reader.TextMessageUtil;
import org.apache.activemq.artemis.spi.core.protocol.RemotingConnection;
import org.apache.activemq.artemis.spi.core.remoting.Connection;
import org.apache.activemq.artemis.spi.core.remoting.ReadyListener;
import org.jboss.logging.Logger;

public class CustomLogInterceptor implements AmqpInterceptor {

    private static final Logger LOGGER = Logger.getLogger(CustomLogInterceptor.class);

    public CustomLogInterceptor() {
        LOGGER.info("******************** INTERCEPTOR CREATED ************************* ");
    }

    @Override
    public boolean intercept(AMQPMessage packet, RemotingConnection connection) throws ActiveMQException {
        LOGGER.info("CustomLogInterceptor gets called!");
        LOGGER.trace("Packet: " + packet.getClass().getName());
        final RemotingConnection conn = this.wrapConn(connection);
        LOGGER.trace("AMQPMessage: " + packet.toString());
        final ICoreMessage message = packet.toCore();
        LOGGER.trace("CoreMessage: " + message.toString());
        // @formatter:off
        // just a logger wrapper to output in JSON format
        LogUtil.toLog(LOGGER, message.getMessageID() == 0 ? "in" : "out", 
            String.valueOf(message.getMessageID()), 
            message.getStringProperty("JMSCorrelationID"), 
            message.getAddress(),
            TextMessageUtil.readBodyText(message.getBodyBuffer()).toString(),
            conn.getClientID(),
            conn.getRemoteAddress(),
            conn.getProtocolName(),
            message.getStringProperty("_AMQ_ROUTE_TO"),
            this.extractAllProps(message));
        // @formatter:on        
        return true;
    }

    private RemotingConnection wrapConn(final RemotingConnection connection) {
        return connection == null ? new NullableRemotingConnection() : connection;
    }

    private String extractAllProps(final ICoreMessage message) {
        final StringBuffer sb = new StringBuffer();

        for (SimpleString item : message.getPropertyNames()) {
            sb.append(item.toString());
            sb.append("=");
            sb.append(message.getStringProperty(item));
            sb.append(", ");
        }

        return sb.toString();
    }

    public static class NullableRemotingConnection implements RemotingConnection {

        @Override
        public void bufferReceived(Object connectionID, ActiveMQBuffer buffer) {
            // TODO Auto-generated method stub

        }

        @Override
        public Object getID() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getCreationTime() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public String getRemoteAddress() {
            return "null-remote-address";
        }

        @Override
        public void addFailureListener(FailureListener listener) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean removeFailureListener(FailureListener listener) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void addCloseListener(CloseListener listener) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean removeCloseListener(CloseListener listener) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public List<CloseListener> removeCloseListeners() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setCloseListeners(List<CloseListener> listeners) {
            // TODO Auto-generated method stub

        }

        @Override
        public List<FailureListener> getFailureListeners() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public List<FailureListener> removeFailureListeners() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void setFailureListeners(List<FailureListener> listeners) {
            // TODO Auto-generated method stub

        }

        @Override
        public ActiveMQBuffer createTransportBuffer(int size) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void fail(ActiveMQException me) {
            // TODO Auto-generated method stub

        }

        @Override
        public void fail(ActiveMQException me, String scaleDownTargetNodeID) {
            // TODO Auto-generated method stub

        }

        @Override
        public void destroy() {
            // TODO Auto-generated method stub

        }

        @Override
        public Connection getTransportConnection() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean isClient() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isDestroyed() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void disconnect(boolean criticalError) {
            // TODO Auto-generated method stub

        }

        @Override
        public void disconnect(String scaleDownNodeID, boolean criticalError) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean checkDataReceived() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void flush() {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean isWritable(ReadyListener callback) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void killMessage(SimpleString nodeID) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean isSupportReconnect() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isSupportsFlowControl() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public Subject getSubject() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public String getProtocolName() {
            return "null-protocol-name";
        }

        @Override
        public void setClientID(String cID) {
            // TODO Auto-generated method stub

        }

        @Override
        public String getClientID() {
            return "null-client-id";
        }

        @Override
        public void scheduledFlush() {
            // TODO Auto-generated method stub

        }

        @Override
        public String getTransportLocalAddress() {
            // TODO Auto-generated method stub
            return null;
        }

    }

}
