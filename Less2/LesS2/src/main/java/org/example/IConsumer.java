package org.example;

/**
 * Интерфейс потребителя
 */
public interface IConsumer {

    //Передача сообщения
    public Status sendMessage(String str);

    //Проверка доступности линии
    public boolean isLineFree();
}
