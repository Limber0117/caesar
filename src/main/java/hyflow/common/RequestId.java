package hyflow.common;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Represents the unique id of request. To ensure uniqueness, the id contains
 * the id of client and the request sequence number. Every client should have
 * assigned unique client id, so that unique requests id can be created. Clients
 * gives consecutive sequence numbers to every sent request. The sequence number
 * starts with 0.
 */
public class RequestId implements Serializable, Comparable<RequestId> {
    private static final long serialVersionUID = 1L;

    private final short clientId;
    private final int seqNumber;

    /**
     * Creates new <code>RequestId</code> instance.
     * 
     * @param clientId - the id of client
     * @param seqNumber - the request sequence number
     */
    public RequestId(short clientId, int seqNumber) {
        this.clientId = clientId;
        this.seqNumber = seqNumber;
    }

    public RequestId(DataInputStream input) throws IOException {
        this.clientId = input.readShort();
        this.seqNumber = input.readInt();
    }

    /**
     * Returns the id of client.
     * 
     * @return the id of client
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Returns the request sequence number.
     * 
     * @return the request sequence number
     */
    public int getSeqNumber() {
        return seqNumber;
    }

    public int byteSize() {
        return 2 + 4;
    }

    public void writeTo(ByteBuffer bb) {
        bb.putShort(clientId);
        bb.putInt(seqNumber);
    }

    public int compareTo(RequestId requestId) {
        if (clientId != requestId.clientId)
            return clientId - requestId.clientId;
        else
            return seqNumber - requestId.seqNumber;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        RequestId other = (RequestId) obj;
        return clientId == other.clientId && seqNumber == other.seqNumber;
    }

    public int hashCode() {
        int result = 17;
        result = 31*result + clientId;
        result = 31*result + seqNumber;
        return result;
    }

    public String toString() {
        return clientId + ":" + seqNumber;
    }
}
