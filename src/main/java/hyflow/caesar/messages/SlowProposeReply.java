package hyflow.caesar.messages;

import hyflow.common.RequestId;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by balajiarun on 3/12/16.
 */
public final class SlowProposeReply extends Message {

    private final RequestId requestId;
    private final Status status;
    private final Set<RequestId> pred;
    private final long position;

    public SlowProposeReply(int view, RequestId rId, Status status, Set<RequestId> pred, long position) {
        super(view);
        this.requestId = rId;
        this.status = status;
        this.pred = pred;
        this.position = position;
    }

    public SlowProposeReply(DataInputStream input) throws IOException {
        super(input);
        requestId = new RequestId(input);
        status = Status.values()[input.readUnsignedByte()];

        int length = input.readInt();
        pred = new ConcurrentSkipListSet<>();
        while (--length >= 0)
            pred.add(new RequestId(input));

        position = input.readLong();
    }

    public RequestId getRequestId() {
        return requestId;
    }

    public Set<RequestId> getPred() {
        return pred;
    }

    public Status getStatus() {
        return status;
    }

    public long position() {
        return position;
    }

    @Override
    public MessageType getType() {
        return MessageType.SlowProposeReply;
    }

    @Override
    protected void write(ByteBuffer bb) {
        requestId.writeTo(bb);
        bb.put((byte) status.ordinal());

        bb.putInt(pred.size());
        for (RequestId rId : pred) {
            rId.writeTo(bb);
        }
        bb.putLong(position);
    }

    @Override
    public int byteSize() {
        return super.byteSize() + requestId.byteSize() + 1 + 4 + (pred.size() * requestId.byteSize()) + 8;
    }

    @Override
    public String toString() {
        return "SlowProposeReply{" +
                "requestId=" + requestId +
                ", status=" + status +
                ", pred=" + pred +
                ", position=" + position +
                '}';
    }

    public enum Status {
        ACK, NACK
    }
}
