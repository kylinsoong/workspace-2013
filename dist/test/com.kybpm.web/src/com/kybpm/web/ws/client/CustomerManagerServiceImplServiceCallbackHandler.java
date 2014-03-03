
/**
 * CustomerManagerServiceImplServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package com.kybpm.web.ws.client;

    /**
     *  CustomerManagerServiceImplServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class CustomerManagerServiceImplServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public CustomerManagerServiceImplServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public CustomerManagerServiceImplServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for validateCustomer method
            * override this method for handling normal response from validateCustomer operation
            */
           public void receiveResultvalidateCustomer(
                    com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub.ValidateCustomerResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from validateCustomer operation
           */
            public void receiveErrorvalidateCustomer(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for createCustomer method
            * override this method for handling normal response from createCustomer operation
            */
           public void receiveResultcreateCustomer(
                    com.kybpm.web.ws.client.CustomerManagerServiceImplServiceStub.CreateCustomerResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from createCustomer operation
           */
            public void receiveErrorcreateCustomer(java.lang.Exception e) {
            }
                


    }
    