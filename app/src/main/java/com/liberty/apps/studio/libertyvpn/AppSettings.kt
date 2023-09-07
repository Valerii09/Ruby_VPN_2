package com.liberty.apps.studio.libertyvpn


/**
 * Этот класс содержит набор статических свойств, которые определяют опции приложения и настройки подписки.
 * Он содержит параметры, такие как флаги, которые управляют включением рекламы (Admob или Facebook),
 * и ID One Signal, который используется для уведомлений. Класс также содержит идентификаторы подписок на один месяц,
 * три месяца и один год. Одна из функций класса - управлять флагом isUserPaid,
 * который используется для определения того, имеет ли пользователь подписку или нет.
 */
class AppSettings {
    companion object {
        //this flag will be handled by subscription
        var isUserPaid = false

        // enable admob or facebook ads, by default admob ads are enable
        // set flags true or false
        val enableAdmobAds = true
        val enableFacebookAds = false

        //place your one signal id
        val oneSignalId = "Your One Signal Id"

        //Subscription id's
        val one_month_subscription_id = "put your one month subscription id here"
        val three_month_subscription_id = "put your three months subscription id here"
        val one_year_subscription_id = "put your one year subscription id here"
    }
}