package org.example;

/**
 * Интерфейс поставщика
 */
public interface IProducer {

    public boolean subscription(IConsumer consumer);

    public boolean cancel(IConsumer consumer);
}
