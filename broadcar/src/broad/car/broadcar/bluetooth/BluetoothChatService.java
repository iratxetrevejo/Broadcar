/*********************************************************************
**																	**
** MODULES USED 													**
** 																	**
**********************************************************************/

package broad.car.broadcar.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import broad.car.broadcar.MainActivity;
import broad.car.broadcar.R;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

/** @addtogroup Broadcar
*
* @{

* @file BluetoothChatService
* @brief  This class does all the work for setting up and managing Bluetooth
 * connections with other devices. It has a thread that listens for
 * incoming connections, a thread for connecting with a device, and a
 * thread for performing data transmissions when connected.
*
* @par VERSION HISTORY
* Version : v0.0
* Date : 30/01/2013
* Revised by : BroadCar team
* Description : Original version.
* @author  Iratxe Trevejo
* @author  Ibon Ortega
* 
* @}
*/
public class BluetoothChatService {
    
	/*********************************************************************
	** 																	**
	** GLOBAL VARIABLES 												**
	** 																	**
	**********************************************************************/
	// Debugging
    private static final String TAG = "BluetoothChatService";
    private static final boolean D = true;

    // Name for the SDP record when creating server socket
    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String NAME_INSECURE = "BluetoothChatInsecure";

    // Unique UUID for this application
    //00001101-0000-1000-8000-00805F9B34FB
    //8ce255c0-200a-11e0-ac64-0800200c9a66
    private static final UUID MY_UUID_SECURE =
        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final UUID MY_UUID_INSECURE =
        UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    
    // Constants that indicate the current connection state
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_LISTEN = 1;     // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3;  // now connected to a remote device

	// Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	
	public String DEVICE_NAME;
	public String TOAST;
	/*********************************************************************
	** 																	**
	** IMPORTED CLASSES / Declarations  								**
	** 																	**
	**********************************************************************/
    // Member fields
    private final BluetoothAdapter mAdapter;
    private final Handler mHandler;
    private AcceptThread mSecureAcceptThread;
    private AcceptThread mInsecureAcceptThread;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private int mState;
    Context contexto;
	
	/**********************************************************************
	 * @brief  Constructor. Prepares a new BluetoothChat session.
	 * @param context  The UI Activity Context
     * @param handler  A Handler to send messages back to the UI Activity
	 * @return
	 * @TODO 

	**********************************************************************/		

    public BluetoothChatService(Context context, Handler handler) {
    	contexto = context;
    	mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
    }
	
	/**********************************************************************
	 * @brief  Set the current state of the chat connection
	 * @par	   Logica 
	 * 		    -	Give the new state to the Handler so the UI Activity can update

	 * @param state  An integer defining the current connection state
	 * @return
	 * @TODO 

	**********************************************************************/		
 
    private synchronized void setState(int state) {
        if (D) Log.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;

        // Give the new state to the Handler so the UI Activity can update
        mHandler.obtainMessage(Manage_BT_Comunication.MESSAGE_STATE_CHANGE, state, -1).sendToTarget();
    }
    
    
    
	/**********************************************************************
	 * @brief  Funcion Get que devuelve el estado 
	 * @return the current connection state
	 * @TODO 
	**********************************************************************/	
      public synchronized int getState() {
        return mState;
    }
      
      
  	/**********************************************************************
  	 * @brief  Start the chat service. Specifically start AcceptThread to begin a
     * 			session in listening (server) mode. Called by the Activity onResume()
     * @par	   Logica 
	 * 		    -	Cancel any thread attempting to make a connection
	 * 			-	Cancel any thread currently running a connection
	 * 			-	Start the thread to listen on a BluetoothServerSocket
  	 * @return 
  	 * @TODO 
  	**********************************************************************/	
    
    public synchronized void start() {
        if (D) Log.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        setState(STATE_LISTEN);

        // Start the thread to listen on a BluetoothServerSocket
        if (mSecureAcceptThread == null) {
            mSecureAcceptThread = new AcceptThread(true);
            mSecureAcceptThread.start();
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread(false);
            mInsecureAcceptThread.start();
        }
    }

    
  	/**********************************************************************
  	 * @brief  Start the ConnectThread to initiate a connection to a remote device.
     * @par	   Logica 
	 * 		    -	Cancel any thread attempting to make a connection
	 * 			-	Cancel any thread currently running a connection
	 *      	-	Start the thread to connect with the given device
	 * 
  	 * @param device  The BluetoothDevice to connect
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     * @return 
  	 * @TODO 
  	**********************************************************************/	
    /**
     * 
    
     */
    public synchronized void connect(BluetoothDevice device, boolean secure) {
        if (D) Log.d(TAG, "connect to: " + device);

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device, secure);
        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    
	/**********************************************************************
  	 * @brief  Start the ConnectedThread to begin managing a Bluetooth connection
     * @par	   Logica 
	 * 		    -	Cancel the thread that completed the connection
	 * 			-	Cancel any thread currently running a connection
	 * 			-	Cancel the accept thread because we only want to connect to one device
	 *      	-	Start the thread to manage the connection and perform transmissions
	 * 			-	Send the name of the connected device back to the UI Activity
  	 * @param socket  The BluetoothSocket on which the connection was made
     * @param device  The BluetoothDevice that has been connected
     * @return 
  	 * @TODO 
  	**********************************************************************/	
   
