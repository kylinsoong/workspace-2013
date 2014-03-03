
/**
 * InventoryManagerServiceImplServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

    package com.kybpm.web.ws.client;

    /**
     *  InventoryManagerServiceImplServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class InventoryManagerServiceImplServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public InventoryManagerServiceImplServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public InventoryManagerServiceImplServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for deleteProduct method
            * override this method for handling normal response from deleteProduct operation
            */
           public void receiveResultdeleteProduct(
                    com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.DeleteProductResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from deleteProduct operation
           */
            public void receiveErrordeleteProduct(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProducts method
            * override this method for handling normal response from getProducts operation
            */
           public void receiveResultgetProducts(
                    com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.GetProductsResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProducts operation
           */
            public void receiveErrorgetProducts(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addProduct method
            * override this method for handling normal response from addProduct operation
            */
           public void receiveResultaddProduct(
                    com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.AddProductResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addProduct operation
           */
            public void receiveErroraddProduct(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getProductById method
            * override this method for handling normal response from getProductById operation
            */
           public void receiveResultgetProductById(
                    com.kybpm.web.ws.client.InventoryManagerServiceImplServiceStub.GetProductByIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getProductById operation
           */
            public void receiveErrorgetProductById(java.lang.Exception e) {
            }
                


    }
    