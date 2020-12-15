package br.ftd.entity;

import java.sql.Date;
import java.util.Calendar;

public class KeyPass {
	
	public static boolean checkKeyPass(Date keyDate){
		
		boolean result = true;
		
		java.util.Date dataatual = Calendar.getInstance().getTime();
		
		if(dataatual.after(keyDate)){
			result = false;
		}
		
		return result;
	}
	
}
