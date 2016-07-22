package com.maxifly.fb2_illustrator;

import ch.qos.cal10n.BaseName;
import ch.qos.cal10n.Locale;
import ch.qos.cal10n.LocaleData;

@BaseName("LogMessagesSearcher")
@LocaleData( { @Locale("en") })
public enum LogMessagesSearcher {
    UNEXPECTED_VALUE, //=Неожиданное значение {0}
    DONE_WORK, //=Работа закончена.
    SHUTDOWN_NOW, //=Получен сигнал на прекращение работы.
}
