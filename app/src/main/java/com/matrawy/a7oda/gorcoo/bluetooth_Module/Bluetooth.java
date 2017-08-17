package com.matrawy.a7oda.gorcoo.bluetooth_Module;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.matrawy.a7oda.gorcoo.CallBack;
import com.matrawy.a7oda.gorcoo.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by 7oda on 7/29/2017.
 */

public class Bluetooth {
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    Activity m;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    TextView myLabel;
    EditText myTextbox;
    BitSet dots;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

    public Bluetooth(Activity m) {
        this.m = m;
    }

    public Set<BluetoothDevice> findBT() {

        try {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (mBluetoothAdapter == null) {
                Log.e("FindBT", "No bluetooth adapter available. ");
                myLabel.setText("No bluetooth adapter available.");
            }
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
                m.startActivityForResult(enableBluetooth, 0);
            }

            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            return pairedDevices;
        } catch (NullPointerException e) {
            Log.e("Nulll", e.toString());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            Log.e("Exxx", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    // Tries to open a connection to the bluetooth printer device
    public void openBT(final ProgressDialog progressDialog, final CallBack call) throws IOException {
        Thread lol = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); // Standard SerialPortService ID
                    mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);

                    mmSocket.connect();
                    mmOutputStream = mmSocket.getOutputStream();
                    mmInputStream = mmSocket.getInputStream();
                    beginListenForData();
                } catch (Exception e) {
                }
                m.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if (!mmSocket.isConnected())
                            Toast.makeText(m, "Device Is Offline..", Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(m, "Device Is Connected .", Toast.LENGTH_LONG).show();
                            call.callBackFunc(0);
                        }
                    }
                });
            }
        });
        lol.start();
    }

    // After opening a connection to bluetooth printer device,
// we have to listen and check if a data were sent to be printed.
    void beginListenForData() {
        try {
            // This is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.currentThread().isInterrupted()
                            && !stopWorker) {

                        try {
                            int bytesAvailable = mmInputStream.available();
                            if (bytesAvailable > 0) {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                for (int i = 0; i < bytesAvailable; i++) {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length);
                                        final String data = new String(
                                                encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData(String msg) {
        try {
            print_image();
            mmOutputStream.write(msg.getBytes());
            m.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(m, "Done send data", Toast.LENGTH_LONG).show();
                }
            });
            mmOutputStream.flush();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Close the connection to bluetooth printer.
    public void closeBT(){
        try {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            myLabel.setText("Bluetooth Closed");
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BluetoothDevice getMmDevice() {
        return mmDevice;
    }

    public void setMmDevice(BluetoothDevice mmDevice) {
        this.mmDevice = mmDevice;
    }


    private void print_image() {
        Resources res = m.getResources();
        int id = R.drawable.logo;
        Bitmap bmp = BitmapFactory.decodeResource(res, id);
        convertBitmap(bmp);
        try {
            mmOutputStream.write(PrinterCommands.SET_LINE_SPACING_24);
            int offset = 0;
            while (offset < bmp.getHeight()) {
                mmOutputStream.write(PrinterCommands.SELECT_BIT_IMAGE_MODE);
                for (int x = 0; x < bmp.getWidth(); ++x) {

                    for (int k = 0; k < 3; ++k) {

                        byte slice = 0;
                        for (int b = 0; b < 8; ++b) {
                            int y = (((offset / 8) + k) * 8) + b;
                            int i = (y * bmp.getWidth()) + x;
                            boolean v = false;
                            if (i < dots.length()) {
                                v = dots.get(i);
                            }
                            slice |= (byte) ((v ? 1 : 0) << (7 - b));
                        }
                        mmOutputStream.write(slice);
                    }
                }
                offset += 24;
                mmOutputStream.write(PrinterCommands.FEED_LINE);
                mmOutputStream.write(PrinterCommands.FEED_LINE);
                mmOutputStream.write(PrinterCommands.FEED_LINE);
                mmOutputStream.write(PrinterCommands.FEED_LINE);
                mmOutputStream.write(PrinterCommands.FEED_LINE);
                mmOutputStream.write(PrinterCommands.FEED_LINE);
            }
            mmOutputStream.write(PrinterCommands.SET_LINE_SPACING_30);
            mmOutputStream.write(("\n\n\n").getBytes());

        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
    }

    public String convertBitmap(Bitmap inputBitmap) {

        int mWidth = inputBitmap.getWidth();
        int mHeight = inputBitmap.getHeight();

        convertArgbToGrayscale(inputBitmap, mWidth, mHeight);
        String mStatus = "ok";
        return mStatus;

    }

    private void convertArgbToGrayscale(Bitmap bmpOriginal, int width,
                                        int height) {
        int pixel;
        int k = 0;
        int B = 0, G = 0, R = 0;
        dots = new BitSet();
        try {

            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    // get one pixel color
                    pixel = bmpOriginal.getPixel(y, x);

                    // retrieve color of all channels
                    R = Color.red(pixel);
                    G = Color.green(pixel);
                    B = Color.blue(pixel);
                    // take conversion up to one single value by calculating
                    // pixel intensity.
                    R = G = B = (int) (0.299 * R + 0.587 * G + 0.114 * B);
                    // set bit into bitset, by calculating the pixel's luma
                    if (R < 55) {
                        dots.set(k);//this is the bitset that i'm printing
                    }
                    k++;
                }
            }

        } catch (Exception e) {
            Log.e("ImageException", e.toString());
        }
    }
}