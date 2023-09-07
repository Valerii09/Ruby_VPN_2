package com.liberty.apps.studio.libertyvpn.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.liberty.apps.studio.libertyvpn.db.ServerContract.ServerEntry;

import com.liberty.apps.studio.libertyvpn.model.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Этот класс является помощником для работы с базой данных SQLite в приложении Android.
 * Он наследуется от класса SQLiteOpenHelper и содержит методы для создания и обновления базы данных,
 * а также методы для вставки, удаления и получения данных из базы данных.
 * Класс также отвечает за сериализацию и десериализацию объектов Server в ContentValues и Cursor.
 * Метод getInstance возвращает единственный экземпляр класса в приложении,
 * чтобы избежать создания нескольких экземпляров приложения.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "droidovpn.db";
    private static DbHelper instance;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    /**
     * Эта функция представляет собой метод, который вызывается при создании новой базы данных SQLite.
     * Он принимает экземпляр класса SQLiteDatabase в качестве параметра и выполняет запрос CREATE TABLE,
     * который создает таблицу сервера в базе данных.
     * Метод db.execSQL() используется для выполнения SQL-запросов на создание, изменение или удаление таблиц,
     * индексов и других объектов базы данных.
     * ServerDatabase.SQL_CREATE_SERVER содержит строку запроса SQL, который будет выполнен при создании базы данных.
     * Этот запрос создает таблицу с названием "server" с несколькими столбцами, определенными ранее в классе ServerDatabase.
     * Таким образом, при вызове этого метода приложение создаст базу данных SQLite и таблицу сервера, готовую к использованию
     */
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ServerDatabase.SQL_CREATE_SERVER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ServerDatabase.SQL_DELETE_SERVER);
        onCreate(db);
    }

    @Override
    /**
     * Этот метод используется в SQLiteOpenHelper для обработки события "downgrade" в базе данных.
     * Это происходит, когда пользователь устанавливает приложение с новой версией базы данных,
     * но у него уже была установлена старая версия базы данных.
     * В этом случае новая версия не может быть совместима со старой версией, поэтому база данных возвращает версию до того,
     * как была обновлена или установлена новая версия.
     * Метод onDowngrade вызывается автоматически, если новая версия базы данных меньше, чем старая версия.
     * Внутри этого метода вызывается метод onUpgrade с теми же параметрами, что и при вызове listener'а.
     */
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /**
     * @param servers Это метод для сохранения списка серверов в базу данных SQLite на устройстве Android.
     *                Он открывает базу данных, начинает транзакцию, удаляет старые записи,
     *                вставляет новые записи в таблицу ServerEntry, заканчивает транзакцию и закрывает базу данных.
     *                Данные сохраняются в виде объектов класса Server,
     *                а ContentValues используются для преобразования данных в формат,
     *                поддерживаемый базой данных.
     */
    public void save(List<Server> servers) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();

        deleteOld(db);

        for (Server server : servers) {
            ContentValues values = getContentValues(server);
            db.insert(ServerEntry.TABLE_NAME, null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    /**
     * Deletes all servers except for starred
     */
    public void deleteOld(SQLiteDatabase db) {
        db.delete(ServerEntry.TABLE_NAME,
                ServerEntry.COLUMN_NAME_IS_STARRED + " = 0",
                null);
    }

    public void setStarred(String ipAddress, boolean isStarred) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ServerEntry.COLUMN_NAME_IS_STARRED, isStarred ? 1 : 0);
        db.update(ServerEntry.TABLE_NAME, values,
                ServerContract.ServerEntry.COLUMN_NAME_IP_ADDRESS + " = ?", new String[]{ipAddress});
    }

    public List<Server> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Server> servers = new ArrayList<>();
        Cursor cursor = db.query(ServerContract.ServerEntry.TABLE_NAME,
                ServerContract.ServerEntry.ALL_COLUMNS, null, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                servers.add(cursorToServer(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        db.close();

        return servers;
    }

    /**
     *
     *  Это метод, который создает объект ContentValues,
     *               используемый для вставки данных в базу данных SQLite. В метод передается объект Server,
     *               содержащий информацию о сервере,
     *               и с помощью методов put создается объект ContentValues со значениями полей таблицы в базе данных.
     *               Затем этот объект возвращается из метода для использования при вставке записи в таблицу.
     * @return
     */
    private ContentValues getContentValues(Server server) {
        ContentValues values = new ContentValues();
        values.put(ServerEntry.COLUMN_NAME_HOST_NAME, server.hostName);
        values.put(ServerEntry.COLUMN_NAME_IP_ADDRESS, server.ipAddress);
        values.put(ServerEntry.COLUMN_NAME_SCORE, server.score);
        values.put(ServerEntry.COLUMN_NAME_PING, server.ping);
        values.put(ServerEntry.COLUMN_NAME_SPEED, server.speed);
        values.put(ServerEntry.COLUMN_NAME_COUNTRY_LONG, server.countryLong);
        values.put(ServerEntry.COLUMN_NAME_COUNTRY_SHORT, server.countryShort);
        values.put(ServerEntry.COLUMN_NAME_VPN_SESSIONS, server.vpnSessions);
        values.put(ServerEntry.COLUMN_NAME_UPTIME, server.uptime);
        values.put(ServerEntry.COLUMN_NAME_TOTAL_USERS, server.totalUsers);
        values.put(ServerEntry.COLUMN_NAME_TOTAL_TRAFFIC, server.totalTraffic);
        values.put(ServerEntry.COLUMN_NAME_LOG_TYPE, server.logType);
        values.put(ServerEntry.COLUMN_NAME_OPERATOR, server.operator);
        values.put(ServerEntry.COLUMN_NAME_OPERATOR_MESSAGE, server.message);
        values.put(ServerEntry.COLUMN_NAME_CONFIG_DATA, server.ovpnConfigData);
        values.put(ServerEntry.COLUMN_NAME_PORT, server.port);
        values.put(ServerEntry.COLUMN_NAME_PROTOCOL, server.protocol);
        values.put(ServerEntry.COLUMN_NAME_IS_OLD, 0);
        values.put(ServerEntry.COLUMN_NAME_IS_STARRED, server.isStarred ? 1 : 0);
        return values;
    }

    private Server cursorToServer(Cursor cursor) {
        Server server = new Server();
        server.hostName = cursor.getString(1);
        server.ipAddress = cursor.getString(2);
        server.score = cursor.getInt(3);
        server.ping = cursor.getString(4);
        server.speed = cursor.getLong(5);
        server.countryLong = cursor.getString(6);
        server.countryShort = cursor.getString(7);
        server.vpnSessions = cursor.getLong(8);
        server.uptime = cursor.getLong(9);
        server.totalUsers = cursor.getLong(10);
        server.totalTraffic = cursor.getString(11);
        server.logType = cursor.getString(12);
        server.operator = cursor.getString(13);
        server.message = cursor.getString(14);
        server.ovpnConfigData = cursor.getString(15);
        server.port = cursor.getInt(16);
        server.protocol = cursor.getString(17);
        server.isStarred = cursor.getInt(19) == 1;
        return server;
    }
}
