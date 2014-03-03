
/**
 * CalculateServiceImplServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package com.kybpm.web.ws.client;

    /**
     *  CalculateServiceImplServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class CalculateServiceImplServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public CalculateServiceImplServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public CalculateServiceImplServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for calculateSalesTax method
            * override this method for handling normal response from calculateSalesTax operation
            */
           public void receiveResultcalculateSalesTax(
                    com.kybpm.web.ws.client.CalculateServiceImplServiceStub.CalculateSalesTaxResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from calculateSalesTax operation
           */
            public void receiveErrorcalculateSalesTax(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for calculateShippingTotal method
            * override this method for handling normal response from calculateShippingTotal operation
            */
           public void receiveResultcalculateShippingTotal(
                    com.kybpm.web.ws.client.CalculateServiceImplServiceStub.CalculateShippingTotalResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from calculateShippingTotal operation
           */
            public void receiveErrorcalculateShippingTotal(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for calculateGrandTotal method
            * override this method for handling normal response from calculateGrandTotal operation
            */
           public void receiveResultcalculateGrandTotal(
                    com.kybpm.web.ws.client.CalculateServiceImplServiceStub.CalculateGrandTotalResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from calculateGrandTotal operation
           */
            public void receiveErrorcalculateGrandTotal(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for calculateProductTotal method
            * override this method for handling normal response from calculateProductTotal operation
            */
           public void receiveResultcalculateProductTotal(
                    com.kybpm.web.ws.client.CalculateServiceImplServiceStub.CalculateProductTotalResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from calculateProductTotal operation
           */
            public void receiveErrorcalculateProductTotal(java.lang.Exception e) {
            }
                


    }
    