package com.tyaa.OPRST.Cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.ViewGroup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by Pavlo on 11/15/2015.
 */
public abstract class Serializator<T> {
    public static final String TAG = "Serializator";
    private File cacheDir;
    protected abstract Context getContext();
    protected abstract String getFilename();
    protected Serializator(){
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().
                equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory(),
                    "com.tyaa.OPRST/.Cache");
        else
            cacheDir=getContext().getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }

    protected void clear(){
        File[] files=cacheDir.listFiles();
        for(File f:files){
            f.delete();}
    }
    public static String md5(String s) { try {

        // Create MD5 Hash
        MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
        digest.update(s.getBytes());
        byte messageDigest[] = digest.digest();

        // Create Hex String
        StringBuffer hexString = new StringBuffer();
        for (int i=0; i<messageDigest.length; i++)
            hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
        return hexString.toString();

    } catch (NoSuchAlgorithmException nsae) {
        Log.e(TAG, "Failed to create hash", nsae);
    }
        return "";

    }
    protected void Serialize(ArrayList<T> list) {
        Serialize(list,getFilename());
    }
    protected void Serialize(ArrayList<T> list,String filename) {
        FileOutputStream fos;
        try {
            //fos = context.getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
            fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
        } catch (FileNotFoundException fnfe) {
            Log.e(TAG, "Failed to find file on serialize", fnfe);
        } catch (IOException io) {
            Log.e(TAG, "Failed to get access to file on serialize", io);
        }
    }

    protected void Serialize(T object,String filename){
        FileOutputStream fos;
        try {
            //fos = context.getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
            fos = getContext().openFileOutput(filename, Context.MODE_PRIVATE);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
        } catch (FileNotFoundException fnfe) {
            Log.e(TAG, "Failed to find file on serialize", fnfe);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to get access to file on serialize", ioe);
        }
    }
    protected T Desserialize() {
        return Desserialize(getFilename());
    }

    protected T Desserialize(String filename) {
        FileInputStream fin;
        T afromfile = null;

        try {
            fin = getContext().openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fin);
            afromfile = (T) ois.readObject();
            ois.close();
        } catch (StreamCorruptedException sce) {
            Log.e(TAG, "Failed to read stream", sce);
        } catch (OptionalDataException ode) {
            Log.e(TAG, "Failed to find object in file", ode);
        } catch (FileNotFoundException fnfe) {
            Log.e(TAG, "Failed to find file on deserialize", fnfe);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to get access to file on deserialize", ioe);
        } catch (ClassNotFoundException cnfe) {
            Log.e(TAG, "Failed to get deserialize class", cnfe);
        }

        return afromfile;

    }
}
