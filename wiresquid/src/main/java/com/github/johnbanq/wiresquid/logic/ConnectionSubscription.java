package com.github.johnbanq.wiresquid.logic;


import io.vavr.collection.Seq;
import io.vavr.collection.SortedSet;
import io.vavr.collection.TreeSet;

import java.util.Comparator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;


/**
 * an active view to the concurrently updated ordered subset of connections (ordered late to early, filtered by filter)
 *
 * user can getSet() to get a consistent snapshot of the set
 *
 * useful for imgui rendering
 */
public class ConnectionSubscription {

    private final ConnectionFilter filter;
    private final Consumer<ConnectionSubscription> onClose;

    /**
     * note: despite set object is CAS capable, but we still use lock to perform pessimistic update
     */
    private final Lock setLock = new ReentrantLock();

    /**
     * use volatile to allow reading it without Locking
     */
    private volatile SortedSet<WiresquidConnection> set = TreeSet.empty(
            Comparator.comparingLong(WiresquidConnection::getId).reversed()
    );

    public ConnectionSubscription(ConnectionFilter filter, Consumer<ConnectionSubscription> onClose) {
        this.filter = filter;
        this.onClose = onClose;
    }

    // lifecycle & query //

    public void close() {
        onClose.accept(this);
    }

    public SortedSet<WiresquidConnection> getSet() {
        return set;
    }

    // listener //

    void onInit(Seq<WiresquidConnection> connections) {
        editSet(s-> s.addAll(connections.filter(filter::shouldAccept)));
    }

    void onConnectionCreated(WiresquidConnection connection) {
        editSet(set->{
            if(filter.shouldAccept(connection)) {
                set = set.add(connection);
            }
            return set;
        });
    }

    void onConnectionStateChange(WiresquidConnection from, WiresquidConnection to) {
        editSet(set->{
            if(filter.shouldAccept(to)) {
                set = set.replace(from, to);
            }
            return set;
        });
    }

    private void editSet(Function<SortedSet<WiresquidConnection>, SortedSet<WiresquidConnection>> editor) {
        setLock.lock();
        try {
            SortedSet<WiresquidConnection> newSet = editor.apply(set);
            set = newSet;
        } finally {
            setLock.unlock();
        }
    }

}
