package com.liberty.apps.studio.libertyvpn.view.activites

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.android.billingclient.api.SkuDetails
import com.google.android.gms.ads.MobileAds
import com.liberty.apps.studio.libertyvpn.AppSettings
import com.liberty.apps.studio.libertyvpn.billing.BillingClass
import com.liberty.apps.studio.libertyvpn.databinding.ActivitySplashBinding
import com.onesignal.OneSignal

/**
 * Этот класс является SplashScreen (экран приветствия) приложения и наследуется от AppCompatActivity.
 * Он реализует интерфейсы BillingClass.BillingErrorHandler и BillingClass.SkuDetailsListener и
 * содержит методы для обработки ошибок при оплате и получения информации о деталях подписок.
 * В onCreate() методе инициализируются GoogleAds, OneSignal и Billing.
 * В методе startMainActivity() задается задержка в 2 секунды перед открытием основного экрана приложения (ControllerActivity).
 * Метод displayErrorMessage() обрабатывает сообщения об ошибках оплаты,
 * а метод subscriptionsDetailList() получает детали о подписках из Google Play
 */

class SplashActivity : AppCompatActivity(), BillingClass.BillingErrorHandler,
    BillingClass.SkuDetailsListener {

    private var binding: ActivitySplashBinding ?= null
    private var billingClass: BillingClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initGoogleAds()
        initOneSignal()
        initBilling()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    private fun initGoogleAds() {
        MobileAds.initialize(
            this
        ) { }
    }

    private fun initOneSignal() {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(AppSettings.oneSignalId);
    }

    private fun initBilling() {
        billingClass = BillingClass(this@SplashActivity)
        billingClass!!.setmCallback(this, this);
        billingClass!!.startConnection();
    }

    private fun startMainActivity() {
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this@SplashActivity, ControllerActivity::class.java))
            finish()
        }, 2000)
    }

    override fun displayErrorMessage(message: String?) {
        when {
            message.equals("done") -> {
                AppSettings.isUserPaid =
                    billingClass!!.isSubscribedToSubscriptionItem(AppSettings.one_month_subscription_id) ||
                            billingClass!!.isSubscribedToSubscriptionItem(AppSettings.three_month_subscription_id) ||
                            billingClass!!.isSubscribedToSubscriptionItem(AppSettings.one_year_subscription_id)

                startMainActivity()
            }
            message.equals("error") -> {
                AppSettings.isUserPaid = false;
                startMainActivity()
            }
            else -> {
                AppSettings.isUserPaid = false;
                startMainActivity()
            }
        }
    }

    override fun subscriptionsDetailList(skuDetailsList: MutableList<SkuDetails>?) {
    }
}