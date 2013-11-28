package com.redhat.gss;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

import javax.management.MBeanServer;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.jboss.remoting.InvocationRequest;
import org.jboss.remoting.InvocationResponse;
import org.jboss.remoting.InvokerLocator;
import org.jboss.remoting.ServerInvocationHandler;
import org.jboss.remoting.ServerInvoker;
import org.jboss.remoting.callback.InvokerCallbackHandler;
import org.jboss.remoting.transport.Connector;
import org.jboss.remoting.transport.PortUtil;


public class SupportCase898655TestCase extends TestCase
{
   private static Logger log = Logger.getLogger(SupportCase898655TestCase.class);
   
   private static boolean firstTime = true;
   
   protected String host;
   protected int port;
   protected String locatorURI;
   protected InvokerLocator serverLocator;
   protected Connector connector;
   protected TestInvocationHandler invocationHandler;

   
   public void setUp() throws Exception
   {
      if (firstTime)
      {
         firstTime = false;
         Logger.getLogger("org.jboss.remoting").setLevel(Level.INFO);
         Logger.getLogger("org.jboss.test.remoting").setLevel(Level.INFO);
         String pattern = "[%d{ABSOLUTE}] [%t] %5p (%F:%L) - %m%n";
         PatternLayout layout = new PatternLayout(pattern);
         ConsoleAppender consoleAppender = new ConsoleAppender(layout);
         Logger.getRootLogger().addAppender(consoleAppender);  
      }
   }

   
   public void tearDown()
   {
   }
   
   /**
    * Remoting expects a version byte, but, since the payload is not an InvocationRequest,
    * it doesn't write a version byte.
    */
   public void testRawData() throws Throwable
   {
      log.info("entering " + getName());
      setupServer();
      Socket s = new Socket("localhost", serverLocator.getPort());
      ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
      oos.write(22); // Write version byte.
      oos.writeObject("abc");
      ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
      Object result = ois.readObject();
      System.out.println(result);
      Assert.assertEquals("abc", result);
      shutdownServer();
      log.info(getName() + " PASSES");
   }
   
   
   /**
    * The payload is an InvocationRequest, so Remoting returns a version byte.
    */
   public void testInvocationRequest() throws Throwable
   {
      log.info("entering " + getName());
      setupServer();
      Socket s = new Socket("localhost", serverLocator.getPort());
      ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
      oos.write(22); // Write version byte.
      InvocationRequest request = new InvocationRequest(null, "test", "abc", null, null, serverLocator);
      oos.writeObject(request);
      InputStream is = s.getInputStream();
      ObjectInputStream ois = new ObjectInputStream(is);
      ois.read(); // Get version byte.
      InvocationResponse response = (InvocationResponse) ois.readObject();
      Object result = response.getResult();
      System.out.println(result);
      Assert.assertEquals("abc", result);
      shutdownServer();
      log.info(getName() + " PASSES");
   }
   
   
   protected String getTransport()
   {
      return "socket";
   }
   

   protected void setupServer() throws Exception
   {
      host = InetAddress.getLocalHost().getHostAddress();
      port = PortUtil.findFreePort(host);
      locatorURI = getTransport() + "://" + host + ":" + port;
      serverLocator = new InvokerLocator(locatorURI);
      log.info("Starting remoting server with locator uri of: " + locatorURI);
      HashMap config = new HashMap();
      config.put(InvokerLocator.FORCE_REMOTE, "true");
      connector = new Connector(serverLocator, config);
      connector.create();
      invocationHandler = new TestInvocationHandler();
      connector.addInvocationHandler("test", invocationHandler);
      connector.start();
   }
   
   
   protected void shutdownServer() throws Exception
   {
      if (connector != null)
         connector.stop();
   }
   
   
   static class TestInvocationHandler implements ServerInvocationHandler
   {
      public void addListener(InvokerCallbackHandler callbackHandler) {}
      public Object invoke(final InvocationRequest invocation) throws Throwable
      {
         Object o = invocation.getParameter();
         return o;
      }
      public void removeListener(InvokerCallbackHandler callbackHandler) {}
      public void setMBeanServer(MBeanServer server) {}
      public void setInvoker(ServerInvoker invoker) {}
   }
}