    public synchronized void connected(BluetoothSocket socket, BluetoothDevice
            device, final String socketType) {
        if (D) Log.d(TAG, "connected, Socket Type:" + socketType);

        // Cancel the thread that completed the connection
        if (mConnectThread != null) {mConnectThread.cancel(); mConnectThread = null;}

        // Cancel any thread currently running a connection
        if (mConnectedThread != null) {mConnectedThread.cancel(); mConnectedThread = null;}

        // Cancel the accept thread because we only want to connect to one device
        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }
        if (mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket, socketType);
        mConnectedThread.start();

        // Send the name of the connected device back to the UI Activity
        Message msg = mHandler.obtainMessage(MESSAGE_DEVICE_NAME);
        Bundle bundle = new Bundle();
        DEVICE_NAME=contexto.getResources().getText(R.string.DEVICE_NAME).toString();
        bundle.putString(DEVICE_NAME, device.getName());
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        setState(STATE_CONNECTED);
    }
    /**********************************************************************
  	 * @brief   Stop all threads
     * @param 
     * @return 
  	 * @TODO 
  	**********************************************************************/	
   
    public synchronized void stop() {
        if (D) Log.d(TAG, "stop");

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread = null;
        }

        if (mSecureAcceptThread != null) {
            mSecureAcceptThread.cancel();
            mSecureAcceptThread = null;
        }

        if (mInsecureAcceptThread != null) {
            mInsecureAcceptThread.cancel();
            mInsecureAcceptThread = null;
        }
        setState(STATE_NONE);
    }
    
	/**********************************************************************
  	 * @brief  Write to the ConnectedThread in an unsynchronized manner
     * @par	   Logica 
	 * 		    -	Create temporary object
	 * 			-	Synchronize a copy of the ConnectedThread
	 * 			-	Perform the write unsynchronized
  	 * @param out The bytes to write
     * @see ConnectedThread#write(byte[])
     * @return 
  	 * @TODO 
  	**********************************************************************/	

    public void write(byte[] out) {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED) return;
            r = mConnectedThread;
        }
        // Perform the write unsynchronized
        r.write(out);
    }

    
    /**********************************************************************
  	 * @brief   Indicate that the connection attempt failed and notify the UI Activity.
     * @par	   Logica 
	 * 		    -	Indicate that the connection attempt failed and notify the UI Activity.
	 * 			-	Start the service over to restart listening mode
  	 * @param 
     * @return 
  	 * @TODO 
  	**********************************************************************/	
   
    private void connectionFailed() {
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        TOAST=contexto.getResources().getText(R.string.TOAST).toString();
        bundle.putString(TOAST, "Unable to connect device");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        // Start the service over to restart listening mode
        BluetoothChatService.this.start();
    }

    /**********************************************************************
  	 * @brief  Indicate that the connection was lost and notify the UI Activity.
     * @par	   Logica 
	 * 		    -	Send a failure message back to the Activity
	 * 			-	Start the service over to restart listening mode
  	 * @param 
     * @return 
  	 * @TODO 
  	**********************************************************************/	
   
    private void connectionLost() {
        // Send a failure message back to the Activity
        Message msg = mHandler.obtainMessage(MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(TOAST, "Device connection was lost");
        msg.setData(bundle);
        mHandler.sendMessage(msg);

        // Start the service over to restart listening mode
        BluetoothChatService.this.start();
    }
    /**********************************************************************
   	 * @brief  This thread runs while listening for incoming connections. It behaves
     * 			like a server-side client. It runs until a connection is accepted
     * 			(or until cancelled).
     * @par	   Logica 
 	 * 		    -	The local server socket
 	 * 			-	Create a new listening server socket
   	 * @param 
     * @return 
   	 * @TODO 
   	**********************************************************************/	
    
    private class AcceptThread extends Thread {
        // The local server socket
        private final BluetoothServerSocket mmServerSocket;
        private String mSocketType;

        public AcceptThread(boolean secure) {
            BluetoothServerSocket tmp = null;
            mSocketType = secure ? "Secure":"Insecure";

            // Create a new listening server socket
            try {
                if (secure) {
                    tmp = mAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE,MY_UUID_SECURE);
                } else {
                    tmp = mAdapter.listenUsingInsecureRfcommWithServiceRecord(NAME_INSECURE, MY_UUID_INSECURE);
                }
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + "listen() failed", e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            if (D) Log.d(TAG, "Socket Type: " + mSocketType +
                    "BEGIN mAcceptThread" + this);
            setName("AcceptThread" + mSocketType);

            BluetoothSocket socket = null;

            // Listen to the server socket if we're not connected
            while (mState != STATE_CONNECTED) {
                try {
                    // This is a blocking call and will only return on a
                    // successful connection or an exception
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    Log.e(TAG, "Socket Type: " + mSocketType + "accept() failed", e);
                    break;
                }

                // If a connection was accepted
                if (socket != null) {
                    synchronized (BluetoothChatService.this) {
                        switch (mState) {
                        case STATE_LISTEN:
                        case STATE_CONNECTING:
                            // Situation normal. Start the connected thread.
                            connected(socket, socket.getRemoteDevice(),
                                    mSocketType);
                            break;
                        case STATE_NONE:
                        case STATE_CONNECTED:
                            // Either not ready or already connected. Terminate new socket.
                            try {
                                socket.close();
                            } catch (IOException e) {
                                Log.e(TAG, "Could not close unwanted socket", e);
                            }
                            break;
                        }
                    }
                }
            }
            if (D) Log.i(TAG, "END mAcceptThread, socket Type: " + mSocketType);

        }

        public void cancel() {
            if (D) Log.d(TAG, "Socket Type" + mSocketType + "cancel " + this);
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Socket Type" + mSocketType + "close() of server failed", e);
            }
        }
    }

    /**********************************************************************
   	 * @brief  Get a BluetoothSocket for a connection with the given BluetoothDevice
   	 * @param 
     * @return 
   	 * @TODO 
   	**********************************************************************/	
  
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;

        public ConnectThread(BluetoothDevice device, boolean secure) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = secure ? "Secure" : "Insecure";

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                if (secure) {
                    tmp = device.createRfcommSocketToServiceRecord(
                            MY_UUID_SECURE);
                } else {
                    tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID_INSECURE);
                }
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + "create() failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread" + mSocketType);

            // Always cancel discovery because it will slow down a connection
            mAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.e(TAG, "unable to close() " + mSocketType +
                            " socket during connection failure", e2);
                }
                connectionFailed();
                return;
            }

            // Reset the ConnectThread because we're done
            synchronized (BluetoothChatService.this) {
                mConnectThread = null;
            }

            // Start the connected thread
            connected(mmSocket, mmDevice, mSocketType);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
            }
        }
    }

    /**********************************************************************
   	 * @brief This thread runs during a connection with a remote device.
     * 			It handles all incoming and outgoing transmissions.
   	 * @param 
     * @return 
   	 * @TODO 
   	**********************************************************************/	 
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            Log.d(TAG, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the BluetoothSocket input and output streams
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                Log.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        
        
        /**********************************************************************
       	 * @brief 
       	 * @par Logica
       	 * 		-Keep listening to the InputStream while connected
       	 * 		-Read from the InputStream
       	 * 		-Send the obtained bytes to the UI Activity
       	 * 		-Start the service over to restart listening mode
       	 * @param 
         * @return 
       	 * @TODO 
       	**********************************************************************/	 
        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep listening to the InputStream while connected
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    // Send the obtained bytes to the UI Activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                    connectionLost();
                    // Start the service over to restart listening mode
                    BluetoothChatService.this.start();
                    break;
                }
            }
        }
        /**********************************************************************
       	 * @brief Write to the connected OutStream.
       	 * @par Logica
       	 * 		-Share the sent message back to the UI Activity
       	 * @param 
         * @return 
       	 * @TODO 
       	**********************************************************************/	 
        /**
         * 
         * @param buffer  The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                mmOutStream.write(buffer);

                // Share the sent message back to the UI Activity
                mHandler.obtainMessage(MESSAGE_WRITE, -1, -1, buffer)
                        .sendToTarget();
            } catch (IOException e) {
                Log.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }
}