package com.example.testapplication.Service;

import android.content.Intent;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testapplication.Activity.LoginActivity;
import com.example.testapplication.Activity.MainActivity;
import com.example.testapplication.R;

public class NavigationHelper
{
    public static void CreateNavigation(AppCompatActivity activity)
    {
        Button buttonMenu = activity.findViewById(R.id.nav_menu);

        buttonMenu.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(
                    activity, buttonMenu);
            activity.getMenuInflater().inflate(R.menu.nav_menu,
                    popupMenu.getMenu());

            // 设置点击菜单项的监听器
            popupMenu.setOnMenuItemClickListener(item -> {
                var itemId = item.getItemId();
                if(itemId == R.id.nav_login)
                {
                    Intent intent = new Intent(activity,
                            LoginActivity.class);
                    activity.startActivity(intent);
                }
                else if (itemId == R.id.nav_register)
                {

                }

                return true;
            });

            popupMenu.show(); // 显示弹出菜单
        });
    }
}