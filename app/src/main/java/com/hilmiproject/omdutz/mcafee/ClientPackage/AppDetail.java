package com.hilmiproject.omdutz.mcafee.ClientPackage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by L on 21/11/17.
 */

public class AppDetail {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String token;
    private String email;
    private String password;
    private String notelp,norek,namatoko,alamattoko;
    private String status;
    private String jk;

    public String getJk() {
            return this.sharedPreferences.getString("jk",null);
    }

    public void setJk(String jk) {
        this.editor.putString("jk",jk);
        this.editor.commit();
    }

    public String getStatus() {
        return this.sharedPreferences.getString("status",null);
    }

    public void setStatus(String status) {
        this.editor.putString("status",status);
        this.editor.commit();
    }

    private void setSharedPreferences(){
        sharedPreferences = context.getSharedPreferences("akun",context.MODE_APPEND);
        editor            = sharedPreferences.edit();
    }

    public AppDetail(Context context) {
        this.context = context;
        this.setSharedPreferences();
    }

    public AppDetail(Context context, String token, String nama, String password) {
        this.context = context;
        this.setSharedPreferences();
        editor.putString("token"    ,token);
        editor.putString("email"     ,nama );
        editor.putString("password" ,password);
        editor.commit();
    }
    public void logout(){
        this.editor.clear().commit();
    }

    public String getNotelp() {
        return this.sharedPreferences.getString("notelp",null);
    }

    public void setNotelp(String notelp) {
        this.editor.putString("notelp",notelp);
        this.editor.commit();
    }

    public String getNorek() {
        return this.sharedPreferences.getString("norek",null);
    }

    public void setNorek(String norek) {
        this.editor.putString("norek",notelp);
        this.editor.commit();
    }

    public String getNamatoko() {
        return this.sharedPreferences.getString("namatoko",null);
    }

    public void setNamatoko(String namatoko) {
        this.editor.putString("namatoko",notelp);
        this.editor.commit();
    }

    public String getAlamattoko() {
        return this.sharedPreferences.getString("alamattoko",null);
    }

    public void setAlamattoko(String alamattoko) {
        this.editor.putString("alamattoko",notelp);
        this.editor.commit();
    }

    public boolean isLogin(){
        if(this.sharedPreferences.getString("email",null) != null
                && this.sharedPreferences.getString("password",null) != null){
            return true;
        }
        return false;

    }

    public String getToken() {
        return this.sharedPreferences.getString("token",null);
    }

    public void setToken(String token) {
        this.editor.putString("token",token);
        this.editor.commit();
    }
    public String getNama(){
        return this.sharedPreferences.getString("nama",null);
    }
    public void setNama(String nama){
        this.editor.putString("nama",nama).commit();
    }

    public String getEmail() {
        return this.sharedPreferences.getString("email",null);
    }

    public void setEmail(String nama) {
        this.editor.putString("email",nama);
        this.editor.commit();
    }

    public String getPassword() {
        return this.sharedPreferences.getString("password",null);
    }

    public void setPassword(String password) {
        this.editor.putString("password",password);
        this.editor.commit();
    }
    public boolean getIklan(String iklan){
        return this.sharedPreferences.getBoolean(iklan,false);
    }
    public boolean getIklan2(){
        return this.sharedPreferences.getBoolean("iklan2",false);
    }
    public void setIklan2(boolean hasil){
        this.editor.putBoolean("iklan2",hasil);
        this.editor.commit();
    }
    public void setIklan(boolean hasil,String iklan){
        this.editor.putBoolean(iklan,hasil);
        this.editor.commit();
    }
    public void setRek(boolean ada){
        this.editor.putBoolean("rek",ada);
        this.editor.commit();
    }
    public boolean getRek() {
        return this.sharedPreferences.getBoolean("rek",false);
    }
    public void setBadget(int badget){
        this.editor.putInt("badge",badget);
        this.editor.commit();
    }
    public int getBadgetTotal(){
        return this.sharedPreferences.getInt("badge",0);
    }


}
