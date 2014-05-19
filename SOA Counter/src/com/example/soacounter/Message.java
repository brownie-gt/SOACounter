package com.example.soacounter;

import android.content.Context;
import android.widget.Toast;


public class Message {
	//Muestra Mensaje Pop-Up
	public static void message(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	}
}
