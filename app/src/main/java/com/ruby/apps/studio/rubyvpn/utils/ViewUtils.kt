package com.ruby.apps.studio.rubyvpn.utils

import android.content.Context
import android.widget.Toast

/**
 * Это расширение функции для класса Context, которая добавляет возможность выводить всплывающее уведомление
 * (Toast) с указанным сообщением (message) и продолжительностью отображения в 2 секунды.
 * Проще говоря, это удобный способ вывести короткое сообщение
 * для пользователя на экране во время работы приложения.
 */
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}