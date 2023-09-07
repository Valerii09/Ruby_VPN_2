package com.liberty.apps.studio.libertyvpn.db;

import com.liberty.apps.studio.libertyvpn.db.ServerContract.ServerEntry;

/**
 * Это класс, который определяет структуру базы данных на сервере.
 * Он содержит две константы строк, которые определяют типы данных в таблице (текстовый и числовой),
 * а также информацию о структуре таблицы и ее колонках и связанные с ней SQL-запросы для ее создания и удаления.
 * Класс обеспечивает доступ к таблице данных на сервере и позволяет приложению хранить и извлекать информацию из нее.
 */
class ServerDatabase {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    static final String SQL_CREATE_SERVER =
            "CREATE TABLE " + ServerEntry.TABLE_NAME + " (" +
            ServerEntry._ID + " INTEGER PRIMARY KEY," +
            ServerEntry.COLUMN_NAME_HOST_NAME + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_IP_ADDRESS + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_SCORE + INTEGER_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_PING + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_SPEED + INTEGER_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_COUNTRY_LONG + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_COUNTRY_SHORT + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_VPN_SESSIONS + INTEGER_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_UPTIME + INTEGER_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_TOTAL_USERS + INTEGER_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_TOTAL_TRAFFIC + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_LOG_TYPE + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_OPERATOR + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_OPERATOR_MESSAGE + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_CONFIG_DATA + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_PORT + INTEGER_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_PROTOCOL + TEXT_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_IS_OLD + INTEGER_TYPE + COMMA_SEP +
            ServerEntry.COLUMN_NAME_IS_STARRED + INTEGER_TYPE +
            " )";

    static final String SQL_DELETE_SERVER = "DROP TABLE IF EXISTS " + ServerEntry.TABLE_NAME;
}
