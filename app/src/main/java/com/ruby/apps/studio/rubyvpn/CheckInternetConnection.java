package com.ruby.apps.studio.rubyvpn;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Этот класс предназначен для проверки наличия интернет-соединения на устройстве. Он содержит метод netCheck,
 * который принимает в качестве параметра контекст и возвращает булевское значение: true,
 * если есть активное соединение с интернетом, и false, если соединение отсутствует.
 * Для проверки доступности интернета метод использует класс ConnectivityManager,
 * который является системным сервисом Android и предоставляет информацию о состоянии сетевых подключений.
 * Метод получает доступ к объекту ConnectivityManager через getSystemService(Context.CONNECTIVITY_SERVICE)
 * и затем использует метод getActiveNetworkInfo(), чтобы получить информацию о текущем активном сетевом подключении.
 * Если информация доступна и подключение установлено или находится в процессе установки, метод возвращает true, иначе - false
 */
public class CheckInternetConnection {

    /**
     * Check internet status
     * @param context
     * @return: internet connection status
     */
    public boolean netCheck(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();

        boolean isConnected = nInfo != null && nInfo.isConnectedOrConnecting();
        return isConnected;
    }
}
