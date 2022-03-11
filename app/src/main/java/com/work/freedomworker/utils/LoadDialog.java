/**
 * 
 */
package com.work.freedomworker.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author hemi_yang
 *
 */
public class LoadDialog {

	private ProgressDialog progressDialog;
	
	public  void  showProgressDialog(Context mContext, String loadmsg){
		progressDialog= ProgressDialog.show(mContext, "", loadmsg, false, false);
	}
	
	public  void closeProgressDialog(){
		progressDialog.dismiss();
	}
	
	
}
