package org.bcit.com2522.project.scuffed.server;
        /*
         * Copyright 2015 MongoDB, Inc.
         *
         * Licensed under the Apache License, Version 2.0 (the "License");
         * you may not use this file except in compliance with the License.
         * You may obtain a copy of the License at
         *
         *   http://www.apache.org/licenses/LICENSE-2.0
         *
         * Unless required by applicable law or agreed to in writing, software
         * distributed under the License is distributed on an "AS IS" BASIS,
         * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
         * See the License for the specific language governing permissions and
         * limitations under the License.
         */
//More info regarding these example subscribers can be found here:
//https://mongodb.github.io/mongo-java-driver/4.9/driver-reactive/getting-started/quick-start-primer/

        import com.mongodb.MongoTimeoutException;
        import org.bson.Document;
        import org.reactivestreams.Subscriber;
        import org.reactivestreams.Subscription;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.CountDownLatch;
        import java.util.concurrent.TimeUnit;

        import static java.lang.String.format;

/**
 * Subscriber helper implementations for the Quick Tour.
 */
public final class SubscriberHelpers {

  /**
   * A Subscriber that stores the publishers results and provides a latch so can block on completion.
   *
   * @param <T> The publishers result type
   */
  public static class ObservableSubscriber<T> implements Subscriber<T> {
        private final List<T> received;
        private final List<Throwable> errors;
        private final CountDownLatch latch;
        private volatile Subscription subscription;
        private volatile boolean completed;

    /**
     * Instantiates a new Observable subscriber.
     */
    ObservableSubscriber() {
            this.received = new ArrayList<T>();
            this.errors = new ArrayList<Throwable>();
            this.latch = new CountDownLatch(1);
        }

        @Override
        public void onSubscribe(final Subscription s) {
            subscription = s;
        }

        @Override
        public void onNext(final T t) {
            received.add(t);
        }

        @Override
        public void onError(final Throwable t) {
            errors.add(t);
            onComplete();
        }

        @Override
        public void onComplete() {
            completed = true;
            latch.countDown();
        }

    /**
     * Gets subscription.
     *
     * @return the subscription
     */
    public Subscription getSubscription() {
            return subscription;
        }

    /**
     * Gets received.
     *
     * @return the received
     */
    public List<T> getReceived() {
            return received;
        }

    /**
     * Gets error.
     *
     * @return the error
     */
    public Throwable getError() {
            if (errors.size() > 0) {
                return errors.get(0);
            }
            return null;
        }

    /**
     * Is completed boolean.
     *
     * @return the boolean
     */
    public boolean isCompleted() {
            return completed;
        }

    /**
     * Get list.
     *
     * @param timeout the timeout
     * @param unit    the unit
     * @return the list
     * @throws Throwable the throwable
     */
    public List<T> get(final long timeout, final TimeUnit unit) throws Throwable {
            return await(timeout, unit).getReceived();
        }

    /**
     * Await observable subscriber.
     *
     * @return the observable subscriber
     * @throws Throwable the throwable
     */
    public ObservableSubscriber<T> await() throws Throwable {
            return await(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        }

    /**
     * Await observable subscriber.
     *
     * @param timeout the timeout
     * @param unit    the unit
     * @return the observable subscriber
     * @throws Throwable the throwable
     */
    public ObservableSubscriber<T> await(final long timeout, final TimeUnit unit) throws Throwable {
            subscription.request(Integer.MAX_VALUE);
            if (!latch.await(timeout, unit)) {
                throw new MongoTimeoutException("Publisher onComplete timed out");
            }
            if (!errors.isEmpty()) {
                throw errors.get(0);
            }
            return this;
        }
    }

  /**
   * A Subscriber that immediately requests Integer.MAX_VALUE onSubscribe
   *
   * @param <T> The publishers result type
   */
  public static class OperationSubscriber<T> extends ObservableSubscriber<T> {

        @Override
        public void onSubscribe(final Subscription s) {
            super.onSubscribe(s);
            s.request(Integer.MAX_VALUE);
        }
    }

  /**
   * A Subscriber that prints a message including the received items on completion
   *
   * @param <T> The publishers result type
   */
  public static class PrintSubscriber<T> extends OperationSubscriber<T> {
        private final String message;

    /**
     * A Subscriber that outputs a message onComplete.
     *
     * @param message the message to output onComplete
     */
    public PrintSubscriber(final String message) {
            this.message = message;
        }

        @Override
        public void onComplete() {
            System.out.println(format(message, getReceived()));
            super.onComplete();
        }
    }

  /**
   * A Subscriber that prints the json version of each document
   */
  public static class PrintDocumentSubscriber extends OperationSubscriber<Document> {

        @Override
        public void onNext(final Document document) {
            super.onNext(document);
            System.out.println(document.toJson());
        }
    }

    private SubscriberHelpers() {
    }
}