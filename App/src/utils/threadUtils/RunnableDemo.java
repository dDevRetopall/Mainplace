package utils.threadUtils;

import cliente.net.connection.MainCliente;
import cliente.net.register.RegisterActivity;
import main.mainApplication.Constantes;

public class RunnableDemo implements Runnable {
	   public Thread t;
	   private String threadName;
	   boolean suspended = false;
	   int contador = Constantes.timeForCode;
	   int segundos = 0;
	   int minutos = 0;
	  public  boolean continuing=true;
	private RegisterActivity ra;
	   public RunnableDemo( String name,RegisterActivity ra) {
	      threadName = name;
		this.ra = ra;
	    
	      
	   }
	   
	   public void run() {
		  while (continuing) {
	      
	      try {
	    	  	minutos = (int) (contador / 60);
				segundos = (contador % 60);
				if (segundos <= 9) {
					ra.background2.setText(ra.info + " (" + minutos + ":0" + segundos + ")");
				} else {
					ra.background2.setText(ra.info + " (" + minutos + ":" + segundos + ")");
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				contador--;
				if (contador == 0) {
					if (segundos <= 9) {
						minutos = (int) (contador / 60);
						segundos = (contador % 60);
						ra.background2.setText(ra.info + " (" + minutos + ":0" + segundos + ")");
					} else {
						ra.background2.setText(ra.info + " (" + minutos + ":" + segundos + ")");
					}
					if (ra.resent == false) {
						ra.info = "The activation code haven't been completed. We have resent another email";
						MainCliente.sendEmailSendRequest(ra.tf3.getText(), ra.tf1.getText());
						ra.waiting();
						contador = Constantes.timeForCode;
						ra.resent = true;
						this.suspend();
					} else {
						ra.info = "The activation code haven't been completed";
						ra.background.setText("Session for creating account expired");
						ra.background2.setText(ra.info);
						ra.p33.remove(ra.labelCode);
						ra.p33.remove(ra.verification);
						ra.p33.remove(ra.codeL);
						ra.p33.updateUI();
						ra.quitarTodo();
						continuing = false;

						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							System.out.println("Error thread sleep");
							e.printStackTrace();
						}

					}

				}
	            synchronized(this) {
	               while(suspended) {
	                  wait();
	               }
	            }
	         
	      }catch (InterruptedException e) {
	       e.printStackTrace();
	       System.out.println("Error thread");
	      }
	     
		  }
	   }

	   public void start () {
	     
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	   }
	   public void stop () {
		     
		     
		   }
	   
	   public void suspend() {
	      suspended = true;
	   }
	   
	   public synchronized void resume() {
	      suspended = false;
	      notify();
	   }
	}

	
	