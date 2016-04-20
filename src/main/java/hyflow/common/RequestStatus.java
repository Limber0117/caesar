package hyflow.common;

/**
 * Created by balajiarun on 3/10/16.
 */
public enum RequestStatus {
    Waiting,

    PrePending,
    Pending,

    SlowPrePending,
    SlowPending,

    Rejected,
    Accepted,

    Stable,
    Delivered
}
