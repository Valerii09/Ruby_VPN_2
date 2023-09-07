package com.liberty.apps.studio.libertyvpn.view.activites

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.liberty.apps.studio.libertyvpn.R
import com.liberty.apps.studio.libertyvpn.databinding.ActivityControllerBinding

/**
 * Этот класс является контроллером активности, которая управляет поведением навигации фрагментов.
 * Он наследуется от класса AppCompatActivity и переопределяет метод onCreate(), который вызывается при создании активности.

В методе onCreate() класса ControllerActivity происходит следующее:

    **создается экземпляр класса ActivityControllerBinding,
    который отвечает за связывание элементов пользовательского интерфейса активности с кодом;

    **вызывается метод inflate() объекта binding, который загружает макет активности из файла activity_controller.xml и
    проводит связывание его с кодом;

    **вызывается метод setContentView() объекта активности с передачей ему корневого элемента макета,
    что устанавливает этот макет в качестве содержимого активности;

вызывается метод setupNavigation(), который настраивает навигацию между фрагментами.

Метод setupNavigation() настраивает навигацию между фрагментами, используя библиотеку Navigation.

    **создается экземпляр NavHostFragment, который управляет фрагментами внутри контейнера.
    Контейнером является элемент fragmentContainerView, который определен в макете activity_controller.xml;

    **создается экземпляр PopupMenu, который содержит список команд для навигации.
    В данном случае, список команд определяется в файле main_nav_menu.xml;

    **получается объект меню PopupMenu;

    **вызывается метод setupWithNavController() объекта binding.mainBottomNav,
    который устанавливает связь между верхней панелью навигации и NavController из NavHostFragment.
    Теперь при выборе команды из меню, NavController переключает соответствующий фрагмент.
 */
class ControllerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityControllerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
    }

    /*
    * Controller activity control the Navigation behaviour of the Fragments
    * */
    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.main_nav_menu)
        val menu = popupMenu.menu
        binding.mainBottomNav.setupWithNavController(menu, navHostFragment.navController)
    }
}